package com.example.donationapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val userName: String,
    val email: String,
    val phoneNumber: String,
    val password: String
)