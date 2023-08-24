package com.example.composetaskerapp.data.repositories

import com.example.composetaskerapp.common.extensions.mapToCache
import com.example.composetaskerapp.common.extensions.mapToTask
import com.example.composetaskerapp.domain.models.Task
import com.example.composetaskerapp.domain.models.TaskCategory
import com.example.composetaskerapp.domain.repositories.TaskRepository
import com.example.composetaskerapp.presentation.TaskApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl() : TaskRepository {

    private val dao = TaskApp.database.fetchTaskDao()

    override fun fetchAllTask(): Flow<List<Task>> {
        return dao.fetchAllTasksFlow().map { it -> it.map { it.mapToTask() } }
    }

    override fun fetchAllTaskFlow(): Flow<List<Task>> {
        return dao.fetchAllTasksFlow().map { it -> it.map { it.mapToTask() } }
    }

    override fun fetchAllTaskCategories(): List<TaskCategory> {
        TODO("Not yet implemented")
    }

    override fun fetchById(taskId: String): Task {
        TODO("Not yet implemented")
    }

    override fun fetchCategoryById(categoryId: String): TaskCategory {
        TODO("Not yet implemented")
    }

    override fun fetchTasksByCategoryId(categoryId: String): List<Task> {
        TODO("Not yet implemented")
    }

    override fun fetchTasksSizeByCategoryId(categoryId: String): Int {
        return dao.fetchTasksSizeByCategoryId(categoryId).size
    }

    override fun addNewTask(task: Task): Boolean {
        dao.addNewTask(task.mapToCache())
        return true
    }

    override fun updateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun removeTaskById(id: String) {
        TODO("Not yet implemented")
    }

    override fun removeTasksByIds(ids: List<String>) {
        dao.deleteTasksByIds(ids)
    }
}