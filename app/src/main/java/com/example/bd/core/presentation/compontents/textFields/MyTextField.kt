package com.example.bd.core.presentation.compontents.textFields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.DisabledTextFieldColor
import com.example.bd.core.presentation.theme.UnfocusedTextFieldColor
import com.example.bd.core.presentation.theme.White

@Composable
fun MyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    isError: Boolean = false,
    errorText: String = "",
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = {
            Text(
                text = labelText,
                fontFamily = AlegreyaFontFamily,
                color = UnfocusedTextFieldColor,
                fontSize = 15.sp
            )
        },
        supportingText = {
            if (isError) {
                Text(
                    text = errorText,
                    color = MaterialTheme.colorScheme.error,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 15.sp
                )
            }
        },
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        singleLine = maxLines == 1,
        textStyle = TextStyle(
            fontFamily = AlegreyaFontFamily,
            color = White,
            fontSize = 17.sp
        ),
        isError = isError,
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            unfocusedIndicatorColor = UnfocusedTextFieldColor,
            disabledIndicatorColor = DisabledTextFieldColor,
            unfocusedLabelColor = UnfocusedTextFieldColor,
            focusedLabelColor = White,
            focusedIndicatorColor = White
        ),
        modifier = modifier
    )
}