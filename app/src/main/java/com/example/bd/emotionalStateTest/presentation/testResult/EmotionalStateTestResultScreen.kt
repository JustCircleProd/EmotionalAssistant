package com.example.bd.emotionalStateTest.presentation.testResult

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
import androidx.navigation.compose.rememberNavController
import com.example.bd.core.data.initialData.InitialEmotionalStateTests
import com.example.bd.core.domain.models.EmotionalStateName
import com.example.bd.core.domain.models.EmotionalStateTestResult
import com.example.bd.core.presentation.compontents.NavigationItem
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.SubtitleTextColor
import com.example.bd.core.presentation.theme.TonalButtonColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.presentation.util.getEmotionalStateNameString
import com.example.db.R

@Composable
fun EmotionalStateTestResultScreen(
    navController: NavController,
    viewModel: EmotionalStateTestResultViewModel = hiltViewModel()
) {
    Surface {
        Column {
            BackButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
            )

            val testResults by viewModel.testResults.collectAsStateWithLifecycle()

            if (testResults.isNullOrEmpty()) {
                NoTestResults(navController)
                return@Surface
            }

            LazyColumn(
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
                    Text(
                        text = stringResource(R.string.here_is_what_we_found),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 24.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(20.dp))
                }


                items(testResults!!.size) { index ->
                    testResults!![index].emotionalStateTest?.emotionalStateName?.let {
                        EmotionalStateTestResultItem(navController, emotionalStateName = it)
                    }

                    if (index < testResults!!.size - 1) {
                        Spacer(Modifier.height(40.dp))
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
private fun NoTestResults(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
            .animateContentSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.neutral),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.we_have_not_detected_any_deviations_from_the_norm),
            fontWeight = FontWeight.Medium,
            fontFamily = AlegreyaFontFamily,
            fontSize = 21.sp,
            color = White,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(25.dp))

        MyButton(
            text = stringResource(R.string.exit),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.popBackStack()
            }
        )
    }
}

@Composable
private fun EmotionalStateTestResultItem(
    navController: NavController,
    emotionalStateName: EmotionalStateName
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = getEmotionalStateNameString(context, emotionalStateName),
            fontWeight = FontWeight.SemiBold,
            fontFamily = AlegreyaFontFamily,
            fontSize = 24.sp,
            color = White,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        EmotionalStateIllustration(emotionalStateName)

        Spacer(Modifier.height(15.dp))

        EmotionalStateInfo(emotionalStateName)

        Spacer(Modifier.height(20.dp))

        MyButton(
            text = stringResource(id = R.string.recommendations),
            modifier = Modifier.fillMaxWidth(),
            containerColor = TonalButtonColor,
            onClick = {
                navController.navigate(
                    NavigationItem.EmotionalStateRecommendation.getRouteWithArguments(
                        emotionalStateName
                    )
                )
            }
        )
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
                val navController = rememberNavController()

                BackButton(
                    onClick = {},
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
                )

                val testResults = listOf(
                    EmotionalStateTestResult().apply {
                        emotionalStateTest = InitialEmotionalStateTests.get()[0]
                        score = 12
                    }
                )

                LazyColumn(
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
                        Text(
                            text = stringResource(R.string.here_is_what_we_found),
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 24.sp,
                            color = White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(20.dp))
                    }


                    items(testResults.size) { index ->
                        testResults[index].emotionalStateTest?.emotionalStateName?.let {
                            EmotionalStateTestResultItem(navController, emotionalStateName = it)
                        }

                        if (index < testResults.size - 1) {
                            Spacer(Modifier.height(40.dp))
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