package com.example.composetaskerapp.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.composetaskerapp.data.models.TaskCategoryCache
import com.example.composetaskerapp.domain.models.TaskCategory
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskCategoryDao {

    @Insert
    fun addTaskCategory(taskCategory: TaskCategoryCache)


    @Query("DELETE FROM task_categories_table WHERE id = :id")
    fun deleteTaskCategoryById(id:String)

    @Query("SELECT * FROM task_categories_table")
    fun fetchAllTaskCategoriesFlow(): Flow<List<TaskCategoryCache>>

    @Query("SELECT * FROM task_categories_table WHERE id = :id")
    fun fetchTaskCategoryById(id: String):TaskCategoryCache

}