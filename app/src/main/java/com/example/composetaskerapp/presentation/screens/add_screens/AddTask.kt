package com.example.composetaskerapp.presentation.screens.add_screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition")
@Destination(start = false)
@Composable
fun AddTask(
    navigator: DestinationsNavigator
) {
    val viewModel : AddTaskViewModel = viewModel()
    viewModel.navigateUPTOFlow.filterNotNull().onEach {
        navigator.navigateUp()
    }.launchIn(rememberCoroutineScope())

    AddTaskScreen(
        uiState = viewModel.uiState,
        onSaveTask =  viewModel::addNewTask,
        toastFlow = viewModel.toastFlow,
        onCancelClick = { navigator.navigateUp() },
        updateSelectedTitle = viewModel::updateSelectedTitle,
        updateSelectedTime = viewModel::updateSelectedTime,
        updateSelectedDate = viewModel::updateSelectedDate,
        updateSelectedCategory = viewModel::updateSelectedCategory,
    )
}