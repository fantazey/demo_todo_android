package com.example.firsttestapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "check_list_item")
data class CheckListItem(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var work: String = "",
    var unit: String = "",
    var count: Int = 0
)