package com.example.bd.registration.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.UnfocusedTextFieldColor
import com.example.bd.core.presentation.theme.White

@Composable
fun MyTextField(
    state: MutableState<String>,
    isError: () -> Boolean,
    errorText: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    TextField(
        value = state.value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholderText,
                fontFamily = AlegreyaFontFamily,
                color = UnfocusedTextFieldColor,
                fontSize = 16.sp
            )
        },
        supportingText = {
            if (isError()) {
                Text(
                    text = errorText,
                    color = Color.Red,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 15.sp
                )
            }
        },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        maxLines = 1,
        textStyle = TextStyle(
            fontFamily = AlegreyaFontFamily,
            color = White,
            fontSize = 16.sp
        ),
        isError = isError(),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            unfocusedIndicatorColor = UnfocusedTextFieldColor,
            disabledIndicatorColor = UnfocusedTextFieldColor,
            unfocusedLabelColor = UnfocusedTextFieldColor,
            focusedLabelColor = White,
            focusedIndicatorColor = White
        ),
        modifier = modifier
    )
}