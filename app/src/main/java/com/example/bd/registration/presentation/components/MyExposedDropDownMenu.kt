package com.example.bd.registration.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.UnfocusedTextFieldColor
import com.example.bd.core.presentation.theme.White
import com.example.db.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExposedDropDownMenu(
    items: Map<Any, String>,
    selectedItem: Any?,
    onMenuItemClicked: (Any) -> Unit,
    labelText: String,
    isError: Boolean = false,
    errorText: String = ""
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
            value = items[selectedItem] ?: "",
            onValueChange = {},
            isError = isError,
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
            label = {
                Text(
                    text = labelText,
                    color = UnfocusedTextFieldColor,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 15.sp
                )
            },
            trailingIcon = {
                if (expanded) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropUp,
                        contentDescription = stringResource(id = R.string.icon_arrow_drop_up_content_description),
                        tint = White,
                        modifier = Modifier.size(24.dp)

                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = stringResource(id = R.string.icon_arrow_drop_down_content_description),
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            singleLine = true,
            maxLines = 1,
            readOnly = true,
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
                        Text(
                            text = item.value,
                            fontFamily = AlegreyaFontFamily,
                            color = White,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        expanded = false
                        onMenuItemClicked(item.key)
                    }
                )
            }
        }
    }
}