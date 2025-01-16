package com.example.donationapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donation_table")
data class Donation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val campaignId: Int, // Correct type
    val donationAmount: Double, // Correct type
    val remark: String,
    val campaignName: String // Add this field to store the campaign name
)