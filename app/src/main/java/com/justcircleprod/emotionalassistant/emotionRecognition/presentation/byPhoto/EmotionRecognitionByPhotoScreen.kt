package com.justcircleprod.emotionalassistant.emotionRecognition.presentation.byPhoto

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
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
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
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionName
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.BackButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyButton
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.TonalButtonColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.getEmotionNameString
import com.justcircleprod.emotionalassistant.emotionRecognition.presentation.viewModel.EmotionRecognitionEvent
import com.justcircleprod.emotionalassistant.emotionRecognition.presentation.viewModel.EmotionRecognitionStage
import com.justcircleprod.emotionalassistant.emotionRecognition.presentation.viewModel.EmotionRecognitionViewModel
import kotlinx.coroutines.launch


@Composable
fun EmotionRecognitionByPhotoScreen(
    navController: NavController,
    viewModel: EmotionRecognitionViewModel,
    returnRoute: String?
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
        val context = LocalContext.current

        val recognizedEmotion = viewModel.recognizedEmotion.collectAsStateWithLifecycle()
        val imageBitmap = viewModel.imageBitmap.collectAsStateWithLifecycle()

        val recognitionStage = viewModel.recognitionStage.collectAsStateWithLifecycle()

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wave_animation))

        AnimatedVisibility(
            visible = recognitionStage.value == EmotionRecognitionStage.FACE_DETECTION ||
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
                            navController.navigate(
                                NavigationItem.EmotionSelectionFromList.getRouteWithArguments(
                                    returnRoute
                                )
                            )
                        },
                        onConfirmButtonClicked = {
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

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
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
            stringResource(R.string.recognition_error)
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
private fun EmotionImage(
    imageBitmap: State<Bitmap?>
) {
    AnimatedContent(
        imageBitmap.value,
        transitionSpec = {
            fadeIn(animationSpec = tween(700, delayMillis = 400)) togetherWith fadeOut(
                animationSpec = tween(300)
            )
        },
        label = stringResource(id = R.string.emotion_recognition)
    ) {
        if (it != null) {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
                    .size(
                        dimensionResource(id = R.dimen.emotion_image_width),
                        dimensionResource(id = R.dimen.emotion_image_height)
                    )
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

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wave_animation))

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

                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
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

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wave_animation))

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

                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
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

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wave_animation))

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

                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewFaceDetectionPrototype() {
    Surface {
        val recognitionStage =
            remember { mutableStateOf(EmotionRecognitionStage.FACE_DETECTION) }

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
                        Color.Black
                    }
                )

                Spacer(Modifier.height(30.dp))

                Image(
                    painter = painterResource(id = R.drawable.prototype_image_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(
                            dimensionResource(id = R.dimen.emotion_image_width),
                            dimensionResource(id = R.dimen.emotion_image_height)
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewEmotionClassifiedPrototype() {
    Surface {
        val recognitionStage =
            remember { mutableStateOf(EmotionRecognitionStage.EMOTION_CLASSIFIED) }

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
                        Color.Black
                    }
                )

                Spacer(Modifier.height(30.dp))

                Image(
                    painter = painterResource(id = R.drawable.prototype_image_placeholder),
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

                Column(Modifier.fillMaxWidth()) {
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
                                text = stringResource(R.string.select_from_the_list),
                                fontFamily = AlegreyaFontFamily,
                                fontSize = 19.sp,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

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
                                text = stringResource(id = R.string.confirm),
                                fontFamily = AlegreyaFontFamily,
                                fontSize = 19.sp,
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewErrorPrototype() {
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
                    text = "Ошибка",
                    textAlign = TextAlign.Center,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(Modifier.height(30.dp))

                Image(
                    painter = painterResource(id = R.drawable.prototype_image_placeholder),
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
                            text = stringResource(id = R.string.to_choose_a_method),
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