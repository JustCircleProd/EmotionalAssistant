package com.example.bd.emotionRecognition.presentation.selectionFromList

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bd.core.domain.models.EmotionName
import com.example.bd.core.presentation.compontents.NavigationItem
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.compontents.buttons.OptionButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.presentation.util.getEmotionNameString
import com.example.bd.emotionRecognition.presentation.viewModel.EmotionRecognitionEvent
import com.example.bd.emotionRecognition.presentation.viewModel.EmotionRecognitionViewModel
import com.example.db.R
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
                        viewModel.onEvent(EmotionRecognitionEvent.OnEmotionResultConfirmed)

                        if (viewModel.emotionIdToUpdate != null) {
                            navController.popBackStack(returnRoute, false)
                        } else {
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