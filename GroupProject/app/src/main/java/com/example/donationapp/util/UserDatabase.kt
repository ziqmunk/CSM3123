package com.example.donationapp.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.donationapp.model.Campaign
import com.example.donationapp.model.CampaignDao
import com.example.donationapp.model.Donation
import com.example.donationapp.model.User
import com.example.donationapp.model.DonationDao
import com.example.donationapp.model.UserDao

@Database(
    entities = [User::class, Campaign::class, Donation::class],
    version = 3,  // Incremented version number
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun campaignDao(): CampaignDao
    abstract fun donationDao(): DonationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    // Add migration handling here (for future schema changes)
                    .addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration()  // This is used for destructive migrations (not recommended for production)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Example migration from version 2 to version 3 (you can add specific changes here if needed)
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example: Add a new column in Donation table
                // database.execSQL("ALTER TABLE donation_table ADD COLUMN new_column TEXT DEFAULT ''")
            }
        }
    }
}
