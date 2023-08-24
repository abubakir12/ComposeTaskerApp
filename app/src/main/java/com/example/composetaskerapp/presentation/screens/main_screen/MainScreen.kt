@file:Suppress("UNUSED_EXPRESSION")

package com.example.composetaskerapp.presentation.screens.main_screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetaskerapp.R
import com.example.composetaskerapp.domain.models.TaskCategory
import com.example.composetaskerapp.presentation.components.FABComponent
import com.example.composetaskerapp.presentation.components.TaskCategoryItem
import com.example.composetaskerapp.presentation.components.TaskItem
import com.example.composetaskerapp.common.extensions.SpacerHeight
import com.example.composetaskerapp.common.extensions.SpacerWidth
import com.example.composetaskerapp.presentation.models.TaskUi
import com.example.composetaskerapp.presentation.theme.ComposeTaskerAppTheme
import com.example.composetaskerapp.presentation.theme.ExtraMediumSpacing
import com.example.composetaskerapp.presentation.theme.LargeSpacing
import com.example.composetaskerapp.presentation.theme.MediumSpacing
import com.example.composetaskerapp.presentation.theme.SmallSpacing

@Preview
@Composable
fun MainScreenPreview() {
    ComposeTaskerAppTheme {
        MainScreen(
            onTaskClick = {},
            onTaskCategoryClick = {},
            uiState = MainUiState(tasks = TaskUi.previews),
            onSelectItem = { _, _ -> },
            onRemoveSelectedItems = {},
            onSelectAllItems = {},
            onUnSeSelectAllItems = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    uiState: MainUiState,
    onTaskClick: () -> Unit,
    onTaskCategoryClick: () -> Unit,
    onSelectItem: (TaskUi, Boolean) -> Unit,
    onRemoveSelectedItems: () -> Unit,
    onSelectAllItems: () -> Unit,
    onUnSeSelectAllItems: () -> Unit,
) {

    Scaffold(
        floatingActionButton = {
            FABComponent(
                onTaskClick = onTaskClick,
                onTaskCategoryClick =onTaskCategoryClick
            )
        }
    ) { innerPaddings ->
        LazyColumn(
            modifier =
            modifier
                .padding(innerPaddings)
                .fillMaxSize(),
        ) {
            item {
                MainScreenHeader(
                    onRemoveSelectedItems = onRemoveSelectedItems,
                    onUnSeSelectAllItems = onUnSeSelectAllItems,
                    onSelectAllItems = onSelectAllItems
                )
            }

            items(
                items = uiState.tasks,
                key = { item -> item.id + item.isSelected.hashCode() }
            ) { task ->
                TaskItem(
                    task = task,
                    onClick = {},
                    onSelected = onSelectItem,
                    isSelected = uiState.selectedTasks.contains(task)
                )
                Spacer(modifier = modifier.height(LargeSpacing))
            }

            items(
                items = uiState.taskCategories,
                key = { item -> item.first.id }
            ) { categoryAndSize ->
                val(category,size) = categoryAndSize

                Column(
                    modifier = modifier
                        .padding(
                            start = 60.dp,
                            end = MediumSpacing
                        )
                ) {
                    SpacerHeight(dp = SmallSpacing)
                    TaskCategoryItem(
                        category = category,
                        count = size
                    )
                    SpacerHeight(dp = SmallSpacing)
                }
            }
        }
    }
}

@Composable
fun MainScreenHeader(
    modifier: Modifier = Modifier,
    onRemoveSelectedItems: () -> Unit,
    onSelectAllItems: () -> Unit,
    onUnSeSelectAllItems: () -> Unit,
) {
    var isDropDownVisible by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .padding(vertical = LargeSpacing)
            .fillMaxWidth()
    ) {
        Spacer(modifier = modifier.width(60.dp))
        Text(
            text = stringResource(id = R.string.today),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = modifier.weight(1f))
        Column {

            IconButton(onClick = { isDropDownVisible = !isDropDownVisible }) {
                Icon(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = null,
                    tint = colorResource(id = R.color.blue)
                )
            }
            OptionsDropDownMenu(
                isVisible = isDropDownVisible,
                onDismissRequest = { isDropDownVisible = false },
                onRemoveItems = {
                    onRemoveSelectedItems()
                    isDropDownVisible = false
                },
                onSelectAllItems = {
                    onSelectAllItems()
                    isDropDownVisible = false
                },
                onUnselectAllItems = {
                    onUnSeSelectAllItems()
                    isDropDownVisible = false
                }

            )
        }

        Spacer(modifier = modifier.width(ExtraMediumSpacing))

    }
}

@Composable
fun OptionsDropDownMenu(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveItems: () -> Unit,
    onSelectAllItems: () -> Unit,
    onUnselectAllItems: () -> Unit
) {
    DropdownMenu(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = MediumSpacing),
        expanded = isVisible,
        onDismissRequest = onDismissRequest
    ) {


        DropDownOptionItem(
            textId = R.string.select_all_items,
            iconId = R.drawable.marked,
            onClick = onSelectAllItems
        )
        Divider()
        DropDownOptionItem(
            textId = R.string.unselected_all_items,
            iconId = R.drawable.unmarked,
            onClick = onUnselectAllItems
        )
        Divider()
        DropDownOptionItem(
            textId = R.string.remove_selected_items,
            iconId = R.drawable.trash,
            onClick = onRemoveItems,
            isDeleteMenu = true
        )
    }
}


@Composable
fun DropDownOptionItem(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    @DrawableRes iconId: Int,
    onClick: () -> Unit,
    isDeleteMenu: Boolean = false
) {
    val contentColor = if (isDeleteMenu) colorResource(id = R.color.red)
    else colorResource(id = R.color.blue)

    DropdownMenuItem(
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = modifier.size(18.dp),
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = contentColor
                )
                SpacerWidth(dp = LargeSpacing)
                Text(
                    text = stringResource(id = textId),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = contentColor
                    )
                )
            }
            Spacer(modifier = modifier.height(MediumSpacing))
        },
        onClick = onClick
    )
}