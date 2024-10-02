package com.example.firsttestapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_check_list")
data class TaskCheckList(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    @ColumnInfo(name = "task_id")
    var taskId: Int = 0
)