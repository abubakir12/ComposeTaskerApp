package com.example.composetaskerapp.domain.repositories

import com.example.composetaskerapp.domain.models.Task
import com.example.composetaskerapp.domain.models.TaskCategory
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun fetchAllTaskFlow(): Flow<List<Task>>

    fun fetchAllTaskCategories(): List<TaskCategory>

    fun fetchById(taskId: String): Task

    fun fetchCategoryById(categoryId: String): TaskCategory

    fun fetchTasksByCategoryId(categoryId: String): List<Task>

    fun fetchTasksSizeByCategoryId(categoryId: String): Int

    fun addNewTask(task: Task): Boolean

    fun updateTask(task: Task)

    fun removeTaskById(id: String)

    fun removeTasksByIds(ids: List<String>)
    fun fetchAllTask(): Flow<List<Task>>
}