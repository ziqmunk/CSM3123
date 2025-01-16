package com.example.donationapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donationapp.R
import com.example.donationapp.adapter.CampaignAdapter
import com.example.donationapp.model.Campaign
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Retrieve the passed username and user ID from the Intent
        val username = intent.getStringExtra("username")
        val userId = intent.getIntExtra("USER_ID", -1) // Retrieve the actual user ID

        // Set welcome message
        val tvWelcome: TextView = findViewById(R.id.tvWelcome)
        tvWelcome.text = if (username != null) {
            "Welcome, $username!"  // Set the username in the welcome text
        } else {
            "Welcome, User!"  // Default text if username is null
        }

        val rvCampaigns: RecyclerView = findViewById(R.id.rvCampaigns)

        // Sample campaigns (replace with dynamic campaigns from DB)
        val campaigns = listOf(
            Campaign(id = 1, userId = userId, campaignName = "Kita Bantu Gaza", campaignDetails = "Bantuan barangan keperluan asas seperti makanan dan pakaian musim sejuk.", targetAmount = 270000.00, donatedAmount = 0.0, remark = ""),
            Campaign(id = 2, userId = userId, campaignName = "Sentuhan Anak Down Syndrome", campaignDetails = "Bantuan untuk meringankan beban ibu bapa yang menjaga anak-anak down sindrom.", targetAmount = 80000.00, donatedAmount = 0.0, remark = ""),
            Campaign(id = 3, userId = userId, campaignName = "Misi Banjir Pantai Timur", campaignDetails = "Bantuan asas seperti makanan, pakaian, dan wang ringgit untuk membaiki rumah penduduk yang terjejas di pantai timur.", targetAmount = 500000.00, donatedAmount = 0.0, remark = ""),
            Campaign(id = 4, userId = userId, campaignName = "Kebakaran Rumah Anak Yatim", campaignDetails = "Sumbangan ikhlas untuk membina kembali rumah anak yatim Darul Taqwa yang telah terbakar.", targetAmount = 300000.00, donatedAmount = 0.0, remark = "")
        )

        // Pass a lambda to handle item clicks
        val adapter = CampaignAdapter(campaigns) { selectedCampaign ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("campaign_name", selectedCampaign.campaignName)
                putExtra("campaign_details", selectedCampaign.campaignDetails)
                putExtra("campaign_amount", selectedCampaign.targetAmount)
                putExtra("USER_ID", userId)  // Pass the correct userId to DetailsActivity
            }
            startActivity(intent)
        }

        rvCampaigns.adapter = adapter
        rvCampaigns.layoutManager = LinearLayoutManager(this)

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
    }
}
