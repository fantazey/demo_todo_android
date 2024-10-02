package com.example.firsttestapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "check_list_item")
data class CheckListItem(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var work: String = "",
    var unit: String = "",
    var count: Int = 0,
    @ColumnInfo(name = "check_list_item_id")
    var checkListItemId: Int = 0
)