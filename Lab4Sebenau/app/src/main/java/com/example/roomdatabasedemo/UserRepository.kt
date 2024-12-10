package com.example.roomdatabasedemo

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao, private val taskDao: TaskDao) {
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

    // Task-related methods
    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    suspend fun getTasksForUser(userId: Int): List<Task> {
        return taskDao.getTasksForUser(userId)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

    suspend fun getTasksByUserId(userId: Int): List<Task> {
        return taskDao.getTasksByUserId(userId)
    }

}
