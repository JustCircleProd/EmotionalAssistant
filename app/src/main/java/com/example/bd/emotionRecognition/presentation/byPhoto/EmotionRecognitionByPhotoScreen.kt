package com.example.bd.emotionRecognition.presentation.byPhoto

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bd.core.presentation.compontents.appNavigation.NavigationItem
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.TonalButtonColor
import com.example.bd.core.utils.getEmotionNameString
import com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel.EmotionRecognitionEvent
import com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel.EmotionRecognitionStage
import com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel.EmotionRecognitionViewModel
import com.example.db.R

@Composable
fun EmotionRecognitionByPhotoScreen(
    navController: NavController,
    viewModel: EmotionRecognitionViewModel
) {
    BackHandler {
        viewModel.onEvent(EmotionRecognitionEvent.OnBackPressedOnEmotionRecognitionByPhotoScreen)
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(EmotionRecognitionEvent.OnEmotionRecognitionByPhotoScreenLaunched)
    }

    Surface {
        val recognizedEmotion by viewModel.emotion.collectAsStateWithLifecycle()
        val imageBitmap by viewModel.imageBitmap.collectAsStateWithLifecycle()

        val recognitionStage by viewModel.recognitionStage.collectAsStateWithLifecycle()

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wave_animation_2))

        AnimatedVisibility(
            visible = recognizedEmotion == null,
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
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.back_button_layout_padding),
                    start = dimensionResource(id = R.dimen.back_button_layout_padding)
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
                val textValue = when (recognitionStage) {
                    EmotionRecognitionStage.FACE_DETECTION -> {
                        stringResource(R.string.face_detection)
                    }

                    EmotionRecognitionStage.EMOTION_CLASSIFICATION -> {
                        stringResource(R.string.emotion_recognition)
                    }

                    EmotionRecognitionStage.EMOTION_CLASSIFIED -> {
                        getEmotionNameString(LocalContext.current, recognizedEmotion)
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
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(30.dp))

                AnimatedContent(
                    imageBitmap,
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
                            modifier = Modifier.size(
                                dimensionResource(id = R.dimen.emotion_recognition_photo_width),
                                dimensionResource(id = R.dimen.emotion_recognition_photo_height)
                            )
                        )
                    }
                }

                Spacer(Modifier.height(50.dp))

                AnimatedVisibility(
                    visible = recognizedEmotion != null,
                    enter = fadeIn(animationSpec = tween(700, delayMillis = 400)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        MyButton(
                            text = stringResource(R.string.select_from_the_list),
                            containerColor = TonalButtonColor,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                navController.navigate(NavigationItem.EmotionSelectionFromList.route)
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        MyButton(
                            text = stringResource(id = R.string.confirm),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                viewModel.onEvent(EmotionRecognitionEvent.OnEmotionResultConfirmed)
                            }
                        )
                    }
                }
            }
        }
    }
}