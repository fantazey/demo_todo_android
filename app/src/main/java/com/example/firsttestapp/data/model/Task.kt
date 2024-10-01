package com.example.firsttestapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "task_id")
    var taskId: Int = 0,
    @ColumnInfo(name="name")
    var name: String = "",
    @ColumnInfo(name="related_place")
    var relatedPlace: String = "",
    @ColumnInfo(name="address")
    var address: String = "",
    @ColumnInfo(name="activity")
    var activity: Int = 0
)