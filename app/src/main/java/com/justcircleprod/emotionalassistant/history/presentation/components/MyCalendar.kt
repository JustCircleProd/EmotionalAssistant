package com.justcircleprod.emotionalassistant.history.presentation.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.domain.models.Emotion
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.getMonthName
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
import java.io.File
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MyCalendar(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    emotions: List<Emotion>,
    modifier: Modifier = Modifier
) {
    val currentMonth = remember { YearMonth.now() }
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
                    isSelected = selectedDate == day.date,
                    onClick = {
                        onDateSelected(it.date)
                    },
                    emotionImageFileName = emotions.firstOrNull {
                        it.dateTime.toLocalDate() == day.date && it.imageFileName != null
                    }?.imageFileName
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
            .size(dimensionResource(id = R.dimen.icon_button_size))
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            contentDescription = null,
            tint = White,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
        )
    }
}

@Composable
private fun ForwardMonthButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
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
    onClick: (CalendarDay) -> Unit,
    emotionImageFileName: String? = null,
) {
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(dimensionResource(id = R.dimen.calendar_day_cell_padding))
            .clip(CircleShape)
            .run {
                if (day.position == DayPosition.MonthDate) {
                    clickable { onClick(day) }
                }

                if (day.date == LocalDate.now() && !isSelected) {
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

        if (emotionImageFileName != null) {
            val imagePath =
                File(context.filesDir, emotionImageFileName)

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imagePath)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize()
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

                        !isSelected && emotionImageFileName != null -> {
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