package com.example.donationapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class DonationWithCampaign(
    @Embedded val donation: Donation,
    @Relation(
        parentColumn = "campaignId",
        entityColumn = "id"
    )
    val campaign: Campaign? // Make campaign nullable to handle missing data
)
