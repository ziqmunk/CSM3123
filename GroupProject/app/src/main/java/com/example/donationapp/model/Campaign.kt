package com.example.donationapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campaign_table")
data class Campaign (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val campaignName: String,
    val campaignDetails: String,
    val targetAmount: Double,
    val donatedAmount: Double,
    val remark: String
)