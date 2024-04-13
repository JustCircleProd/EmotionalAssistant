package com.example.bd.emotionAdditionalInfo.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.compontents.textFields.emotionAdditionalInfo.DateTextField
import com.example.bd.core.presentation.compontents.textFields.emotionAdditionalInfo.NoteTextField
import com.example.bd.core.presentation.compontents.textFields.emotionAdditionalInfo.TimeTextField
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.SubtitleTextColor
import com.example.bd.core.presentation.theme.White
import com.example.db.R
import java.time.LocalDateTime

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
                    onValueChange = {
                        dateTime = it.atTime(dateTime.toLocalTime())
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                TimeTextField(
                    time = dateTime.toLocalTime(),
                    onValueChange = {
                        dateTime = dateTime.withHour(it.hour).withMinute(it.minute)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                var note by remember { mutableStateOf("") }

                NoteTextField(
                    note = note,
                    onValueChange = {
                        note = it
                    },
                    modifier = Modifier.fillMaxWidth()
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

@Preview
@Composable
private fun Preview() {
    BdTheme {
        Surface {
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
                        LocalDateTime.of(2024, 2, 27, 12, 0)
                    )
                }

                DateTextField(
                    date = dateTime,
                    onValueChange = {
                        dateTime = it.atTime(dateTime.toLocalTime())
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                TimeTextField(
                    time = dateTime.toLocalTime(),
                    onValueChange = {
                        dateTime = dateTime.withHour(it.hour).withMinute(it.minute)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                var note by remember { mutableStateOf("Заметка") }

                NoteTextField(
                    note = note,
                    onValueChange = {
                        note = it
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(40.dp))

                MyButton(
                    text = stringResource(id = R.string.confirm),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    }
                )
            }
        }
    }
}