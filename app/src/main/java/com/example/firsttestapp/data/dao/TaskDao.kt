package com.example.firsttestapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.firsttestapp.data.model.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Insert
    suspend fun insertAll(tasks: List<Task>)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task WHERE task_id = :taskId")
    fun get(taskId: Long): LiveData<Task>

    @Query("SELECT * FROM task ORDER BY task_id DESC")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT count(1) FROM task")
    suspend fun count(): Int

    @Query("SELECT * FROM task WHERE task_id = :taskId LIMIT 1")
    suspend fun getById(taskId: Int): Task?
}