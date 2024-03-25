package com.example.bd.emotionAdditionalInfo.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bd.core.presentation.compontents.MyTextField
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.SubtitleTextColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.utils.formatLocalDate
import com.example.bd.core.utils.formatLocalTime
import com.example.bd.emotionAdditionalInfo.presentation.components.MyDatePickerDialog
import com.example.bd.emotionAdditionalInfo.presentation.components.MyTimePickerDialog
import com.example.db.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun EmotionAdditionalInfoScreen(
    navController: NavController,
    viewModel: EmotionAdditionalInfoViewModel = hiltViewModel()
) {
    Surface {
        val emotion = viewModel.emotion?.collectAsStateWithLifecycle(null)

        if (emotion?.value != null) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                Text(
                    text = stringResource(R.string.emotion_added),
                    fontWeight = FontWeight.Bold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    color = White,
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = stringResource(R.string.would_you_like_to_add_additional_info),
                    fontSize = 19.sp,
                    fontFamily = AlegreyaFontFamily,
                    color = SubtitleTextColor,
                )

                Spacer(modifier = Modifier.height(50.dp))

                var dateTime by remember {
                    mutableStateOf(
                        emotion.value!!.dateTime
                    )
                }

                DateTextField(
                    date = dateTime,
                    onValueChanged = {
                        dateTime = it.atTime(dateTime.toLocalTime())
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                TimeTextField(
                    time = dateTime.toLocalTime(),
                    onValueChanged = {
                        dateTime = dateTime.withHour(it.hour).withMinute(it.minute)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                var note by remember { mutableStateOf("") }

                MyTextField(
                    value = note,
                    maxLines = 3,
                    labelText = stringResource(R.string.note),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = { note = it },
                )

                Spacer(modifier = Modifier.height(40.dp))

                MyButton(
                    text = stringResource(id = R.string.confirm),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.onEvent(
                            EmotionAdditionalInfoEvent.SaveAdditionalInfo(
                                dateTime,
                                note
                            )
                        )
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
private fun DateTextField(
    date: LocalDateTime,
    onValueChanged: (LocalDate) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }

    if (showDatePickerDialog) {
        MyDatePickerDialog(
            initialDate = date,
            onDateSelected = { onValueChanged(it) },
            onDismiss = { showDatePickerDialog = false }
        )
    }

    MyTextField(
        value = formatLocalDate(date.toLocalDate()),
        readOnly = true,
        enabled = false,
        onValueChange = { },
        labelText = stringResource(R.string.date),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
                showDatePickerDialog = true
            }
    )
}

@Composable
private fun TimeTextField(
    time: LocalTime,
    onValueChanged: (LocalTime) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var showTimePickerDialog by remember {
        mutableStateOf(false)
    }

    if (showTimePickerDialog) {
        MyTimePickerDialog(
            initialTime = time,
            onTimeSelected = onValueChanged,
            onDismiss = { showTimePickerDialog = false }
        )
    }

    MyTextField(
        value = formatLocalTime(time),
        readOnly = true,
        enabled = false,
        onValueChange = {},
        labelText = stringResource(R.string.time),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
                showTimePickerDialog = true
            }
    )
}