package com.example.donationapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donationapp.R
import com.example.donationapp.model.DonationWithCampaign
import com.example.donationapp.ui.adapters.DonationAdapter
import com.example.donationapp.util.AppDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var rvDonations: RecyclerView
    private lateinit var adapter: DonationAdapter
    private lateinit var btnClearHistory: Button
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Initialize RecyclerView
        rvDonations = findViewById(R.id.rvDonations)
        rvDonations.layoutManager = LinearLayoutManager(this)

        // Retrieve user ID from intent
        val userId = intent.getIntExtra("USER_ID", -1)
        Log.d("HistoryActivity", "Received User ID: $userId")

        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID. Please log in again.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Load donation history
        loadDonationHistory(userId)

        // Initialize Clear History Button
        btnClearHistory = findViewById(R.id.btnClearHistory)
        btnClearHistory.setOnClickListener {
            clearDonationHistory(userId)
        }

        // Bottom Navigation
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_history -> {
                    startActivity(Intent(this, HistoryActivity::class.java))
                    return@setOnNavigationItemSelectedListener false
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    return@setOnNavigationItemSelectedListener false
                }
                else -> false
            }
        }
    }

    private fun loadDonationHistory(userId: Int) {
        coroutineScope.launch {
            try {
                val database = withContext(Dispatchers.IO) { AppDatabase.getInstance(this@HistoryActivity) }
                val donations: List<DonationWithCampaign> = withContext(Dispatchers.IO) {
                    database.donationDao().getDonationsWithCampaignsByUser(userId)
                }

                if (donations.isNotEmpty()) {
                    // Update the adapter with donation history data
                    adapter = DonationAdapter(donations) { donation ->
                        showEditRemarkDialog(donation)
                    }
                    rvDonations.adapter = adapter
                } else {
                    Toast.makeText(this@HistoryActivity, "No donations found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("HistoryActivity", "Error loading donations", e)
                Toast.makeText(this@HistoryActivity, "Error loading donations: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Show Edit Remark Dialog
    private fun showEditRemarkDialog(donation: DonationWithCampaign) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_remark, null)
        val etRemark = dialogView.findViewById<EditText>(R.id.etRemark)
        etRemark.setText(donation.donation.remark) // Accessing donation's remark

        AlertDialog.Builder(this)
            .setTitle("Edit Remark")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val updatedRemark = etRemark.text.toString()
                updateDonationRemark(donation.donation.id, updatedRemark)
            }
            .setNegativeButton("Dismiss", null)
            .show()
    }

    // Update Donation Remark in Database
    private fun updateDonationRemark(donationId: Int, newRemark: String) {
        coroutineScope.launch {
            try {
                val database = withContext(Dispatchers.IO) { AppDatabase.getInstance(this@HistoryActivity) }
                withContext(Dispatchers.IO) {
                    database.donationDao().updateRemark(donationId, newRemark)
                }
                Toast.makeText(this@HistoryActivity, "Remark updated", Toast.LENGTH_SHORT).show()
                loadDonationHistory(intent.getIntExtra("USER_ID", -1)) // Reload donation history after update
            } catch (e: Exception) {
                Log.e("HistoryActivity", "Error updating remark", e)
                Toast.makeText(this@HistoryActivity, "Error updating remark: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Clear Donation History for User
    private fun clearDonationHistory(userId: Int) {
        coroutineScope.launch {
            try {
                val database = withContext(Dispatchers.IO) { AppDatabase.getInstance(this@HistoryActivity) }
                withContext(Dispatchers.IO) {
                    database.donationDao().clearDonationsByUser(userId)
                }
                // Pass a dummy callback for empty list
                adapter = DonationAdapter(emptyList()) { }
                rvDonations.adapter = adapter
                Toast.makeText(this@HistoryActivity, "Donation history cleared", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("HistoryActivity", "Error clearing donation history", e)
                Toast.makeText(this@HistoryActivity, "Error clearing history: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel() // Cancel any ongoing coroutines when the activity is destroyed
    }
}
