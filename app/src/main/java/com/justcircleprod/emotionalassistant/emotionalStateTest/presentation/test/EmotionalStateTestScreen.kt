package com.justcircleprod.emotionalassistant.emotionalStateTest.presentation.test

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.data.initialData.InitialEmotionalStateTests
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.BackButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.OptionButton
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.SubtitleTextColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.TonalButtonColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import kotlinx.coroutines.launch

@Composable
fun EmotionalStateTestScreen(
    navController: NavHostController,
    returnRoute: String?,
    viewModel: EmotionalStateTestViewModel = hiltViewModel()
) {
    Surface {
        Column {
            BackButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
            )

            if (returnRoute == null) return@Surface

            val currentTest by viewModel.currentTest.collectAsStateWithLifecycle()

            val currentQuestionNumber by viewModel.currentQuestionNumber.collectAsStateWithLifecycle()
            val numberOfQuestions by viewModel.numberOfQuestions.collectAsStateWithLifecycle()

            val context = LocalContext.current

            if (currentTest != null) {
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

                    QuestionNumber(currentQuestionNumber, numberOfQuestions)

                    Spacer(Modifier.height(10.dp))

                    val questionsResourceId = context.resources.getIdentifier(
                        currentTest!!.questionsResourceEntryName,
                        "array",
                        context.packageName
                    )
                    val questionText =
                        stringArrayResource(id = questionsResourceId)[viewModel.currentTestQuestionIndex]

                    QuestionCard(questionText)

                    Spacer(Modifier.height(20.dp))

                    var selectedOption by rememberSaveable {
                        mutableStateOf<Int?>(null)
                    }

                    var isError by rememberSaveable {
                        mutableStateOf(false)
                    }

                    OptionButtons(
                        selectedOption,
                        onSelect = {
                            selectedOption = it
                            isError = false
                        },
                        isError = isError
                    )

                    Spacer(Modifier.height(30.dp))

                    val scope = rememberCoroutineScope()

                    if (currentQuestionNumber != numberOfQuestions) {
                        MyButton(
                            text = stringResource(R.string.next_question),
                            onClick = {
                                if (selectedOption != null) {
                                    isError = false

                                    viewModel.onEvent(
                                        EmotionalStateTestEvent.OnGoToNextQuestionClick(
                                            selectedOption!!
                                        )
                                    )

                                    selectedOption = null
                                } else {
                                    isError = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        MyButton(
                            text = stringResource(R.string.complete_test),
                            onClick = {
                                if (selectedOption != null) {
                                    isError = false

                                    viewModel.onEvent(
                                        EmotionalStateTestEvent.OnCompleteTestClick(
                                            selectedOption!!
                                        )
                                    )

                                    scope.launch {
                                        viewModel.areTestResultsSaved.collect {
                                            if (it) {
                                                navController.navigate(
                                                    NavigationItem.EmotionalStateTestResult.getRouteWithArguments(
                                                        viewModel.testResultsDate
                                                    )
                                                ) {
                                                    popUpTo(returnRoute)
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    isError = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                }
            }
        }
    }
}

@Composable
private fun QuestionNumber(currentQuestionNumber: Int, numberOfQuestions: Int) {
    Text(
        text = stringResource(R.string.question_number, currentQuestionNumber, numberOfQuestions),
        fontFamily = AlegreyaFontFamily,
        fontSize = 17.sp,
        color = SubtitleTextColor,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun QuestionCard(question: String) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = TonalButtonColor
        ),
        modifier = Modifier.clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotional_state_test_question_card_rounded_corner_size)))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.emotional_state_test_question_card_horizontal_padding),
                    vertical = dimensionResource(id = R.dimen.emotional_state_test_question_card_vertical_padding)
                )
                .defaultMinSize(minHeight = dimensionResource(id = R.dimen.emotional_state_test_question_card_min_height))
        ) {
            Text(
                text = question,
                fontFamily = AlegreyaFontFamily,
                fontSize = 21.sp,
                fontWeight = FontWeight.Medium,
                color = White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun OptionButtons(selectedOption: Int?, onSelect: (Int) -> Unit, isError: Boolean) {
    val optionsTexts = stringArrayResource(id = R.array.emotional_state_test_options)

    optionsTexts.forEachIndexed { index, optionText ->
        OptionButton(
            value = index,
            text = optionText,
            selected = selectedOption == index,
            isError = isError,
            onClick = {
                onSelect(it as Int)
            }
        )

        if (index != optionsTexts.size - 1) {
            Spacer(Modifier.height(8.dp))
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
                    onClick = { },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
                )

                val currentTest = InitialEmotionalStateTests.get()[0]

                val currentQuestionNumber = 1
                val numberOfQuestions = 23

                val context = LocalContext.current

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

                    QuestionNumber(currentQuestionNumber, numberOfQuestions)

                    Spacer(Modifier.height(10.dp))

                    val questionsResourceId = context.resources.getIdentifier(
                        currentTest.questionsResourceEntryName,
                        "array",
                        context.packageName
                    )
                    val questionText =
                        stringArrayResource(id = questionsResourceId)[0]

                    QuestionCard(questionText)

                    Spacer(Modifier.height(20.dp))

                    var selectedOption by rememberSaveable {
                        mutableStateOf<Int?>(2)
                    }

                    var isError by rememberSaveable {
                        mutableStateOf(false)
                    }

                    OptionButtons(
                        selectedOption,
                        onSelect = {
                            selectedOption = it
                            isError = false
                        },
                        isError = isError
                    )

                    Spacer(Modifier.height(30.dp))

                    MyButton(
                        text = stringResource(R.string.next_question),
                        onClick = {

                        },
                        modifier = Modifier.fillMaxWidth()
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

            val currentTest = InitialEmotionalStateTests.get()[0]

            val currentQuestionNumber = 1
            val numberOfQuestions = 23

            val context = LocalContext.current

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
                    text = stringResource(
                        R.string.question_number,
                        currentQuestionNumber,
                        numberOfQuestions
                    ),
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 17.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))

                val questionsResourceId = context.resources.getIdentifier(
                    currentTest.questionsResourceEntryName,
                    "array",
                    context.packageName
                )
                val questionText =
                    stringArrayResource(id = questionsResourceId)[0]

                Card(
                    colors = CardDefaults.cardColors().copy(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotional_state_test_question_card_rounded_corner_size)))
                        .border(
                            1.dp,
                            Color.Black,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.emotional_state_test_question_card_rounded_corner_size))
                        )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(
                                horizontal = dimensionResource(id = R.dimen.emotional_state_test_question_card_horizontal_padding),
                                vertical = dimensionResource(id = R.dimen.emotional_state_test_question_card_vertical_padding)
                            )
                            .defaultMinSize(minHeight = dimensionResource(id = R.dimen.emotional_state_test_question_card_min_height))
                    ) {
                        Text(
                            text = questionText,
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                val selectedOption by rememberSaveable {
                    mutableStateOf<Int?>(2)
                }

                val optionsTexts = stringArrayResource(id = R.array.emotional_state_test_options)

                optionsTexts.forEachIndexed { index, optionText ->
                    Card(
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.option_button_rounded_corner_size)),
                        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = if (selectedOption == index) 2.dp else 1.dp,
                                color = when {
                                    selectedOption == index -> Color.Black
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
                                selected = selectedOption == index,
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

                    if (index != optionsTexts.size - 1) {
                        Spacer(Modifier.height(8.dp))
                    }
                }

                Spacer(Modifier.height(30.dp))

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
                            text = stringResource(id = R.string.next_question),
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