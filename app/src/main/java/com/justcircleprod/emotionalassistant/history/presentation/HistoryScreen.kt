package com.justcircleprod.emotionalassistant.history.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BottomSheetContainerColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.SubtitleTextColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.formatLocalDate
import com.justcircleprod.emotionalassistant.history.presentation.components.AddEmotionCard
import com.justcircleprod.emotionalassistant.history.presentation.components.EmotionCard
import com.justcircleprod.emotionalassistant.history.presentation.components.EmotionalStateResultCard
import com.justcircleprod.emotionalassistant.history.presentation.components.MyCalendar
import com.justcircleprod.emotionalassistant.R
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController, viewModel: HistoryViewModel = hiltViewModel()) {
    Surface {
        val scaffoldState = rememberBottomSheetScaffoldState()

        val emotions by viewModel.emotions.collectAsStateWithLifecycle(initialValue = emptyList())

        val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()

        LaunchedEffect(selectedDate) {
            if (selectedDate == null) return@LaunchedEffect

            launch {
                scaffoldState.bottomSheetState.expand()
            }
        }

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContainerColor = BottomSheetContainerColor,
            sheetContent = {
                BottomSheetContent(
                    selectedDate,
                    navController,
                    viewModel
                )
            },
            sheetPeekHeight = 0.dp,
            sheetDragHandle = { BottomSheetDefaults.DragHandle(color = White) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

                Text(
                    text = stringResource(R.string.history_of_your_states),
                    fontWeight = FontWeight.Medium,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    color = White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                MyCalendar(
                    selectedDate = selectedDate,
                    onDateSelected = {
                        viewModel.selectedDate.value = it
                    },
                    emotions = emotions
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}

@Composable
private fun BottomSheetContent(
    selectedDate: LocalDate?,
    navController: NavController,
    viewModel: HistoryViewModel
) {
    val emotionsForSelectedDate by viewModel.emotionsForSelectedDate.collectAsStateWithLifecycle()

    val emotionalStateResultsForSelectedDate by viewModel.emotionalStateTestResultsForSelectedDate.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.bottom_sheet_horizontal_padding))
            .padding(bottom = dimensionResource(id = R.dimen.bottom_sheet_bottom_padding))
    ) {
        if (selectedDate != null) {
            Text(
                text = formatLocalDate(selectedDate),
                fontWeight = FontWeight.Medium,
                fontFamily = AlegreyaFontFamily,
                fontSize = 24.sp,
                color = White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            if (emotionsForSelectedDate.isEmpty() && emotionalStateResultsForSelectedDate.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_data),
                    fontWeight = FontWeight.Medium,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 19.sp,
                    color = White,
                    textAlign = TextAlign.Center
                )

                return@Column
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = stringResource(R.string.emotional_states),
                        fontWeight = FontWeight.Medium,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 19.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )
                }

                if (emotionalStateResultsForSelectedDate.isNotEmpty()) {
                    items(emotionalStateResultsForSelectedDate.size) {
                        val testResult = emotionalStateResultsForSelectedDate[it]
                        val emotionalStateName =
                            testResult.emotionalStateTest?.emotionalStateName ?: return@items

                        EmotionalStateResultCard(
                            emotionalStateName,
                            onRecommendationButtonClick = {
                                navController.navigate(
                                    NavigationItem.EmotionalStateRecommendation.getRouteWithArguments(
                                        emotionalStateName
                                    )
                                )
                            },
                            onDeleteButtonClick = {
                                viewModel.deleteEmotionalStateTestResult(testResult)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = stringResource(R.string.no_data),
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 17.sp,
                            color = SubtitleTextColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(Modifier.height(5.dp))
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = stringResource(id = R.string.emotions),
                        fontWeight = FontWeight.Medium,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 19.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    AddEmotionCard(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(
                                NavigationItem.EmotionRecognitionMethodSelection.getRouteWithArguments(
                                    returnRoute = NavigationItem.History.route,
                                    date = selectedDate
                                )
                            )
                        }
                    )
                }

                items(emotionsForSelectedDate.size) {
                    val emotion = emotionsForSelectedDate[it]

                    EmotionCard(
                        emotion,
                        modifier = Modifier.weight(1f),
                        onDetailButtonClick = {
                            navController.navigate(
                                NavigationItem.EmotionDetail.getRouteWithArguments(emotion.id)
                            )
                        },
                        onEditButtonClick = {
                            navController.navigate(
                                NavigationItem.EmotionDetail.getRouteWithArguments(
                                    emotion.id,
                                    inEditMode = true
                                )
                            )
                        },
                        onDeleteButtonClick = {
                            viewModel.deleteEmotion(emotion)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    HistoryScreenPreview(isBottomSheetExpanded = true)
}