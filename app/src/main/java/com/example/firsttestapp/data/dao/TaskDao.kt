package com.example.firsttestapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.firsttestapp.data.model.CheckListItem
import com.example.firsttestapp.data.model.Task
import com.example.firsttestapp.data.model.TaskCheckList

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
    fun get(taskId: Int): LiveData<Task>

    @Query("SELECT * FROM task ORDER BY task_id asc")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT count(1) FROM task")
    suspend fun count(): Int

    @Query("SELECT * FROM task WHERE task_id = :taskId LIMIT 1")
    suspend fun getById(taskId: Int): Task?

    @Insert
    suspend fun insertCheckListItem(task: CheckListItem)

    @Update
    suspend fun updateCheckListItem(task: CheckListItem)

    @Delete
    suspend fun deleteCheckListItem(task: CheckListItem)

    @Insert
    suspend fun insertTaskCheckList(taskCheckList: TaskCheckList)

    @Update
    suspend fun updateTaskCheckList(taskCheckList: TaskCheckList)

    @Delete
    suspend fun deleteTaskCheckList(taskCheckList: TaskCheckList)

    @Query("SELECT * FROM task_check_list WHERE id = :id LIMIT 1")
    suspend fun getTaskCheckListById(id: Int): TaskCheckList?

    @Query("SELECT * FROM check_list_item WHERE id = :id LIMIT 1")
    suspend fun getCheckListItemById(id: Int): CheckListItem?

    @Query("SELECT check_list_item.* FROM check_list_item " +
            "JOIN task_check_list ON task_check_list.id = check_list_item.check_list_item_id " +
            "WHERE task_check_list.task_id = :taskId")
    fun getCheckListItems(taskId: Int): LiveData<List<CheckListItem>>

    @Query("SELECT check_list_item.* FROM check_list_item " +
            "JOIN task_check_list ON task_check_list.id = check_list_item.check_list_item_id " +
            "WHERE task_check_list.task_id = 2")
    fun getCheckListItemsDebug(): LiveData<List<CheckListItem>>
}