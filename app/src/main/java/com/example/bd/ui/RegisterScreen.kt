package com.example.bd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bd.domain.model.User
import com.example.bd.ui.common.MyButton
import com.example.bd.ui.theme.AlegreyaFontFamily
import com.example.bd.ui.theme.BdTheme
import com.example.bd.ui.theme.SubtitleTextColor
import com.example.bd.ui.theme.UnfocusedTextFieldColor
import com.example.bd.ui.theme.White
import com.example.db.R
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.leaves),
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.BottomCenter
                )
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.welcome_screens_space))
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_emotion),
                contentDescription = stringResource(id = R.string.icon_emotion_content_description),
                modifier = Modifier
                    .size(
                        dimensionResource(id = R.dimen.register_screen_icon_size),
                        dimensionResource(id = R.dimen.register_screen_icon_size)
                    )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(id = R.string.your_data_title),
                fontWeight = FontWeight.Bold,
                fontFamily = AlegreyaFontFamily,
                fontSize = 27.sp,
                color = White,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.your_data_text),
                fontSize = 19.sp,
                fontFamily = AlegreyaFontFamily,
                color = SubtitleTextColor,
            )

            Spacer(modifier = Modifier.height(50.dp))

            val nameState = remember { mutableStateOf("") }
            val isNameValid = remember { mutableStateOf(false) }
            val isFirstInteractionWithNameTextField = remember { mutableStateOf(true) }
            val isNameTextFieldError =
                { !(isFirstInteractionWithNameTextField.value || isNameValid.value) }

            CustomTextField(
                state = nameState,
                isError = isNameTextFieldError,
                errorText = stringResource(R.string.fill_in_the_field),
                onValueChange = {
                    nameState.value = it
                    isFirstInteractionWithNameTextField.value = false
                    isNameValid.value = nameState.value.isNotBlank()
                },
                placeholderText = stringResource(id = R.string.name),
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(10.dp))

            val ageState = remember { mutableStateOf("") }
            val isAgeValid = remember { mutableStateOf(false) }
            val isFirstInteractionWithAgeTextField = remember { mutableStateOf(true) }

            CustomTextField(
                state = ageState,
                isError = { !(isFirstInteractionWithAgeTextField.value || isAgeValid.value) },
                errorText = stringResource(R.string.fill_in_the_field),
                onValueChange = {
                    ageState.value = it
                    isFirstInteractionWithAgeTextField.value = false
                    isAgeValid.value = ageState.value.isNotBlank()
                },
                placeholderText = stringResource(id = R.string.age),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            val genders = listOf(
                stringResource(id = R.string.male_gender),
                stringResource(id = R.string.female_gender)
            )
            val genderState = remember { mutableStateOf("") }

            val isGenderValid = remember { mutableStateOf(false) }
            val isFirstInteractionWithGenderMenu = remember { mutableStateOf(true) }

            CustomExposedDropDownMenu(
                items = genders,
                selectedTextState = genderState,
                isError = { !(isFirstInteractionWithGenderMenu.value || isGenderValid.value) },
                errorText = stringResource(R.string.fill_in_the_field),
                onMenuItemClicked = {
                    genderState.value = it
                    isFirstInteractionWithGenderMenu.value = false
                    isGenderValid.value = genderState.value.isNotBlank()
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            val coroutineScope = rememberCoroutineScope()

            MyButton(
                text = stringResource(id = R.string.confirm),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (isNameValid.value && isAgeValid.value && isGenderValid.value) {
                        coroutineScope.launch {
                            viewModel.registerUser(
                                User(
                                    name = nameState.value,
                                    age = ageState.value.toInt(),
                                    gender = genderState.value
                                )
                            )
                            navController.navigate(NavigationItem.Home.route)
                        }
                    } else {
                        isFirstInteractionWithNameTextField.value = false
                        isFirstInteractionWithAgeTextField.value = false
                        isFirstInteractionWithGenderMenu.value = false
                    }
                }
            )
        }
    }
}

@Composable
private fun CustomTextField(
    state: MutableState<String>,
    isError: () -> Boolean,
    errorText: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    modifier: Modifier = Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomExposedDropDownMenu(
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


@Preview
@Composable
private fun Preview() {
    BdTheme {
        RegisterScreen(rememberNavController())
    }
}

