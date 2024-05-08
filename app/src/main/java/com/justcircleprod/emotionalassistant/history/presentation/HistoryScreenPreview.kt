package com.justcircleprod.emotionalassistant.history.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
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
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateName
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyIconButton
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.BottomSheetCardContainerColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.BottomSheetContainerColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.MyRippleTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.Red
import com.justcircleprod.emotionalassistant.core.presentation.theme.SubtitleTextColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.getMonthName
import com.justcircleprod.emotionalassistant.history.presentation.components.AddEmotionCard
import com.justcircleprod.emotionalassistant.history.presentation.components.EmotionalStateCard
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HistoryScreenPreview(isBottomSheetExpanded: Boolean = true) {
    BdTheme {
        Surface {
            val scaffoldState = if (isBottomSheetExpanded) {
                rememberBottomSheetScaffoldState(
                    rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
                )
            } else {
                rememberBottomSheetScaffoldState()
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
                        Text(
                            text = "27.02.2024",
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

                            item {
                                EmotionalStateCard(
                                    EmotionalStateName.DEPRESSION,
                                    onRecommendationButtonClick = {

                                    },
                                    onDeleteButtonClick = {

                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            item {
                                EmotionalStateCard(
                                    EmotionalStateName.ASTHENIA,
                                    onRecommendationButtonClick = {

                                    },
                                    onDeleteButtonClick = {

                                    },
                                    modifier = Modifier.weight(1f)
                                )
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

                                    }
                                )
                            }

                            item {
                                EmotionCard(
                                    modifier = Modifier.weight(1f),
                                )
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

                    MyCalendar()

                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                }
            }
        }
    }
}

@Composable
private fun EmotionCard(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = BottomSheetCardContainerColor
        ),
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size)))
            .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_preview),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
                    .fillMaxWidth()
            )

            val backgroundAlpha = 0.3f

            Box(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
                    .fillMaxWidth()
                    .background(Color.Black.copy(backgroundAlpha))
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.calendar_day_card_padding))
            ) {
                Column {
                    Text(
                        text = "Эмоция",
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = White
                    )

                    Text(
                        text = "12:00",
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 15.sp,
                        color = SubtitleTextColor
                    )
                }

                EmotionActionButtons(
                    onEditButtonClick = {},
                    onDeleteButtonClick = {},
                    onDetailButtonClick = { }
                )
            }
        }
    }
}

@Composable
private fun EmotionActionButtons(
    onEditButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onDetailButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            MyIconButton(
                imageVector = Icons.Rounded.Edit,
                onClick = onEditButtonClick
            )

            CompositionLocalProvider(LocalRippleTheme provides MyRippleTheme(color = Red)) {
                MyIconButton(
                    imageVector = Icons.Rounded.Delete,
                    iconTintColor = MaterialTheme.colorScheme.error,
                    onClick = onDeleteButtonClick
                )
            }
        }

        MyIconButton(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            onClick = onDetailButtonClick,
            iconModifier = Modifier.rotate(180f)
        )
    }
}

@Composable
private fun MyCalendar(
    modifier: Modifier = Modifier
) {
    val currentMonth = remember { YearMonth.of(2024, 2) }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val daysOfWeek = remember { daysOfWeek() }


    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )

        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(state)

        CalendarTitle(
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            }
        )

        Spacer(Modifier.height(6.dp))

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(
                    day = day,
                    isSelected = false,
                    onClick = {

                    }
                )
            },
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            }
        )
    }
}

@Composable
private fun rememberFirstMostVisibleMonth(
    state: CalendarState
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }

    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth() }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }

    return visibleMonth.value
}

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 90f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}

@Composable
private fun CalendarTitle(
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackMonthButton(
            onClick = goToPrevious,
        )

        val monthStr = getMonthName(LocalContext.current, currentMonth.monthValue)
        val yearStr = currentMonth.year.toString()

        Text(
            text = "$monthStr $yearStr",
            fontFamily = AlegreyaFontFamily,
            fontSize = 21.sp,
            color = White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        ForwardMonthButton(
            onClick = goToNext,
        )
    }
}

@Composable
private fun BackMonthButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(shape = CircleShape)
            .size(dimensionResource(id = R.dimen.icon_button_size))
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            contentDescription = null,
            tint = White,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_button_icon_size))
        )
    }
}

@Composable
private fun ForwardMonthButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(shape = CircleShape)
            .size(dimensionResource(id = R.dimen.icon_button_size))
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            contentDescription = null,
            tint = White,
            modifier = Modifier
                .rotate(180f)
                .size(dimensionResource(id = R.dimen.icon_button_icon_size))
        )
    }
}

@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                textAlign = TextAlign.Center,
                fontFamily = AlegreyaFontFamily,
                fontSize = 16.sp,
                color = White,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(dimensionResource(id = R.dimen.calendar_day_cell_padding))
            .clip(CircleShape)
            .clickable { onClick(day) }
            .run {
                if (day.date == LocalDate.of(2024, 2, 27) && !isSelected) {
                    border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                } else {
                    this
                }
            }
    ) {
        if (day.position != DayPosition.MonthDate) return@Box

        if (
            day.date == LocalDate.of(2024, 2, 7) ||
            day.date == LocalDate.of(2024, 2, 10) ||
            day.date == LocalDate.of(2024, 2, 15) ||
            day.date == LocalDate.of(2024, 2, 18) ||
            day.date == LocalDate.of(2024, 2, 25) ||
            day.date == LocalDate.of(2024, 2, 27)
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_preview),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
                    .fillMaxWidth()
            )
        }

        val backgroundAlpha = 0.3f

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize()
                .background(
                    when {
                        isSelected -> {
                            MaterialTheme.colorScheme.primary.copy(alpha = backgroundAlpha)
                        }

                        !isSelected && (day.date == LocalDate.of(2024, 2, 7) ||
                                day.date == LocalDate.of(2024, 2, 10) ||
                                day.date == LocalDate.of(2024, 2, 15) ||
                                day.date == LocalDate.of(2024, 2, 18) ||
                                day.date == LocalDate.of(2024, 2, 25) ||
                                day.date == LocalDate.of(2024, 2, 27)) -> {
                            Color.Black.copy(alpha = backgroundAlpha)
                        }

                        else -> {
                            Color.Transparent
                        }
                    }
                )
        )

        val dayOfMonth = day.date.dayOfMonth
        val dayStr = if (dayOfMonth.toString().length == 1) "0$dayOfMonth" else "$dayOfMonth"

        Text(
            text = dayStr,
            fontFamily = AlegreyaFontFamily,
            fontSize = 16.sp,
            color = White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HistoryScreenPreviewPrototype() {
    val isBottomSheetExpanded = true

    Surface {
        val scaffoldState = if (isBottomSheetExpanded) {
            rememberBottomSheetScaffoldState(
                rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
            )
        } else {
            rememberBottomSheetScaffoldState()
        }

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContainerColor = Color.White,
            sheetContent = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.bottom_sheet_horizontal_padding))
                        .padding(bottom = dimensionResource(id = R.dimen.bottom_sheet_bottom_padding))
                ) {
                    Text(
                        text = "27.02.2024",
                        fontWeight = FontWeight.Medium,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 24.sp,
                        color = Color.Black,
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
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )

                            Spacer(Modifier.height(8.dp))
                        }

                        item {
                            EmotionalStateCardPrototype(
                                modifier = Modifier.weight(1f)
                            )
                        }

                        item {
                            EmotionalStateCardPrototype(
                                modifier = Modifier.weight(1f)
                            )
                        }

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Spacer(Modifier.height(30.dp))

                            Text(
                                text = stringResource(id = R.string.emotions),
                                fontWeight = FontWeight.Medium,
                                fontFamily = AlegreyaFontFamily,
                                fontSize = 19.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )

                            Spacer(Modifier.height(8.dp))
                        }

                        item {
                            AddEmotionCardPrototype(
                                modifier = Modifier.weight(1f)
                            )
                        }

                        item {
                            EmotionCardPrototype(
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            },
            sheetPeekHeight = 0.dp,
            sheetDragHandle = { BottomSheetDefaults.DragHandle(color = Color.Black) }
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
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                MyCalendarPrototype()

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}

@Composable
private fun EmotionCardPrototype(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        ),
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size)))
            .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
            .border(
                1.dp,
                Color.Black,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size))
            )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
        ) {
            Image(
                painter = painterResource(id = R.drawable.prototype_image_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
                    .fillMaxWidth()
            )

            val backgroundAlpha = 0.3f

            Box(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
                    .fillMaxWidth()
                    .background(Color.Black.copy(backgroundAlpha))
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.calendar_day_card_padding))
            ) {
                Column {
                    Text(
                        text = "Lorem",
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    Text(
                        text = "12:00",
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

                EmotionActionButtonsPrototype(

                )
            }
        }
    }
}

@Composable
private fun EmotionActionButtonsPrototype(

) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            IconButton(
                onClick = { },
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_size))
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_size))
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                )
            }
        }

        IconButton(
            onClick = { },
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_size))
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_button_icon_size))
                    .rotate(180f)
            )
        }
    }
}

@Composable
private fun MyCalendarPrototype(
    modifier: Modifier = Modifier
) {
    val currentMonth = remember { YearMonth.of(2024, 2) }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val daysOfWeek = remember { daysOfWeek() }


    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )

        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(state)

        CalendarTitlePrototype(
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            }
        )

        Spacer(Modifier.height(6.dp))

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                DayPrototype(
                    day = day,
                    isSelected = false,
                    onClick = {

                    }
                )
            },
            monthHeader = {
                MonthHeaderPrototype(daysOfWeek = daysOfWeek)
            }
        )
    }
}

@Composable
private fun CalendarTitlePrototype(
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackMonthButtonPrototype(
            onClick = goToPrevious,
        )

        val monthStr = getMonthName(LocalContext.current, currentMonth.monthValue)
        val yearStr = currentMonth.year.toString()

        Text(
            text = "$monthStr $yearStr",
            fontFamily = AlegreyaFontFamily,
            fontSize = 21.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        ForwardMonthButtonPrototype(
            onClick = goToNext,
        )
    }
}

@Composable
private fun BackMonthButtonPrototype(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(shape = CircleShape)
            .size(dimensionResource(id = R.dimen.icon_button_size))
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            tint = Color.Black,
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
        )
    }
}

@Composable
private fun ForwardMonthButtonPrototype(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(shape = CircleShape)
            .size(dimensionResource(id = R.dimen.icon_button_size))
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .rotate(180f)
                .size(dimensionResource(id = R.dimen.icon_button_icon_size))
        )
    }
}

@Composable
private fun MonthHeaderPrototype(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                textAlign = TextAlign.Center,
                fontFamily = AlegreyaFontFamily,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DayPrototype(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(dimensionResource(id = R.dimen.calendar_day_cell_padding))
            .clip(CircleShape)
            .clickable { onClick(day) }
            .run {
                if (day.date == LocalDate.of(2024, 2, 27) && !isSelected) {
                    border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                } else {
                    this
                }
            }
    ) {
        if (day.position != DayPosition.MonthDate) return@Box

        if (
            day.date == LocalDate.of(2024, 2, 7) ||
            day.date == LocalDate.of(2024, 2, 10) ||
            day.date == LocalDate.of(2024, 2, 15) ||
            day.date == LocalDate.of(2024, 2, 18) ||
            day.date == LocalDate.of(2024, 2, 25) ||
            day.date == LocalDate.of(2024, 2, 27)
        ) {
            Image(
                painter = painterResource(id = R.drawable.prototype_image_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
                    .fillMaxWidth()
            )
        }

        val backgroundAlpha = 0.3f

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize()
                .background(
                    when {
                        isSelected -> {
                            Color.Black.copy(alpha = backgroundAlpha)
                        }

                        !isSelected && (day.date == LocalDate.of(2024, 2, 7) ||
                                day.date == LocalDate.of(2024, 2, 10) ||
                                day.date == LocalDate.of(2024, 2, 15) ||
                                day.date == LocalDate.of(2024, 2, 18) ||
                                day.date == LocalDate.of(2024, 2, 25) ||
                                day.date == LocalDate.of(2024, 2, 27)) -> {
                            Color.Black.copy(alpha = backgroundAlpha)
                        }

                        else -> {
                            Color.Transparent
                        }
                    }
                )
        )

        val dayOfMonth = day.date.dayOfMonth
        val dayStr = if (dayOfMonth.toString().length == 1) "0$dayOfMonth" else "$dayOfMonth"

        Text(
            text = dayStr,
            fontFamily = AlegreyaFontFamily,
            fontSize = 16.sp,
            color = if (day.date == LocalDate.of(2024, 2, 7) ||
                day.date == LocalDate.of(2024, 2, 10) ||
                day.date == LocalDate.of(2024, 2, 15) ||
                day.date == LocalDate.of(2024, 2, 18) ||
                day.date == LocalDate.of(2024, 2, 25) ||
                day.date == LocalDate.of(2024, 2, 27)
            ) {
                White
            } else Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun EmotionalStateCardPrototype(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        ),
        onClick = { },
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size)))
            .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
            .border(
                1.dp,
                Color.Black,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size))
            )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
                .padding(dimensionResource(id = R.dimen.calendar_day_card_padding))
        ) {
            Text(
                text = "Lorem ipsum",
                fontFamily = AlegreyaFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_size))
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                    )
                }

                IconButton(
                    onClick = { },
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_size))
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.icon_button_icon_size))
                            .rotate(180f)
                    )
                }
            }
        }
    }
}

@Composable
fun AddEmotionCardPrototype(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        ),
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size)))
            .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
            .border(
                1.dp,
                Color.Black,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size))
            )
            .clickable { }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.calendar_day_card_padding))
            ) {
                Text(
                    text = stringResource(R.string.add_emotion),
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                )
            }
        }
    }
}