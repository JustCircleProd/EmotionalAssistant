package com.example.bd.registration.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.bd.ui.theme.AlegreyaFontFamily
import com.example.bd.ui.theme.UnfocusedTextFieldColor
import com.example.bd.ui.theme.White
import com.example.db.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExposedDropDownMenu(
    items: List<String>,
    selectedTextState: MutableState<String>,
    onMenuItemClicked: (String) -> Unit,
    isError: () -> Boolean,
    errorText: String
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedTextState.value,
            onValueChange = {},
            isError = isError(),
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
            placeholder = {
                Text(
                    text = stringResource(id = R.string.gender),
                    color = UnfocusedTextFieldColor,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 16.sp
                )
            },
            trailingIcon = {
                if (expanded) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_arrow_drop_up),
                        contentDescription = stringResource(id = R.string.icon_arrow_drop_up_content_description)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.icon_arrow_drop_down),
                        contentDescription = stringResource(id = R.string.icon_arrow_drop_down_content_description)
                    )
                }
            },
            singleLine = true,
            maxLines = 1,
            readOnly = true,
            textStyle = TextStyle(
                fontFamily = AlegreyaFontFamily,
                color = White,
                fontSize = 16.sp
            ),
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
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = item, fontFamily = AlegreyaFontFamily)
                    },
                    onClick = {
                        expanded = false
                        onMenuItemClicked(item)
                    }
                )
            }
        }
    }
}