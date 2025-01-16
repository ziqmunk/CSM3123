package com.example.donationapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.donationapp.model.Campaign

@Dao
interface CampaignDao {
    @Insert
    suspend fun insert(campaign: Campaign)

    @Query("SELECT * FROM campaign_table WHERE userId = :userId")
    suspend fun getCampaignsByUserId(userId: Int): List<Campaign>

    @Query("SELECT * FROM campaign_table WHERE id = :campaignId")
    suspend fun getCampaignById(campaignId: Int): Campaign?

    @Update
    suspend fun update(campaign: Campaign)

    @Query("UPDATE campaign_table SET donatedAmount = :amount WHERE id = :campaignId")
    suspend fun updateDonatedAmount(campaignId: Int, amount: Double)

    @Delete
    suspend fun delete(campaign: Campaign)
}
