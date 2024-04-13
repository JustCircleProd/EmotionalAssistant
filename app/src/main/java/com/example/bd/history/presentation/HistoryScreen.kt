package com.example.bd.history.presentation

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
import com.example.bd.core.presentation.compontents.NavigationItem
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BottomSheetContainerColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.presentation.util.formatLocalDate
import com.example.bd.history.presentation.components.AddEmotionCard
import com.example.bd.history.presentation.components.EmotionCard
import com.example.bd.history.presentation.components.MyCalendar
import com.example.db.R
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
                    emotions = emotions
                )
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
    val emotionsForSelectedDate = viewModel.emotionsForSelectedDate.collectAsStateWithLifecycle()

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

            Spacer(Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
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

                    Spacer(Modifier.height(8.dp))
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(Modifier.height(30.dp))

                    Text(
                        text = stringResource(id = R.string.emotions),
                        fontWeight = FontWeight.Medium,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 19.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(8.dp))
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

                items(emotionsForSelectedDate.value.size) {
                    val emotion = emotionsForSelectedDate.value[it]

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
                            viewModel.deleteEmotionResult(emotion)
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