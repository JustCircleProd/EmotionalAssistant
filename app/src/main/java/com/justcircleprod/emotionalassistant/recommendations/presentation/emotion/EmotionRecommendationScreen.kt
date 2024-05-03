package com.justcircleprod.emotionalassistant.recommendations.presentation.emotion

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.justcircleprod.emotionalassistant.core.domain.models.Emotion
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionName
import com.justcircleprod.emotionalassistant.core.presentation.compontents.EmotionImage
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.BackButton
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.SubtitleTextColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.getEmotionNameString
import com.justcircleprod.emotionalassistant.recommendations.presentation.components.RecommendationItem
import com.justcircleprod.emotionalassistant.R

@Composable
fun EmotionRecommendationScreen(
    navController: NavController,
    viewModel: EmotionRecommendationViewModel = hiltViewModel()
) {
    val emotion by viewModel.emotion.collectAsStateWithLifecycle()

    Surface {
        Column {
            BackButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
            )

            if (emotion == null) return@Surface

            val context = LocalContext.current

            val recommendationTitles = when (emotion!!.name) {
                EmotionName.ANGER -> stringArrayResource(id = R.array.anger_recommendation_titles)
                EmotionName.DISGUST -> stringArrayResource(id = R.array.disgust_recommendation_titles)
                EmotionName.FEAR -> stringArrayResource(id = R.array.fear_recommendation_titles)
                EmotionName.HAPPINESS -> stringArrayResource(id = R.array.happiness_recommendation_titles)
                EmotionName.NEUTRAL -> stringArrayResource(id = R.array.neutral_recommendation_titles)
                EmotionName.SADNESS -> stringArrayResource(id = R.array.sadness_recommendation_titles)
                EmotionName.SURPRISE -> stringArrayResource(id = R.array.surprise_recommendation_titles)
            }

            val recommendationTexts = when (emotion!!.name) {
                EmotionName.ANGER -> stringArrayResource(id = R.array.anger_recommendation_texts)
                EmotionName.DISGUST -> stringArrayResource(id = R.array.disgust_recommendation_texts)
                EmotionName.FEAR -> stringArrayResource(id = R.array.fear_recommendation_texts)
                EmotionName.HAPPINESS -> stringArrayResource(id = R.array.happiness_recommendation_texts)
                EmotionName.NEUTRAL -> stringArrayResource(id = R.array.neutral_recommendation_texts)
                EmotionName.SADNESS -> stringArrayResource(id = R.array.sadness_recommendation_texts)
                EmotionName.SURPRISE -> stringArrayResource(id = R.array.surprise_recommendation_texts)
            }

            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                item {
                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                }

                item {
                    val emotionImageFileName = emotion!!.imageFileName

                    if (emotionImageFileName != null) {
                        EmotionImage(context, emotionImageFileName)
                    } else {
                        EmotionIllustration(emotionName = emotion!!.name)
                    }

                    Spacer(Modifier.height(15.dp))
                }

                item {
                    Text(
                        text = getEmotionNameString(context, emotion!!.name),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 24.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(10.dp))
                }

                item {
                    EmotionInfo(emotion!!.name)

                    Spacer(Modifier.height(25.dp))
                }

                item {
                    Text(
                        text = stringResource(id = R.string.recommendations),
                        fontFamily = AlegreyaFontFamily,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 21.sp,
                        color = White,
                    )

                    Spacer(Modifier.height(15.dp))
                }



                items(recommendationTitles.size) {
                    RecommendationItem(recommendationTitles[it], recommendationTexts[it])

                    if (it < recommendationTitles.size - 1) {
                        Spacer(Modifier.height(15.dp))
                    }
                }

                item {
                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                }
            }
        }
    }
}

@Composable
private fun EmotionIllustration(emotionName: EmotionName) {
    val painter = when (emotionName) {
        EmotionName.ANGER -> painterResource(id = R.drawable.anger)
        EmotionName.DISGUST -> painterResource(id = R.drawable.disgust)
        EmotionName.FEAR -> painterResource(id = R.drawable.fear)
        EmotionName.HAPPINESS -> painterResource(id = R.drawable.happiness)
        EmotionName.NEUTRAL -> painterResource(id = R.drawable.neutral)
        EmotionName.SADNESS -> painterResource(id = R.drawable.sadness)
        EmotionName.SURPRISE -> painterResource(id = R.drawable.surprise)
    }

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun EmotionInfo(emotionName: EmotionName) {
    val text = when (emotionName) {
        EmotionName.ANGER -> stringResource(id = R.string.anger_info)
        EmotionName.DISGUST -> stringResource(id = R.string.disgust_info)
        EmotionName.FEAR -> stringResource(id = R.string.fear_info)
        EmotionName.HAPPINESS -> stringResource(id = R.string.happiness_info)
        EmotionName.NEUTRAL -> stringResource(id = R.string.neutral_info)
        EmotionName.SADNESS -> stringResource(id = R.string.sadness_info)
        EmotionName.SURPRISE -> stringResource(id = R.string.surprise_info)
    }

    Text(
        text = text,
        fontStyle = FontStyle.Italic,
        fontFamily = AlegreyaFontFamily,
        fontSize = 15.sp,
        lineHeight = 19.sp,
        textAlign = TextAlign.Center,
        color = SubtitleTextColor,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
        Surface {
            Column {
                BackButton(
                    onClick = {

                    },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
                )

                val emotion = Emotion()

                val context = LocalContext.current

                val recommendationTitles = when (emotion.name) {
                    EmotionName.ANGER -> stringArrayResource(id = R.array.anger_recommendation_titles)
                    EmotionName.DISGUST -> stringArrayResource(id = R.array.disgust_recommendation_titles)
                    EmotionName.FEAR -> stringArrayResource(id = R.array.fear_recommendation_titles)
                    EmotionName.HAPPINESS -> stringArrayResource(id = R.array.happiness_recommendation_titles)
                    EmotionName.NEUTRAL -> stringArrayResource(id = R.array.neutral_recommendation_titles)
                    EmotionName.SADNESS -> stringArrayResource(id = R.array.sadness_recommendation_titles)
                    EmotionName.SURPRISE -> stringArrayResource(id = R.array.surprise_recommendation_titles)
                }

                val recommendationTexts = when (emotion.name) {
                    EmotionName.ANGER -> stringArrayResource(id = R.array.anger_recommendation_texts)
                    EmotionName.DISGUST -> stringArrayResource(id = R.array.disgust_recommendation_texts)
                    EmotionName.FEAR -> stringArrayResource(id = R.array.fear_recommendation_texts)
                    EmotionName.HAPPINESS -> stringArrayResource(id = R.array.happiness_recommendation_texts)
                    EmotionName.NEUTRAL -> stringArrayResource(id = R.array.neutral_recommendation_texts)
                    EmotionName.SADNESS -> stringArrayResource(id = R.array.sadness_recommendation_texts)
                    EmotionName.SURPRISE -> stringArrayResource(id = R.array.surprise_recommendation_texts)
                }

                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                        .animateContentSize()
                ) {
                    item {
                        Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                    }

                    item {
                        EmotionIllustration(emotionName = emotion.name)

                        Spacer(Modifier.height(15.dp))
                    }

                    item {
                        Text(
                            text = getEmotionNameString(context, emotion.name),
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 24.sp,
                            color = White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(10.dp))
                    }

                    item {
                        EmotionInfo(emotion.name)

                        Spacer(Modifier.height(25.dp))
                    }

                    item {
                        Text(
                            text = stringResource(id = R.string.recommendations),
                            fontFamily = AlegreyaFontFamily,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                            fontSize = 21.sp,
                            color = White,
                        )

                        Spacer(Modifier.height(15.dp))
                    }



                    items(recommendationTitles.size) {
                        RecommendationItem(recommendationTitles[it], recommendationTexts[it])

                        if (it < recommendationTitles.size - 1) {
                            Spacer(Modifier.height(15.dp))
                        }
                    }

                    item {
                        Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                    }
                }
            }
        }
    }
}