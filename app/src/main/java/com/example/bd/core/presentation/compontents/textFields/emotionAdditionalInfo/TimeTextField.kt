package com.example.bd.core.presentation.compontents.textFields.emotionAdditionalInfo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.bd.core.presentation.compontents.dialogs.MyTimePickerDialog
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.DisabledTextFieldColor
import com.example.bd.core.presentation.theme.UnfocusedTextFieldColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.presentation.util.formatLocalTime
import com.example.db.R
import java.time.LocalTime

@Composable
fun TimeTextField(
    time: LocalTime,
    onValueChange: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    clickable: Boolean = true
) {
    val focusManager = LocalFocusManager.current

    var showTimePickerDialog by remember {
        mutableStateOf(false)
    }

    if (showTimePickerDialog) {
        MyTimePickerDialog(
            initialTime = time,
            onTimeSelected = onValueChange,
            onDismiss = { showTimePickerDialog = false }
        )
    }

    TextField(
        value = formatLocalTime(time),
        onValueChange = {},
        enabled = false,
        readOnly = true,
        label = {
            Text(
                text = stringResource(R.string.time),
                fontFamily = AlegreyaFontFamily,
                color = UnfocusedTextFieldColor,
                fontSize = 15.sp
            )
        },
        textStyle = TextStyle(
            fontFamily = AlegreyaFontFamily,
            color = White,
            fontSize = 17.sp
        ),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            unfocusedIndicatorColor = UnfocusedTextFieldColor,
            disabledIndicatorColor = if (clickable) UnfocusedTextFieldColor else DisabledTextFieldColor,
            unfocusedLabelColor = UnfocusedTextFieldColor,
            focusedLabelColor = White,
            focusedIndicatorColor = White
        ),
        modifier = modifier
            .run {
                if (clickable) {
                    clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        focusManager.clearFocus()
                        showTimePickerDialog = true
                    }
                } else {
                    this
                }
            }
    )
}