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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bd.core.domain.models.Gender
import com.example.bd.core.presentation.compontents.MyTextField
import com.example.bd.core.presentation.compontents.NavigationItem
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.SubtitleTextColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.utils.getGenderString
import com.example.bd.registration.domain.validation.UserValidation
import com.example.bd.registration.presentation.components.MyExposedDropDownMenu
import com.example.db.R

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
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
                fontSize = 26.sp,
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

            var name by remember { mutableStateOf("") }
            var isFirstInteractionWithNameTextField by remember { mutableStateOf(true) }

            NameTextField(
                name = name,
                isFirstInteraction = isFirstInteractionWithNameTextField,
                userValidation = UserValidation,
                onValueChanged = {
                    name = it
                    isFirstInteractionWithNameTextField = false
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            var age by remember { mutableStateOf("") }
            var isFirstInteractionWithAgeTextField by remember { mutableStateOf(true) }

            AgeTextField(
                age = age,
                isFirstInteraction = isFirstInteractionWithAgeTextField,
                userValidation = UserValidation,
                onValueChanged = {
                    age = it
                    isFirstInteractionWithAgeTextField = false
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            val genders = mutableMapOf<Gender, String>()
            Gender.entries.forEach {
                genders[it] = getGenderString(LocalContext.current, it)
            }

            var gender by remember { mutableStateOf<Gender?>(null) }
            var isFirstInteractionWithGenderMenu by remember { mutableStateOf(true) }

            GenderExposedDropDownMenu(
                genders = genders,
                selectedGender = gender,
                onValueChanged = {
                    gender = it
                    isFirstInteractionWithGenderMenu = false
                },
                isFirstInteraction = isFirstInteractionWithGenderMenu,
                userValidation = UserValidation
            )

            Spacer(modifier = Modifier.height(40.dp))

            MyButton(
                text = stringResource(id = R.string.confirm),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (
                        UserValidation.isNameValid(name) &&
                        UserValidation.isAgeValid(age) &&
                        UserValidation.isGenderValid(gender)
                    ) {
                        viewModel.onEvent(
                            RegisterEvent.OnConfirmPressed(
                                name = name,
                                age = age.toInt(),
                                gender = gender!!
                            )
                        )
                        navController.navigate(NavigationItem.Home.route)
                    } else {
                        isFirstInteractionWithNameTextField = false
                        isFirstInteractionWithAgeTextField = false
                        isFirstInteractionWithGenderMenu = false
                    }
                }
            )
        }
    }
}

@Composable
private fun NameTextField(
    name: String,
    isFirstInteraction: Boolean,
    userValidation: UserValidation,
    onValueChanged: (String) -> Unit
) {
    var isNameValid by remember { mutableStateOf(false) }

    MyTextField(
        value = name,
        isError = !(isFirstInteraction || isNameValid),
        errorText = stringResource(R.string.fill_in_the_field),
        onValueChange = {
            isNameValid = userValidation.isNameValid(it)
            onValueChanged(it)
        },
        labelText = stringResource(id = R.string.name),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun AgeTextField(
    age: String,
    isFirstInteraction: Boolean,
    userValidation: UserValidation,
    onValueChanged: (String) -> Unit
) {
    var isAgeValid by remember { mutableStateOf(false) }

    MyTextField(
        value = age,
        isError = !(isFirstInteraction || isAgeValid),
        errorText = stringResource(R.string.fill_in_the_field),
        onValueChange = {
            isAgeValid = userValidation.isAgeValid(it)
            onValueChanged(it)
        },
        labelText = stringResource(id = R.string.age),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Suppress("UNCHECKED_CAST")
@Composable
private fun GenderExposedDropDownMenu(
    genders: Map<Gender, String>,
    selectedGender: Gender?,
    isFirstInteraction: Boolean,
    userValidation: UserValidation,
    onValueChanged: (Gender) -> Unit
) {
    var isGenderValid by remember { mutableStateOf(false) }

    MyExposedDropDownMenu(
        items = genders as Map<Any, String>,
        selectedItem = selectedGender as Any?,
        labelText = stringResource(id = R.string.gender),
        errorText = stringResource(R.string.fill_in_the_field),
        isError = !(isFirstInteraction || isGenderValid),
        onMenuItemClicked = {
            isGenderValid = userValidation.isGenderValid(it as Gender)

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

