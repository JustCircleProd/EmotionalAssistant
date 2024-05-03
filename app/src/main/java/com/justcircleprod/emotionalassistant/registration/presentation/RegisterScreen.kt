package com.justcircleprod.emotionalassistant.registration.presentation

import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.textFields.MyTextField
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.SubtitleTextColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.R
import kotlinx.coroutines.launch

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
                .padding(horizontal = dimensionResource(id = R.dimen.registration_screens_space))
                .animateContentSize()
        ) {
            Spacer(Modifier.height(dimensionResource(id = R.dimen.registration_screens_space)))

            Image(
                painter = painterResource(id = R.drawable.icon_emotion),
                contentDescription = stringResource(id = R.string.icon_emotion_content_description),
                modifier = Modifier
                    .size(
                        dimensionResource(id = R.dimen.register_screen_icon_size),
                        dimensionResource(id = R.dimen.register_screen_icon_size)
                    )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.lets_get_acquainted),
                fontWeight = FontWeight.Bold,
                fontFamily = AlegreyaFontFamily,
                fontSize = 26.sp,
                color = White,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.lets_get_acquainted_text),
                fontSize = 19.sp,
                fontFamily = AlegreyaFontFamily,
                color = SubtitleTextColor,
            )

            Spacer(modifier = Modifier.height(40.dp))

            var name by remember { mutableStateOf("") }
            var isFirstInteractionWithNameTextField by remember { mutableStateOf(true) }

            NameTextField(
                name = name,
                isFirstInteraction = isFirstInteractionWithNameTextField,
                onValueChanged = {
                    name = it
                    isFirstInteractionWithNameTextField = false
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            val scope = rememberCoroutineScope()

            MyButton(
                text = stringResource(id = R.string.confirm),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (name.isNotEmpty()) {
                        viewModel.onEvent(
                            RegisterEvent.OnConfirmPressed(
                                name = name
                            )
                        )

                        scope.launch {
                            viewModel.isUserRegistered.collect {
                                if (it) {
                                    navController.navigate(NavigationItem.Home.route)
                                }
                            }
                        }
                    } else {
                        isFirstInteractionWithNameTextField = false
                    }
                }
            )

            Spacer(Modifier.height(dimensionResource(id = R.dimen.registration_screens_space)))
        }
    }
}

@Composable
private fun NameTextField(
    name: String,
    isFirstInteraction: Boolean,
    onValueChanged: (String) -> Unit
) {
    var isNameValid by remember { mutableStateOf(false) }

    MyTextField(
        value = name,
        isError = !(isFirstInteraction || isNameValid),
        errorText = stringResource(R.string.fill_in_the_field),
        onValueChange = {
            isNameValid = it.isNotEmpty()
            onValueChanged(it)
        },
        labelText = stringResource(id = R.string.name),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
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
                    .padding(horizontal = dimensionResource(id = R.dimen.registration_screens_space))
                    .animateContentSize()
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.registration_screens_space)))

                Image(
                    painter = painterResource(id = R.drawable.icon_emotion),
                    contentDescription = stringResource(id = R.string.icon_emotion_content_description),
                    modifier = Modifier
                        .size(
                            dimensionResource(id = R.dimen.register_screen_icon_size),
                            dimensionResource(id = R.dimen.register_screen_icon_size)
                        )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(id = R.string.lets_get_acquainted),
                    fontWeight = FontWeight.Bold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    color = White,
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = stringResource(id = R.string.lets_get_acquainted_text),
                    fontSize = 19.sp,
                    fontFamily = AlegreyaFontFamily,
                    color = SubtitleTextColor,
                )

                Spacer(modifier = Modifier.height(40.dp))

                var name by remember { mutableStateOf("Имя") }
                var isFirstInteractionWithNameTextField by remember { mutableStateOf(true) }

                NameTextField(
                    name = name,
                    isFirstInteraction = isFirstInteractionWithNameTextField,
                    onValueChanged = {
                        name = it
                        isFirstInteractionWithNameTextField = false
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))

                MyButton(
                    text = stringResource(id = R.string.confirm),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    }
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.registration_screens_space)))
            }
        }
    }
}

