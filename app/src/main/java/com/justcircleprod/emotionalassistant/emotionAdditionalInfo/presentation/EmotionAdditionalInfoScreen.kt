package com.justcircleprod.emotionalassistant.emotionAdditionalInfo.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.textFields.emotionAdditionalInfo.DateTextField
import com.justcircleprod.emotionalassistant.core.presentation.compontents.textFields.emotionAdditionalInfo.NoteTextField
import com.justcircleprod.emotionalassistant.core.presentation.compontents.textFields.emotionAdditionalInfo.TimeTextField
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.SubtitleTextColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.formatLocalDate
import com.justcircleprod.emotionalassistant.core.presentation.util.formatLocalTime
import java.time.LocalDateTime

@Composable
fun EmotionAdditionalInfoScreen(
    navController: NavController,
    returnRoute: String?,
    viewModel: EmotionAdditionalInfoViewModel = hiltViewModel()
) {
    Surface {
        val emotion by viewModel.emotion.collectAsStateWithLifecycle(null)

        if (emotion == null || returnRoute == null) return@Surface

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                .animateContentSize()
        ) {
            Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

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

            Spacer(modifier = Modifier.height(40.dp))

            var dateTime by remember {
                mutableStateOf(
                    emotion!!.dateTime
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

            Spacer(modifier = Modifier.height(30.dp))

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

                    navController.navigate(
                        NavigationItem.EmotionRecommendation.getRouteWithArguments(
                            emotion!!.id
                        )
                    ) {
                        popUpTo(returnRoute)
                    }
                }
            )

            Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
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
                    .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

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

                Spacer(modifier = Modifier.height(40.dp))

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

                Spacer(modifier = Modifier.height(30.dp))

                MyButton(
                    text = stringResource(id = R.string.confirm),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    }
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPrototype() {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                .animateContentSize()
        ) {
            Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

            Text(
                text = stringResource(R.string.emotion_added),
                fontWeight = FontWeight.Bold,
                fontFamily = AlegreyaFontFamily,
                fontSize = 26.sp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur.",
                fontSize = 19.sp,
                fontFamily = AlegreyaFontFamily,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(40.dp))

            val dateTime by remember {
                mutableStateOf(
                    LocalDateTime.of(2024, 2, 27, 12, 0)
                )
            }

            TextField(
                value = formatLocalDate(dateTime.toLocalDate()),
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.date),
                        fontFamily = AlegreyaFontFamily,
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                },
                textStyle = TextStyle(
                    fontFamily = AlegreyaFontFamily,
                    color = Color.Black,
                    fontSize = 17.sp
                ),
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = White,
                    focusedIndicatorColor = White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = formatLocalTime(dateTime.toLocalTime()),
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.time),
                        fontFamily = AlegreyaFontFamily,
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                },
                textStyle = TextStyle(
                    fontFamily = AlegreyaFontFamily,
                    color = Color.Black,
                    fontSize = 17.sp
                ),
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = White,
                    focusedIndicatorColor = White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            val note by remember { mutableStateOf("Заметка") }

            TextField(
                value = note,
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.note),
                        fontFamily = AlegreyaFontFamily,
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                },
                textStyle = TextStyle(
                    fontFamily = AlegreyaFontFamily,
                    color = Color.Black,
                    fontSize = 17.sp
                ),
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = White,
                    focusedIndicatorColor = White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))

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
                        text = stringResource(id = R.string.confirm),
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