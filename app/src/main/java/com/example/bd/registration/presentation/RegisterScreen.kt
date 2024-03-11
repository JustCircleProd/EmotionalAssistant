package com.example.bd.registration.presentation

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bd.core.domain.models.User
import com.example.bd.core.presentation.appNavigation.NavigationItem
import com.example.bd.core.presentation.compontents.MyButton
import com.example.bd.registration.domain.validation.UserValidation
import com.example.bd.registration.presentation.components.MyExposedDropDownMenu
import com.example.bd.registration.presentation.components.MyTextField
import com.example.bd.ui.theme.AlegreyaFontFamily
import com.example.bd.ui.theme.BdTheme
import com.example.bd.ui.theme.SubtitleTextColor
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
            val isFirstInteractionWithNameTextField = remember { mutableStateOf(true) }

            NameTextField(
                nameState = nameState,
                onValueChanged = {
                    nameState.value = it
                    isFirstInteractionWithNameTextField.value = false
                },
                isFirstInteraction = isFirstInteractionWithNameTextField,
                userValidation = UserValidation
            )

            Spacer(modifier = Modifier.height(10.dp))


            val ageState = remember { mutableStateOf("") }
            val isFirstInteractionWithAgeTextField = remember { mutableStateOf(true) }

            AgeTextField(
                ageState = ageState,
                onValueChanged = {
                    ageState.value = it
                    isFirstInteractionWithAgeTextField.value = false
                },
                isFirstInteraction = isFirstInteractionWithAgeTextField,
                userValidation = UserValidation
            )

            Spacer(modifier = Modifier.height(10.dp))

            val genders = listOf(
                stringResource(id = R.string.male_gender),
                stringResource(id = R.string.female_gender)
            )
            val genderState = remember { mutableStateOf("") }
            val isFirstInteractionWithGenderMenu = remember { mutableStateOf(true) }

            GenderExposedDropDownMenu(
                genders = genders,
                genderState = genderState,
                onValueChanged = {
                    genderState.value = it
                    isFirstInteractionWithGenderMenu.value = false
                },
                isFirstInteraction = isFirstInteractionWithGenderMenu,
                userValidation = UserValidation
            )

            Spacer(modifier = Modifier.height(40.dp))


            val coroutineScope = rememberCoroutineScope()

            MyButton(
                text = stringResource(id = R.string.confirm),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (UserValidation.isNameValid(nameState.value) && UserValidation.isAgeValid(
                            ageState.value
                        ) && UserValidation.isGenderValid(genders, genderState.value)
                    ) {
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
private fun NameTextField(
    nameState: MutableState<String>,
    onValueChanged: (String) -> Unit,
    isFirstInteraction: MutableState<Boolean>,
    userValidation: UserValidation
) {
    var isNameValid by remember { mutableStateOf(false) }

    val isNameTextFieldError =
        { !(isFirstInteraction.value || isNameValid) }

    MyTextField(
        state = nameState,
        isError = isNameTextFieldError,
        errorText = stringResource(R.string.fill_in_the_field),
        onValueChange = {
            isNameValid = userValidation.isNameValid(it)
            if (isNameValid) {
                onValueChanged(it)
            }

        },
        placeholderText = stringResource(id = R.string.name),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun AgeTextField(
    ageState: MutableState<String>,
    onValueChanged: (String) -> Unit,
    isFirstInteraction: MutableState<Boolean>,
    userValidation: UserValidation
) {
    var isAgeValid by remember { mutableStateOf(false) }

    MyTextField(
        state = ageState,
        isError = { !(isFirstInteraction.value || isAgeValid) },
        errorText = stringResource(R.string.fill_in_the_field),
        onValueChange = {
            isAgeValid = userValidation.isAgeValid(it)

            if (isAgeValid) {
                onValueChanged(it)
            }
        },
        placeholderText = stringResource(id = R.string.age),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun GenderExposedDropDownMenu(
    genders: List<String>,
    genderState: MutableState<String>,
    onValueChanged: (String) -> Unit,
    isFirstInteraction: MutableState<Boolean>,
    userValidation: UserValidation
) {
    var isGenderValid by remember { mutableStateOf(false) }

    MyExposedDropDownMenu(
        items = genders,
        selectedTextState = genderState,
        isError = { !(isFirstInteraction.value || isGenderValid) },
        errorText = stringResource(R.string.fill_in_the_field),
        onMenuItemClicked = {
            isGenderValid = userValidation.isGenderValid(genders, it)

            if (isGenderValid) {
                onValueChanged(it)
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
        RegisterScreen(rememberNavController())
    }
}

