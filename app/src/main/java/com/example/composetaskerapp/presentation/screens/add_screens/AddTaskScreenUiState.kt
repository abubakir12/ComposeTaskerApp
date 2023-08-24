package com.example.composetaskerapp.presentation.screens.add_screens

import com.example.composetaskerapp.domain.models.TaskCategory

data class AddTaskScreenUiState(
    val selectedCategory: TaskCategory? = null,
    val selectedDate: String? = null,
    val selectedTime: String? = null,
    val title:String? = null,
    val tasksCategories: List<TaskCategory> = emptyList()
)
