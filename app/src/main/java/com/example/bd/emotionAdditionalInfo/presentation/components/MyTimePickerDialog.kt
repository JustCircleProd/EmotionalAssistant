package com.example.bd.emotionAdditionalInfo.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.bd.core.presentation.theme.PeriodSelectorBorderColor
import com.example.bd.core.presentation.theme.PeriodSelectorUnselectedContentColor
import com.example.bd.core.presentation.theme.TimePickerDialColor
import com.example.bd.core.presentation.theme.TimeSelectorUnselectedContainerColor
import com.example.bd.core.presentation.theme.White
import com.example.db.R
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePickerDialog(
    initialTime: LocalTime = LocalTime.now(),
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute
    )

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(shape = MaterialTheme.shapes.extraLarge) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = stringResource(R.string.select_time), fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(20.dp))

                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors().copy(
                        clockDialColor = TimePickerDialColor,
                        periodSelectorBorderColor = PeriodSelectorBorderColor,
                        periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                        periodSelectorSelectedContentColor = White,
                        periodSelectorUnselectedContentColor = PeriodSelectorUnselectedContentColor,
                        timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                        timeSelectorUnselectedContainerColor = TimeSelectorUnselectedContainerColor,
                        timeSelectorSelectedContentColor = White,
                        timeSelectorUnselectedContentColor = White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(stringResource(R.string.cancel))
                    }

                    TextButton(
                        onClick = {
                            val selectedTime =
                                LocalTime.of(timePickerState.hour, timePickerState.minute)
                            onTimeSelected(selectedTime)

                            onDismiss()
                        }
                    ) {
                        Text(stringResource(R.string.confirm))
                    }
                }
            }
        }
    }
}