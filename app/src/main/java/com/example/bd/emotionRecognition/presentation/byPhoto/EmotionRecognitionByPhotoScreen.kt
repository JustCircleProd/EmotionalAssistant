package com.example.bd.emotionRecognition.presentation.byPhoto

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bd.core.domain.models.EmotionName
import com.example.bd.core.presentation.compontents.NavigationItem
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.TonalButtonColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.presentation.util.getEmotionNameString
import com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel.EmotionRecognitionEvent
import com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel.EmotionRecognitionStage
import com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel.EmotionRecognitionViewModel
import com.example.db.R
import kotlinx.coroutines.launch


@Composable
fun EmotionRecognitionByPhotoScreen(
    navController: NavController,
    viewModel: EmotionRecognitionViewModel,
    returnRoute: String
) {
    val onBackPressed = {
        viewModel.onEvent(EmotionRecognitionEvent.OnBackPressed)
        navController.popBackStack()
    }

    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(Unit) {
        if (viewModel.recognitionStage.value == EmotionRecognitionStage.FACE_DETECTION) {
            viewModel.onEvent(EmotionRecognitionEvent.OnEmotionRecognitionByPhotoScreenLaunched)
        }
    }

    Surface {
        val recognizedEmotion = viewModel.recognizedEmotion.collectAsStateWithLifecycle()
        val imageBitmap = viewModel.imageBitmap.collectAsStateWithLifecycle()

        val recognitionStage = viewModel.recognitionStage.collectAsStateWithLifecycle()

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wave_animation_2))

        AnimatedVisibility(
            visible = recognitionStage.value == EmotionRecognitionStage.FACE_DETECTION &&
                    recognitionStage.value == EmotionRecognitionStage.EMOTION_CLASSIFICATION,
            enter = fadeIn(animationSpec = tween(3000)),
            exit = fadeOut(animationSpec = tween(2000))
        ) {
            LottieAnimation(
                composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        Column {
            BackButton(
                onClick = { onBackPressed() },
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.toolbar_padding),
                    start = dimensionResource(id = R.dimen.toolbar_padding)
                )
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                TitleText(recognitionStage, recognizedEmotion)

                Spacer(Modifier.height(30.dp))

                EmotionImage(imageBitmap)

                Spacer(Modifier.height(50.dp))

                val scope = rememberCoroutineScope()

                AnimatedVisibility(
                    visible = recognitionStage.value == EmotionRecognitionStage.EMOTION_CLASSIFIED,
                    enter = fadeIn(animationSpec = tween(700, delayMillis = 400)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    SuccessfulResultButtons(
                        onSelectFromListButtonClicked = {
                            "${NavigationItem.EmotionSelectionFromList}/${returnRoute}"
                        },
                        onConfirmButtonClicked = {
                            viewModel.onEvent(EmotionRecognitionEvent.OnEmotionResultConfirmed)

                            if (viewModel.emotionIdToUpdate != null) {
                                navController.popBackStack(returnRoute, false)
                            } else {
                                scope.launch {
                                    viewModel.savedEmotionId.collect {
                                        if (it != null) {
                                            navController.navigate(
                                                NavigationItem.EmotionAdditionalInfo.getRouteWithArguments(
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
                }

                AnimatedVisibility(
                    visible = recognitionStage.value == EmotionRecognitionStage.FACE_NOT_DETECTED ||
                            recognitionStage.value == EmotionRecognitionStage.ERROR,
                    enter = fadeIn(animationSpec = tween(700, delayMillis = 400)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    MyButton(
                        text = stringResource(R.string.to_choose_a_method),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onBackPressed() }
                    )
                }
            }
        }
    }
}

@Composable
private fun TitleText(
    recognitionStage: State<EmotionRecognitionStage>,
    recognizedEmotion: State<EmotionName?>
) {
    val textValue = when (recognitionStage.value) {
        EmotionRecognitionStage.FACE_DETECTION -> {
            stringResource(R.string.face_detection)
        }

        EmotionRecognitionStage.EMOTION_CLASSIFICATION -> {
            stringResource(R.string.emotion_recognition)
        }

        EmotionRecognitionStage.EMOTION_CLASSIFIED -> {
            getEmotionNameString(LocalContext.current, recognizedEmotion.value)
        }

        EmotionRecognitionStage.FACE_NOT_DETECTED -> {
            stringResource(id = R.string.face_not_detected)
        }

        EmotionRecognitionStage.ERROR -> {
            stringResource(R.string.recognition_error_try_again)
        }
    }

    AnimatedContent(
        textValue,
        transitionSpec = {
            fadeIn(animationSpec = tween(700, delayMillis = 400)) togetherWith
                    fadeOut(animationSpec = tween(300))
        }, label = stringResource(id = R.string.emotion_recognition)
    ) {
        Text(
            text = it,
            fontFamily = AlegreyaFontFamily,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = if (
                recognitionStage.value == EmotionRecognitionStage.FACE_NOT_DETECTED ||
                recognitionStage.value == EmotionRecognitionStage.ERROR
            ) {
                MaterialTheme.colorScheme.error
            } else {
                White
            }
        )
    }
}

@Composable
private fun EmotionImage(imageBitmap: State<Bitmap?>) {
    AnimatedContent(
        imageBitmap.value,
        transitionSpec = {
            fadeIn(animationSpec = tween(700, delayMillis = 400)) togetherWith fadeOut(
                animationSpec = tween(300)
            )
        }, label = stringResource(id = R.string.emotion_recognition)
    ) {
        if (it != null) {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(
                        dimensionResource(id = R.dimen.emotion_image_width),
                        dimensionResource(id = R.dimen.emotion_image_height)
                    )
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
            )
        }
    }
}

@Composable
private fun SuccessfulResultButtons(
    onSelectFromListButtonClicked: () -> Unit,
    onConfirmButtonClicked: () -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        MyButton(
            text = stringResource(R.string.select_from_the_list),
            containerColor = TonalButtonColor,
            modifier = Modifier.fillMaxWidth(),
            onClick = onSelectFromListButtonClicked
        )

        Spacer(modifier = Modifier.height(12.dp))

        MyButton(
            text = stringResource(id = R.string.confirm),
            modifier = Modifier.fillMaxWidth(),
            onClick = onConfirmButtonClicked
        )
    }
}

@Preview
@Composable
private fun PreviewFaceDetection() {
    BdTheme {
        Surface {
            val recognitionStage =
                remember { mutableStateOf(EmotionRecognitionStage.FACE_DETECTION) }

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wave_animation_2))

            AnimatedVisibility(
                visible = recognitionStage.value == EmotionRecognitionStage.FACE_DETECTION &&
                        recognitionStage.value == EmotionRecognitionStage.EMOTION_CLASSIFICATION,
                enter = fadeIn(animationSpec = tween(3000)),
                exit = fadeOut(animationSpec = tween(2000))
            ) {
                LottieAnimation(
                    composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            Column {
                BackButton(
                    onClick = { },
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.toolbar_padding),
                        start = dimensionResource(id = R.dimen.toolbar_padding)
                    )
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(dimensionResource(id = R.dimen.main_screens_space))
                        .animateContentSize()
                ) {
                    Text(
                        text = "Стадия распознавания эмоции",
                        textAlign = TextAlign.Center,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (
                            recognitionStage.value == EmotionRecognitionStage.FACE_NOT_DETECTED ||
                            recognitionStage.value == EmotionRecognitionStage.ERROR
                        ) {
                            MaterialTheme.colorScheme.error
                        } else {
                            White
                        }
                    )

                    Spacer(Modifier.height(30.dp))

                    Image(
                        painter = painterResource(id = R.drawable.image_preview),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(
                                dimensionResource(id = R.dimen.emotion_image_width),
                                dimensionResource(id = R.dimen.emotion_image_height)
                            )
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
                    )

                    Spacer(Modifier.height(50.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewEmotionClassified() {
    BdTheme {
        Surface {
            val recognitionStage =
                remember { mutableStateOf(EmotionRecognitionStage.EMOTION_CLASSIFIED) }

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wave_animation_2))

            AnimatedVisibility(
                visible = recognitionStage.value == EmotionRecognitionStage.FACE_DETECTION &&
                        recognitionStage.value == EmotionRecognitionStage.EMOTION_CLASSIFICATION,
                enter = fadeIn(animationSpec = tween(3000)),
                exit = fadeOut(animationSpec = tween(2000))
            ) {
                LottieAnimation(
                    composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            Column {
                BackButton(
                    onClick = { },
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.toolbar_padding),
                        start = dimensionResource(id = R.dimen.toolbar_padding)
                    )
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(dimensionResource(id = R.dimen.main_screens_space))
                        .animateContentSize()
                ) {
                    Text(
                        text = "Распознанная эмоция",
                        textAlign = TextAlign.Center,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (
                            recognitionStage.value == EmotionRecognitionStage.FACE_NOT_DETECTED ||
                            recognitionStage.value == EmotionRecognitionStage.ERROR
                        ) {
                            MaterialTheme.colorScheme.error
                        } else {
                            White
                        }
                    )

                    Spacer(Modifier.height(30.dp))

                    Image(
                        painter = painterResource(id = R.drawable.image_preview),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(
                                dimensionResource(id = R.dimen.emotion_image_width),
                                dimensionResource(id = R.dimen.emotion_image_height)
                            )
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
                    )

                    Spacer(Modifier.height(50.dp))

                    SuccessfulResultButtons(
                        onSelectFromListButtonClicked = {

                        },
                        onConfirmButtonClicked = {

                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewError() {
    BdTheme {
        Surface {
            val recognitionStage = remember { mutableStateOf(EmotionRecognitionStage.ERROR) }

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wave_animation_2))

            AnimatedVisibility(
                visible = recognitionStage.value == EmotionRecognitionStage.FACE_DETECTION &&
                        recognitionStage.value == EmotionRecognitionStage.EMOTION_CLASSIFICATION,
                enter = fadeIn(animationSpec = tween(3000)),
                exit = fadeOut(animationSpec = tween(2000))
            ) {
                LottieAnimation(
                    composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            Column {
                BackButton(
                    onClick = { },
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.toolbar_padding),
                        start = dimensionResource(id = R.dimen.toolbar_padding)
                    )
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(dimensionResource(id = R.dimen.main_screens_space))
                        .animateContentSize()
                ) {
                    Text(
                        text = "Ошибка",
                        textAlign = TextAlign.Center,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (
                            recognitionStage.value == EmotionRecognitionStage.FACE_NOT_DETECTED ||
                            recognitionStage.value == EmotionRecognitionStage.ERROR
                        ) {
                            MaterialTheme.colorScheme.error
                        } else {
                            White
                        }
                    )

                    Spacer(Modifier.height(30.dp))

                    Image(
                        painter = painterResource(id = R.drawable.image_preview),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(
                                dimensionResource(id = R.dimen.emotion_image_width),
                                dimensionResource(id = R.dimen.emotion_image_height)
                            )
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
                    )

                    Spacer(Modifier.height(50.dp))

                    MyButton(
                        text = stringResource(R.string.to_choose_a_method),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { }
                    )
                }
            }
        }
    }
}