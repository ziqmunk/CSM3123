package com.example.donationapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface DonationDao {

    @Insert
    suspend fun insert(donation: Donation)

    @Query("SELECT * FROM donation_table WHERE userId = :userId")
    suspend fun getDonationsByUser(userId: Int): List<Donation>

    @Transaction
    @Query("SELECT * FROM donation_table WHERE userId = :userId")
    suspend fun getDonationsWithCampaignsByUser(userId: Int): List<DonationWithCampaign>

    @Query("DELETE FROM donation_table WHERE userId = :userId")
    suspend fun clearDonationsByUser(userId: Int)

    @Query("UPDATE donation_table SET remark = :newRemark WHERE id = :donationId")
    suspend fun updateRemark(donationId: Int, newRemark: String)
}
