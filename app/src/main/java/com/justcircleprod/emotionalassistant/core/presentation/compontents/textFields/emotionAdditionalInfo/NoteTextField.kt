package com.justcircleprod.emotionalassistant.core.presentation.compontents.textFields.emotionAdditionalInfo

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.justcircleprod.emotionalassistant.core.presentation.compontents.textFields.MyTextField
import com.justcircleprod.emotionalassistant.R

@Composable
fun NoteTextField(
    note: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    MyTextField(
        value = note,
        enabled = enabled,
        maxLines = 3,
        labelText = stringResource(R.string.note),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        ),
        onValueChange = onValueChange,
        modifier = modifier,
    )
}