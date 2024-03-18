package com.example.bd.history.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BottomSheetCardContainerColor
import com.example.bd.core.presentation.theme.BottomSheetContainerColor
import com.example.bd.core.presentation.theme.SubtitleTextColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.history.presentation.components.EmotionResultCard
import com.example.bd.history.presentation.components.MyCalendar
import com.example.db.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController, viewModel: HistoryViewModel = hiltViewModel()) {
    Surface {
        val scaffoldState = rememberBottomSheetScaffoldState()

        val emotionResultsWithEmotion =
            viewModel.emotionResultsWithEmotion.collectAsStateWithLifecycle(
                initialValue = listOf()
            )

        val selectedDate = viewModel.selectedDate.collectAsStateWithLifecycle()

        LaunchedEffect(selectedDate.value) {
            if (selectedDate.value == null) return@LaunchedEffect

            launch {
                scaffoldState.bottomSheetState.expand()
            }
        }

        val emotionResultsWithEmotionForSelectedDate = remember(selectedDate) {
            derivedStateOf {
                if (selectedDate.value != null) {
                    emotionResultsWithEmotion.value.filter {
                        it.emotionResult.dateTime.toLocalDate() == selectedDate.value
                    }
                } else {
                    listOf()
                }
            }
        }

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContainerColor = BottomSheetContainerColor,
            sheetContent = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.bottom_sheet_horizontal_padding))
                        .padding(bottom = dimensionResource(id = R.dimen.bottom_sheet_bottom_padding))
                ) {
                    if (selectedDate.value != null) {
                        Text(
                            text = selectedDate.value.toString(),
                            fontWeight = FontWeight.Medium,
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 24.sp,
                            color = White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                EmotionalStateCard(onClick = {})
                            }

                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Spacer(Modifier.height(12.dp))

                                Text(
                                    text = "Ваши эмоции",
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = AlegreyaFontFamily,
                                    fontSize = 19.sp,
                                    color = White,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(Modifier.height(8.dp))
                            }

                            if (emotionResultsWithEmotionForSelectedDate.value.isEmpty()) {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    Text(
                                        text = stringResource(id = R.string.no_emotions),
                                        fontFamily = AlegreyaFontFamily,
                                        fontSize = 17.sp,
                                        color = SubtitleTextColor,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                                return@LazyVerticalGrid
                            }

                            items(emotionResultsWithEmotionForSelectedDate.value.size) {
                                val emotionResultWithEmotion =
                                    emotionResultsWithEmotionForSelectedDate.value[it]

                                EmotionResultCard(
                                    emotionResultWithEmotion,
                                    modifier = Modifier.weight(1f),
                                    onClick = {

                                    },
                                    onDeleteButtonClick = {
                                        Log.d(
                                            "Tag111",
                                            emotionResultWithEmotion.emotionResult.toString()
                                        )
                                        viewModel.deleteEmotionResult(emotionResultWithEmotion.emotionResult)
                                    }
                                )
                            }
                        }
                    }
                }
            },
            sheetPeekHeight = 0.dp,
            sheetDragHandle = { BottomSheetDefaults.DragHandle(color = White) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(dimensionResource(id = R.dimen.main_screens_space))
            ) {
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
                    emotionResultsWithEmotion = emotionResultsWithEmotion
                )
            }
        }
    }
}


@Composable
private fun EmotionalStateCard(
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = BottomSheetCardContainerColor
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size)))
            .height(dimensionResource(id = R.dimen.calendar_day_emotional_state_card_height))
            .clickable { }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.calendar_day_emotional_state_card_height))
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.calendar_day_card_padding))
            ) {
                Text(
                    text = "Эмоциональное состояние",
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = White,
                    textAlign = TextAlign.Start
                )

                IconButton(
                    onClick = onClick,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.icon_button_size))
                        .align(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.icon_button_icon_size))
                            .rotate(180f)

                    )
                }
            }
        }
    }
}