package com.example.donationapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.donationapp.model.Campaign
import com.example.donationapp.repository.CampaignRepository
import com.example.donationapp.util.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CampaignViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CampaignRepository

    init {
        val campaignDao = AppDatabase.getInstance(application).campaignDao()
        repository = CampaignRepository(campaignDao)
    }

    // ✅ Insert Campaign
    fun insertCampaign(campaign: Campaign, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertCampaign(campaign)
                withContext(Dispatchers.Main) {
                    onResult(true, "Campaign inserted successfully")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(false, "Failed to insert campaign: ${e.message}")
                }
            }
        }
    }

    // ✅ Get Campaigns for User
    fun getCampaignsForUser(userId: Int, onResult: (List<Campaign>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val campaigns = repository.getCampaignsForUser(userId)
                withContext(Dispatchers.Main) {
                    onResult(campaigns)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(emptyList())
                }
            }
        }
    }

    // ✅ Delete Campaign
    fun deleteCampaign(campaign: Campaign, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteCampaign(campaign)
                withContext(Dispatchers.Main) {
                    onResult(true, "Campaign deleted successfully")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(false, "Failed to delete campaign: ${e.message}")
                }
            }
        }
    }
}
