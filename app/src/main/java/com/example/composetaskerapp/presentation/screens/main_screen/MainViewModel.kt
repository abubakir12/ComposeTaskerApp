package com.example.composetaskerapp.presentation.screens.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetaskerapp.common.extensions.mapToTaskUi
import com.example.composetaskerapp.data.repositories.TaskCategoryRepositoryImpl
import com.example.composetaskerapp.data.repositories.TaskRepositoryImpl
import com.example.composetaskerapp.domain.repositories.TaskRepository
import com.example.composetaskerapp.domain.usecases.FetchAllTaskCategoryUseCase
import com.example.composetaskerapp.domain.usecases.FetchAllTaskUseCases
import com.example.composetaskerapp.domain.usecases.FetchTasksSizeByCategoryIdUseCase
import com.example.composetaskerapp.domain.usecases.RemoveTasksByIdsUseCase
import com.example.composetaskerapp.presentation.models.TaskUi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MainViewModel : ViewModel() {
    private val repository: TaskRepository = TaskRepositoryImpl()
    private val taskCategoryRepository = TaskCategoryRepositoryImpl()

    private val fetchAllTaskUseCases = FetchAllTaskUseCases(repository)
    private val removeTasksByIdsUseCase = RemoveTasksByIdsUseCase(repository)
    private val fetchTasksSizeByCategoryIdUseCase = FetchTasksSizeByCategoryIdUseCase(repository)

    private val fetchAllTaskCategoryUseCase = FetchAllTaskCategoryUseCase(taskCategoryRepository)

    var uiState by mutableStateOf(MainUiState())


    init {
        fetchAllTaskUseCases().onEach { tasks ->
            uiState = uiState.copy(tasks = tasks.map { it.mapToTaskUi() })
        }.launchIn(viewModelScope)

        fetchAllTaskCategoryUseCase()
            .map { taskCategories ->
                val taskCategoryAndCount = taskCategories.map { category ->
                    val count = fetchTasksSizeByCategoryIdUseCase(categoryId = category.id)
                    Pair(category, count)
                }
                taskCategoryAndCount
            }
            .onEach { taskCategories ->
                uiState = uiState.copy(taskCategories = taskCategories)
            }.launchIn(viewModelScope)
    }

    fun onSelectItem(task: TaskUi, isSelected: Boolean) {
        val selectedTasks = uiState.selectedTasks.toMutableSet()
        if (isSelected) {
            selectedTasks.add(task)
        } else {
            selectedTasks.remove(task)
        }
        uiState = uiState.copy(selectedTasks = selectedTasks)

    }

    fun onRemoveSelectedItems() {
        val removedTasks = uiState.selectedTasks
        val taskIds = removedTasks.map { task: TaskUi -> task.id }
        removeTasksByIdsUseCase(taskIds)
    }

    fun onSelectAllItems() {
        val selectedTask = uiState.selectedTasks.toMutableSet()
        selectedTask.addAll(uiState.tasks)
        uiState = uiState.copy(selectedTasks = selectedTask)
    }

    fun onUnSeSelectAllItems() {
        val selectedTask = uiState.selectedTasks.toMutableSet()
        selectedTask.clear()
        uiState = uiState.copy(selectedTasks = selectedTask)
    }
}




