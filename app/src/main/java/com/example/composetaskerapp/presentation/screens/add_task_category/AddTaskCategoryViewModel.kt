package com.example.composetaskerapp.presentation.screens.add_task_category

import android.icu.text.CaseMap.Title
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.composetaskerapp.R
import com.example.composetaskerapp.data.repositories.TaskCategoryRepositoryImpl
import com.example.composetaskerapp.domain.models.TaskCategory
import com.example.composetaskerapp.domain.usecases.AddTaskCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class AddTaskCategoryViewModel : ViewModel() {

    private val _toastFlow = MutableStateFlow<Int?>(null)
    val toastFlow = _toastFlow.asStateFlow()

    private val _navigateUpTOFlow = MutableStateFlow<Unit?>(null)
    val navigateUPTOFlow = _navigateUpTOFlow.asStateFlow()


    var uiState by mutableStateOf(AddTaskCategoryUiState())
    private val repository = TaskCategoryRepositoryImpl()
    private val addTaskCategoryUseCase = AddTaskCategoryUseCase(repository)

    fun updateTitle(title: String) {
        uiState = uiState.copy(title = title)
    }

    fun updateColorCode(colorCode: String) {
        uiState = uiState.copy(colorCode = colorCode)

    }

    fun saveButtonOnClick() {
        when {
            uiState.title == null -> {
                _toastFlow.tryEmit(R.string.error_empty_title)
            }

            uiState.colorCode == null -> {
                _toastFlow.tryEmit(R.string.error_empty_color)

            }
            else -> {
                val category  =TaskCategory(
                    id = UUID.randomUUID().toString(),
                    title = uiState.title!!,
                    colorCode = uiState.colorCode!!
                )
                addTaskCategoryUseCase(category)
                _navigateUpTOFlow.tryEmit(Unit)
            }
        }
    }
}