package com.justcircleprod.emotionalassistant.recommendations.presentation.emotionalState

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
import androidx.navigation.NavController
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateName
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.BackButton
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.SubtitleTextColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.getEmotionalStateNameString
import com.justcircleprod.emotionalassistant.recommendations.presentation.components.RecommendationItem

@Composable
fun EmotionalStateRecommendationScreen(
    navController: NavController,
    emotionalStateName: EmotionalStateName?
) {
    Surface {
        Column {
            BackButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
            )

            if (emotionalStateName == null) return@Surface

            val context = LocalContext.current

            val recommendationTitles = when (emotionalStateName) {
                EmotionalStateName.DEPRESSION -> stringArrayResource(id = R.array.depression_recommendation_titles)
                EmotionalStateName.NEUROSIS -> stringArrayResource(id = R.array.neurosis_recommendation_titles)
                EmotionalStateName.SOCIAL_PHOBIA -> stringArrayResource(id = R.array.social_phobia_recommendation_titles)
                EmotionalStateName.ASTHENIA -> stringArrayResource(id = R.array.asthenia_recommendation_titles)
                EmotionalStateName.INSOMNIA -> stringArrayResource(id = R.array.insomnia_recommendation_titles)
            }

            val recommendationTexts = when (emotionalStateName) {
                EmotionalStateName.DEPRESSION -> stringArrayResource(id = R.array.depression_recommendation_texts)
                EmotionalStateName.NEUROSIS -> stringArrayResource(id = R.array.neurosis_recommendation_texts)
                EmotionalStateName.SOCIAL_PHOBIA -> stringArrayResource(id = R.array.social_phobia_recommendation_texts)
                EmotionalStateName.ASTHENIA -> stringArrayResource(id = R.array.asthenia_recommendation_texts)
                EmotionalStateName.INSOMNIA -> stringArrayResource(id = R.array.insomnia_recommendation_texts)
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
                    EmotionalStateIllustration(emotionalStateName)

                    Spacer(Modifier.height(15.dp))
                }

                item {
                    Text(
                        text = getEmotionalStateNameString(context, emotionalStateName),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 24.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(10.dp))
                }

                item {
                    EmotionalStateInfo(emotionalStateName)

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
private fun EmotionalStateIllustration(emotionalStateName: EmotionalStateName) {
    val painter = when (emotionalStateName) {
        EmotionalStateName.DEPRESSION -> painterResource(id = R.drawable.depression)
        EmotionalStateName.NEUROSIS -> painterResource(id = R.drawable.neurosis)
        EmotionalStateName.SOCIAL_PHOBIA -> painterResource(id = R.drawable.social_phobia)
        EmotionalStateName.ASTHENIA -> painterResource(id = R.drawable.asthenia)
        EmotionalStateName.INSOMNIA -> painterResource(id = R.drawable.insomnia)
    }

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun EmotionalStateInfo(emotionalStateName: EmotionalStateName) {
    val text = when (emotionalStateName) {
        EmotionalStateName.DEPRESSION -> stringResource(id = R.string.depression_info)
        EmotionalStateName.NEUROSIS -> stringResource(id = R.string.neurosis_info)
        EmotionalStateName.SOCIAL_PHOBIA -> stringResource(id = R.string.social_phobia_info)
        EmotionalStateName.ASTHENIA -> stringResource(id = R.string.asthenia_info)
        EmotionalStateName.INSOMNIA -> stringResource(id = R.string.insomnia_info)
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
                    onClick = {},
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
                )

                val emotionalStateName = EmotionalStateName.DEPRESSION

                val context = LocalContext.current

                val recommendationTitles =
                    stringArrayResource(id = R.array.depression_recommendation_titles)


                val recommendationTexts =
                    stringArrayResource(id = R.array.depression_recommendation_texts)


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
                        EmotionalStateIllustration(emotionalStateName)

                        Spacer(Modifier.height(15.dp))
                    }

                    item {
                        Text(
                            text = getEmotionalStateNameString(context, emotionalStateName),
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 24.sp,
                            color = White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(10.dp))
                    }

                    item {
                        EmotionalStateInfo(emotionalStateName)

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