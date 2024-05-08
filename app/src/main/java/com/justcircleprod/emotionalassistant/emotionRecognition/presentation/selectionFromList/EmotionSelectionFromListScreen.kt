package com.justcircleprod.emotionalassistant.emotionRecognition.presentation.selectionFromList

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionName
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.BackButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.OptionButton
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.getEmotionNameString
import com.justcircleprod.emotionalassistant.emotionRecognition.presentation.viewModel.EmotionRecognitionEvent
import com.justcircleprod.emotionalassistant.emotionRecognition.presentation.viewModel.EmotionRecognitionViewModel
import kotlinx.coroutines.launch

@Composable
fun EmotionSelectionFromListScreen(
    navController: NavHostController,
    viewModel: EmotionRecognitionViewModel,
    returnRoute: String?
) {
    val onBackPressed = {
        if (viewModel.imageBitmap.value == null) {
            viewModel.onEvent(EmotionRecognitionEvent.OnBackPressed)
        }

        navController.popBackStack()
    }

    BackHandler {
        onBackPressed()
    }

    Surface {
        val context = LocalContext.current

        Column {
            BackButton(
                onClick = { onBackPressed() },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
            )

            if (returnRoute == null) return@Surface

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

                Text(
                    text = stringResource(R.string.select_your_emotion),
                    fontWeight = FontWeight.Bold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    color = White,
                )

                Spacer(Modifier.height(40.dp))

                val emotions = EmotionName.entries
                val emotionListItems = mutableMapOf<EmotionName, String>()

                emotions.forEach {
                    emotionListItems[it] = getEmotionNameString(LocalContext.current, it)
                }

                var selectedEmotion by rememberSaveable { mutableStateOf(viewModel.recognizedEmotion.value) }

                val onClick: (Any) -> Unit = {
                    selectedEmotion = it as EmotionName
                }

                emotionListItems.onEachIndexed { index, entry ->
                    OptionButton(
                        value = entry.key,
                        text = entry.value,
                        selected = selectedEmotion == entry.key,
                        onClick = onClick
                    )

                    if (index < emotionListItems.size - 1) {
                        Spacer(Modifier.height(8.dp))
                    }
                }

                Spacer(Modifier.height(50.dp))

                val scope = rememberCoroutineScope()

                MyButton(
                    text = stringResource(id = R.string.confirm),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.recognizedEmotion.value = selectedEmotion

                        if (viewModel.emotionIdToUpdate != null) {
                            viewModel.onEvent(
                                EmotionRecognitionEvent.OnEmotionResultConfirmedToUpdate(
                                    context
                                )
                            )

                            scope.launch {
                                viewModel.savedEmotionId.collect {
                                    if (it != null) {
                                        navController.popBackStack(returnRoute, false)
                                    }
                                }
                            }
                        } else {
                            viewModel.onEvent(EmotionRecognitionEvent.OnEmotionResultConfirmedToAdd)

                            scope.launch {
                                viewModel.savedEmotionId.collect {
                                    if (it != null) {
                                        navController.navigate(
                                            NavigationItem.EmotionAdditionalInfo.getRouteWithArguments(
                                                returnRoute,
                                                it
                                            )
                                        ) {
                                            popUpTo(returnRoute)
                                        }
                                    }
                                }
                            }
                        }
                    }
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
        val onBackPressed = {

        }

        BackHandler {
            onBackPressed()
        }

        Surface {
            Column {
                BackButton(
                    onClick = { onBackPressed() },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                        .animateContentSize()
                ) {
                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

                    Text(
                        text = stringResource(R.string.select_your_emotion),
                        fontWeight = FontWeight.Bold,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 26.sp,
                        color = White,
                    )

                    Spacer(Modifier.height(40.dp))

                    val emotions = EmotionName.entries
                    val emotionListItems = mutableMapOf<EmotionName, String>()

                    emotions.forEach {
                        emotionListItems[it] = getEmotionNameString(LocalContext.current, it)
                    }

                    var selectedEmotion by rememberSaveable { mutableStateOf(EmotionName.HAPPINESS) }

                    val onClick: (Any) -> Unit = {
                        selectedEmotion = it as EmotionName
                    }

                    emotionListItems.onEachIndexed { index, entry ->
                        OptionButton(
                            value = entry.key,
                            text = entry.value,
                            selected = selectedEmotion == entry.key,
                            onClick = onClick
                        )

                        if (index < emotionListItems.size - 1) {
                            Spacer(Modifier.height(8.dp))
                        }
                    }

                    Spacer(Modifier.height(50.dp))

                    MyButton(
                        text = stringResource(id = R.string.confirm),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {

                        }
                    )

                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPrototype() {
    Surface {
        Column {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.toolbar_padding))
                    .size(dimensionResource(id = R.dimen.icon_button_size))
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

                Text(
                    text = stringResource(R.string.select_your_emotion),
                    fontWeight = FontWeight.Bold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    color = Color.Black,
                )

                Spacer(Modifier.height(40.dp))

                val emotions = EmotionName.entries
                val emotionListItems = mutableMapOf<EmotionName, String>()

                emotions.forEach {
                    emotionListItems[it] = getEmotionNameString(LocalContext.current, it)
                }

                val selectedEmotion by rememberSaveable { mutableStateOf(EmotionName.HAPPINESS) }

                emotionListItems.onEachIndexed { index, entry ->
                    Card(
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.option_button_rounded_corner_size)),
                        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = if (selectedEmotion == entry.key) 2.dp else 1.dp,
                                color = when {
                                    selectedEmotion == entry.key -> Color.Black
                                    else -> Color.Gray
                                },
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.option_button_rounded_corner_size))
                            )
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.option_button_rounded_corner_size)))
                            .clickable { }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(
                                vertical = dimensionResource(id = R.dimen.button_content_vertical_text_padding),
                                horizontal = dimensionResource(id = R.dimen.button_content_horizontal_text_padding)
                            )
                        ) {
                            RadioButton(
                                selected = selectedEmotion == entry.key,
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = Color.Gray,
                                    selectedColor = Color.Black
                                ),
                                onClick = { }
                            )

                            Spacer(Modifier.height(12.dp))

                            Text(
                                text = "Lorem",
                                fontFamily = AlegreyaFontFamily,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                    }

                    if (index < emotionListItems.size - 1) {
                        Spacer(Modifier.height(8.dp))
                    }
                }

                Spacer(Modifier.height(50.dp))

                Button(
                    onClick = { },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner_size)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            Color.Black,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner_size))
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(
                            vertical = dimensionResource(id = R.dimen.button_content_vertical_text_padding),
                            horizontal = dimensionResource(id = R.dimen.button_content_horizontal_text_padding)
                        )
                    ) {
                        Text(
                            text = "Подтвердить",
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 19.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}