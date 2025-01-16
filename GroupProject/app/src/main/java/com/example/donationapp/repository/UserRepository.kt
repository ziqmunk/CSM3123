package com.example.donationapp.repository

import androidx.lifecycle.LiveData
import com.example.donationapp.model.User
import com.example.donationapp.model.UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun login(username: String, password: String): User? {
        return userDao.getUserByCredentials(username, password)
    }

    suspend fun register(user: User): Boolean {
        return try {
            userDao.insertUser(user)
            true
        } catch (e: Exception) {
            false
        }
    }

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    fun searchUsers(name: String): LiveData<List<User>> {
        return userDao.searchUsers(name)
    }
}
