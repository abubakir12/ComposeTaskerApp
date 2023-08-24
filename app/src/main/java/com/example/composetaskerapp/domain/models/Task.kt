package com.example.composetaskerapp.domain.models

import java.util.UUID

data class Task(
    val id: String,
    val title: String,
    val time: String,
    val date: String,
    val categoryId: String,
    val categoryColor: String,
)


