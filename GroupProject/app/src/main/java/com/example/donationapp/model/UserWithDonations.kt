package com.example.donationapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithDonations(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val donations: List<Donation>
)
