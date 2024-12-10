package com.example.roomdatabasedemo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Query("SELECT * FROM task_table WHERE userId = :userId")
    suspend fun getTasksForUser(userId: Int): List<Task>

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task_table WHERE userId = :userId")
    fun getTasksByUserId(userId: Int): List<Task>

}
