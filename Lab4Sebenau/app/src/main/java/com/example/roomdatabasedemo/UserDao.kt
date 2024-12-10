package com.example.roomdatabasedemo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE name LIKE :name")
    fun searchUsers(name: String): LiveData<List<User>>

    @Insert
    suspend fun insertTask(task: Task)

    // New methods for tasks
    @Query("INSERT INTO task_table (userId, taskName) VALUES (:userId, :task)")
    suspend fun addTaskForUser(userId: Int, task: String)

    @Query("SELECT * FROM task_table WHERE userId = :userId")
    fun getTasksForUser(userId: Int): LiveData<List<Task>>
}
