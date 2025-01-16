package com.example.donationapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.donationapp.R
import com.example.donationapp.model.Donation
import com.example.donationapp.util.AppDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class DetailsActivity : AppCompatActivity() {

    private var campaignId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Handle Bottom Navigation item selection
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Handle Home action
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_history -> {
                    // Handle History action
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener false
                }
                R.id.nav_profile -> {
                    // Handle Profile action
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener false
                }
                else -> false
            }
        }

        // Retrieve data from Intent
        val campaignName = intent.getStringExtra("campaign_name") ?: "Default Campaign"
        val campaignDetails = intent.getStringExtra("campaign_details") ?: "No details"
        val campaignAmount = intent.getDoubleExtra("campaign_amount", 0.0)
        campaignId = intent.getIntExtra("campaign_id", 0)

        val tvCampaignTitle: TextView = findViewById(R.id.tvCampaignTitle)
        val tvCampaignDetails: TextView = findViewById(R.id.tvDescriptionContent)
        val tvTotalAmountContent: TextView = findViewById(R.id.tvTotalAmountContent)

        tvCampaignTitle.text = campaignName
        tvCampaignDetails.text = campaignDetails
        tvTotalAmountContent.text = "RM ${String.format("%,.2f", campaignAmount)}"

        val btnDonateNow: Button = findViewById(R.id.btnDonateNow)

        btnDonateNow.setOnClickListener {
            val remark = findViewById<EditText>(R.id.editRemark).text.toString().trim()
            val donationAmountString = findViewById<EditText>(R.id.editAmount).text.toString().trim()

            if (donationAmountString.isEmpty()) {
                Toast.makeText(this, "Donation amount cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val donationAmount = donationAmountString.toDoubleOrNull() ?: run {
                Toast.makeText(this, "Enter a valid donation amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (remark.isEmpty()) {
                Toast.makeText(this, "Remark cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = intent.getIntExtra("USER_ID", -1)
            if (userId == -1) {
                Toast.makeText(this, "Invalid user. Please log in again.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val database = AppDatabase.getInstance(this@DetailsActivity)
                    val donation = Donation(userId = userId, campaignId = campaignId, campaignName = campaignName, donationAmount = donationAmount, remark = remark)
                    database.donationDao().insert(donation)

                    val campaign = database.campaignDao().getCampaignById(campaignId)
                    campaign?.let {
                        val updatedAmount = it.donatedAmount + donationAmount
                        database.campaignDao().updateDonatedAmount(campaignId, updatedAmount)
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@DetailsActivity, "Donation successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@DetailsActivity, HistoryActivity::class.java)
                        intent.putExtra("USER_ID", userId)  // Pass the user ID to HistoryActivity
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@DetailsActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
