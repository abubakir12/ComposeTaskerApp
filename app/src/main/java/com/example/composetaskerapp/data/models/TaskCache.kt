package com.example.composetaskerapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class TaskCache(
   @PrimaryKey val id: String,
    @ColumnInfo(name = "title")val title: String,
   @ColumnInfo(name = "time") val time: String,
   @ColumnInfo(name = "date") val date: String,
   @ColumnInfo(name = "category_id") val categoryId: String,
   @ColumnInfo(name = "category_color") val categoryColor: String,
)