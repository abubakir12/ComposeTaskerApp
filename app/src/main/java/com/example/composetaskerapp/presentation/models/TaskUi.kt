package com.example.composetaskerapp.presentation.models

import com.example.composetaskerapp.domain.models.Task
import java.util.UUID

data class TaskUi(
    var id: String,
    val title: String,
    val time: String,
    val date: String,
    val categoryId: String,
    var isSelected: Boolean = false,
    val categoryColor: String
) {
    companion object {
        val preview = TaskUi(
            id = UUID.randomUUID().toString(),
            time = "22:00",
            date = "04.05.2023",
            title = "Go to School",
            categoryId = "",
            categoryColor = ""
        )
        val previews = listOf(
            preview.copy(
                id = UUID.randomUUID().toString(),
            ),
            preview.copy(
                id = UUID.randomUUID().toString(),
            ),
            preview.copy(
                id = UUID.randomUUID().toString(),
            ),
            preview.copy(
                id = UUID.randomUUID().toString(),
            ),
            preview.copy(
                id = UUID.randomUUID().toString(),
            ),
            preview.copy(
                id = UUID.randomUUID().toString(),
            ),
            preview.copy(
                id = UUID.randomUUID().toString(),
            )
        )
    }
}
