package com.example.composetaskerapp.common.extensions

import com.example.composetaskerapp.data.models.TaskCache
import com.example.composetaskerapp.domain.models.Task


fun TaskCache.mapToTask():Task{
    return Task(
        id = id,
        title = title,
        time = time,
        date = date,
        categoryId = categoryId,
        categoryColor = categoryColor

    )
}