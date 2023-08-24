package com.example.composetaskerapp.presentation.screens.add_task_category

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composetaskerapp.R
import com.example.composetaskerapp.common.extensions.SpacerHeight
import com.example.composetaskerapp.common.extensions.toHexCode
import com.example.composetaskerapp.presentation.screens.add_screens.AddTaskToolbar
import com.example.composetaskerapp.presentation.theme.ExtraMediumSpacing
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskCategoryScreen(
    modifier: Modifier = Modifier,
    uiState: AddTaskCategoryUiState,
    toastFlow: Flow<Int?>,
    saveButtonClick: () -> Unit,
    onCancelClick: () -> Unit,
    updateTitle: (String) -> Unit,
    updateColorCode: (String) -> Unit,
) {
    val context = LocalContext.current
    val toastMassageId = toastFlow.collectAsState(initial = null)
    LaunchedEffect(toastMassageId.value) {
        if (toastMassageId.value != null) {
            Toast.makeText(
                context,
                toastMassageId.value!!,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    var currentColor by remember {
        mutableStateOf(HsvColor.from(Color.Red))
    }
    var extraColors by remember {
        mutableStateOf(emptyList<HsvColor>())
    }
    var text by remember {
        mutableStateOf(uiState.title ?: "")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AddTaskToolbar(
            onSaveTask = saveButtonClick,
            onCancelClick = onCancelClick
        )
        SpacerHeight(dp = ExtraMediumSpacing)
        TextField(
            value = text,
            onValueChange = { value ->
                text = value
                updateTitle(value)
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.headlineMedium,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.what),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = if (isSystemInDarkTheme()) Color.White
                        else colorResource(id = R.color.large_gray)
                    )
                )

            }
        )
        Spacer(modifier = modifier.weight(1f))
        HarmonyColorPicker(
            modifier = modifier.size(400.dp),
            harmonyMode = ColorHarmonyMode.ANALOGOUS,
            color = currentColor,
            showBrightnessBar = false
        ) { color ->
            currentColor = color
            extraColors = color.getColors(colorHarmonyMode = ColorHarmonyMode.ANALOGOUS)
            val colorCode = currentColor.toColor().toHexCode()
            updateColorCode(colorCode)
        }
        Spacer(modifier = modifier.weight(1f))
    }
}



