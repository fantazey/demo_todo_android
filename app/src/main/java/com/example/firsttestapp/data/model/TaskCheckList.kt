package com.example.firsttestapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_check_list")
data class TaskCheckList(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var taskId: Int = 0
)