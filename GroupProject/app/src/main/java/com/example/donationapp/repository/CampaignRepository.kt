package com.example.donationapp.repository

import com.example.donationapp.model.Campaign
import com.example.donationapp.model.CampaignDao


class CampaignRepository(private val campaignDao: CampaignDao) {

    suspend fun insertCampaign(campaign: Campaign) {
        campaignDao.insert(campaign)
    }

    suspend fun getCampaignsForUser(userId: Int): List<Campaign> {
        return campaignDao.getCampaignsByUserId(userId)
    }

    suspend fun deleteCampaign(campaign: Campaign) {
        campaignDao.delete(campaign) // Ensure this matches the DAO's delete method
    }
}
