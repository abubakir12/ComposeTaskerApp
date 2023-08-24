package com.example.composetaskerapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_categories_table")
data class TaskCategoryCache(
  @PrimaryKey  val id:String,
    val title:String,
    val colorCode:String,
)
