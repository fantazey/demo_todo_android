package com.example.firsttestapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.firsttestapp.data.dao.TaskDao
import com.example.firsttestapp.data.model.CheckListItem
import com.example.firsttestapp.data.model.Task
import com.example.firsttestapp.data.model.TaskCheckList

@Database(entities = [Task::class, TaskCheckList::class, CheckListItem::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        fun getInstance(context: Context): TodoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, TodoDatabase::class.java, "todo_database").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}