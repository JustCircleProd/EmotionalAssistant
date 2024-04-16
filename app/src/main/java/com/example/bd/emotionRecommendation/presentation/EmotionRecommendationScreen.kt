package com.example.bd.emotionRecommendation.presentation

import androidx.compose.foundation.Image
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
import com.example.bd.core.domain.models.EmotionName
import com.example.bd.core.presentation.compontents.EmotionImage
import com.example.bd.core.presentation.compontents.ErrorLayout
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.SubtitleTextColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.presentation.util.getEmotionNameString
import com.example.db.R

@Composable
fun EmotionRecommendationScreen(
    navController: NavController,
    viewModel: EmotionRecommendationViewModel = hiltViewModel()
) {
    val emotion by viewModel.emotion.collectAsStateWithLifecycle()

    Surface {
        if (emotion == null) {
            ErrorLayout(
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )
            return@Surface
        }

        Column {
            BackButton(
                onClick = {
                    navController.popBackStack()
                },
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
            ) {
                val context = LocalContext.current

                val emotionImageFileName = emotion!!.imageFileName

                if (emotionImageFileName != null) {
                    EmotionImage(context, emotionImageFileName)
                } else {
                    EmotionIllustration(emotionName = emotion!!.name)
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = getEmotionNameString(context, emotion!!.name),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 24.sp,
                    color = White,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(10.dp))

                EmotionInfo(emotion!!.name)

                Spacer(Modifier.height(25.dp))

                Text(
                    text = stringResource(id = R.string.recommendations),
                    fontFamily = AlegreyaFontFamily,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontSize = 21.sp,
                    color = White,
                )

                Spacer(Modifier.height(15.dp))

                EmotionRecommendations(emotion!!.name)
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

@Composable
private fun EmotionRecommendations(emotionName: EmotionName) {
    val recommendationTitles = when (emotionName) {
        EmotionName.ANGER -> stringArrayResource(id = R.array.anger_recommendation_titles)
        EmotionName.DISGUST -> stringArrayResource(id = R.array.disgust_recommendation_titles)
        EmotionName.FEAR -> stringArrayResource(id = R.array.fear_recommendation_titles)
        EmotionName.HAPPINESS -> stringArrayResource(id = R.array.happiness_recommendation_titles)
        EmotionName.NEUTRAL -> stringArrayResource(id = R.array.neutral_recommendation_titles)
        EmotionName.SADNESS -> stringArrayResource(id = R.array.sadness_recommendation_titles)
        EmotionName.SURPRISE -> stringArrayResource(id = R.array.surprise_recommendation_titles)
    }

    val recommendationTexts = when (emotionName) {
        EmotionName.ANGER -> stringArrayResource(id = R.array.anger_recommendation_texts)
        EmotionName.DISGUST -> stringArrayResource(id = R.array.disgust_recommendation_texts)
        EmotionName.FEAR -> stringArrayResource(id = R.array.fear_recommendation_texts)
        EmotionName.HAPPINESS -> stringArrayResource(id = R.array.happiness_recommendation_texts)
        EmotionName.NEUTRAL -> stringArrayResource(id = R.array.neutral_recommendation_texts)
        EmotionName.SADNESS -> stringArrayResource(id = R.array.sadness_recommendation_texts)
        EmotionName.SURPRISE -> stringArrayResource(id = R.array.surprise_recommendation_texts)
    }

    for (i in recommendationTitles.indices) {
        Text(
            text = recommendationTitles[i],
            fontFamily = AlegreyaFontFamily,
            fontSize = 18.sp,
            color = White,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text = recommendationTexts[i],
            fontFamily = AlegreyaFontFamily,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = SubtitleTextColor,
            modifier = Modifier.fillMaxWidth()
        )

        if (i != recommendationTitles.size - 1) {
            Spacer(Modifier.height(15.dp))
        }
    }
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
                ) {
                    val context = LocalContext.current

                    EmotionIllustration(emotionName = EmotionName.SADNESS)

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = getEmotionNameString(context, EmotionName.SADNESS),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 24.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(10.dp))

                    EmotionInfo(EmotionName.SADNESS)

                    Spacer(Modifier.height(25.dp))

                    Text(
                        text = stringResource(id = R.string.recommendations),
                        fontFamily = AlegreyaFontFamily,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 21.sp,
                        color = White,
                    )

                    Spacer(Modifier.height(15.dp))

                    EmotionRecommendations(EmotionName.SADNESS)
                }
            }
        }
    }
}