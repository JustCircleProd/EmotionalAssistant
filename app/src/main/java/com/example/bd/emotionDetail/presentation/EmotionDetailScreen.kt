package com.example.bd.emotionDetail.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ModeEdit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bd.core.presentation.compontents.NavigationItem
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.compontents.buttons.MyIconButton
import com.example.bd.core.presentation.compontents.textFields.emotionAdditionalInfo.DateTextField
import com.example.bd.core.presentation.compontents.textFields.emotionAdditionalInfo.NoteTextField
import com.example.bd.core.presentation.compontents.textFields.emotionAdditionalInfo.TimeTextField
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.SnackbarContainerColor
import com.example.bd.core.presentation.theme.SnackbarContentColor
import com.example.bd.core.presentation.theme.TonalButtonColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.presentation.util.getEmotionNameString
import com.example.db.R
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime

@Composable
fun EmotionDetailScreen(
    navController: NavController,
    inEditMode: Boolean,
    viewModel: EmotionDetailViewModel = hiltViewModel()
) {
    Surface {
        val emotion = viewModel.emotion?.collectAsStateWithLifecycle(null)
        val emotionValue = emotion?.value

        if (emotionValue != null) {
            var inEditModeState by rememberSaveable {
                mutableStateOf(inEditMode)
            }

            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState
                    ) { data ->
                        Snackbar(
                            containerColor = SnackbarContainerColor,
                            contentColor = SnackbarContentColor,
                            actionContentColor = MaterialTheme.colorScheme.secondary,
                            action = {
                                Text(
                                    text = data.visuals.actionLabel?.uppercase() ?: "",
                                    fontFamily = AlegreyaFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            },
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.main_screens_space))
                        ) {
                            Text(
                                text = data.visuals.message,
                                fontFamily = AlegreyaFontFamily,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Column(Modifier.padding(innerPadding)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensionResource(id = R.dimen.toolbar_padding))
                            .padding(horizontal = dimensionResource(id = R.dimen.toolbar_padding))
                    ) {
                        BackButton(
                            onClick = { navController.popBackStack() },
                        )

                        if (inEditModeState) {
                            Text(
                                text = stringResource(R.string.edit),
                                fontWeight = FontWeight.Medium,
                                fontFamily = AlegreyaFontFamily,
                                fontSize = 21.sp,
                                color = White,
                            )
                        }

                        MyIconButton(
                            imageVector = Icons.Rounded.ModeEdit,
                            contentDescription = stringResource(R.string.edit),
                            iconTintColor = if (inEditModeState) MaterialTheme.colorScheme.primary else White,
                            onClick = {
                                inEditModeState = !inEditModeState
                            }
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(dimensionResource(id = R.dimen.main_screens_space))
                    ) {
                        val context = LocalContext.current

                        val emotionImageFileName = emotionValue.imageFileName

                        if (emotionImageFileName != null) {
                            EmotionImage(context, emotionImageFileName)
                        }

                        Spacer(Modifier.height(6.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = getEmotionNameString(
                                    context,
                                    emotionValue.name
                                ),
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = AlegreyaFontFamily,
                                fontSize = 24.sp,
                                color = White,
                            )

                            if (inEditModeState) {
                                MyIconButton(
                                    imageVector = Icons.Rounded.Edit,
                                    contentDescription = stringResource(R.string.edit_emotion),
                                    onClick = {
                                        navController.navigate(
                                            NavigationItem.EmotionRecognitionMethodSelection.getRouteWithArguments(
                                                returnRoute = NavigationItem.EmotionDetail.getRouteWithArguments(
                                                    emotionValue.id
                                                ),
                                                emotionId = emotionValue.id
                                            )
                                        )
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        var dateTime by remember {
                            mutableStateOf(
                                emotionValue.dateTime
                            )
                        }

                        DateTextField(
                            date = dateTime,
                            onValueChange = {
                                dateTime = it.atTime(dateTime.toLocalTime())
                            },
                            clickable = inEditModeState,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        TimeTextField(
                            time = dateTime.toLocalTime(),
                            onValueChange = {
                                dateTime = dateTime.withHour(it.hour).withMinute(it.minute)
                            },
                            clickable = inEditModeState,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        var note by remember { mutableStateOf(emotionValue.note ?: "") }

                        NoteTextField(
                            note = note,
                            onValueChange = {
                                note = it
                            },
                            enabled = inEditModeState,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(40.dp))

                        if (inEditModeState) {
                            MyButton(
                                text = stringResource(id = R.string.confirm),
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    viewModel.onEvent(
                                        EmotionDetailEvent.OnAdditionalInfoConfirmed(
                                            dateTime = dateTime,
                                            note = note
                                        )
                                    )
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            context.getString(R.string.changes_saved),
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                    inEditModeState = false
                                }
                            )
                        } else {
                            MyButton(
                                text = stringResource(id = R.string.recommendations),
                                containerColor = TonalButtonColor,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun EmotionImage(context: Context, emotionImageFileName: String) {
    val imagePath =
        File(context.filesDir, emotionImageFileName)

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imagePath)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(225.dp, 300.dp)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
    )
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
        Surface {
            var inEditMode by remember {
                mutableStateOf(false)
            }

            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(id = R.dimen.toolbar_padding))
                        .padding(horizontal = dimensionResource(id = R.dimen.toolbar_padding))
                ) {
                    BackButton(
                        onClick = { },
                    )

                    if (inEditMode) {
                        Text(
                            text = stringResource(R.string.edit),
                            fontWeight = FontWeight.Medium,
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 21.sp,
                            color = White,
                        )
                    }

                    MyIconButton(
                        imageVector = Icons.Rounded.ModeEdit,
                        contentDescription = stringResource(R.string.edit),
                        iconTintColor = if (inEditMode) MaterialTheme.colorScheme.primary else White,
                        onClick = {
                            inEditMode = !inEditMode
                        }
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(dimensionResource(id = R.dimen.main_screens_space))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_preview),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(225.dp, 300.dp)
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.emotion),
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 24.sp,
                            color = White,
                        )

                        if (inEditMode) {
                            MyIconButton(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = stringResource(R.string.edit_emotion),
                                onClick = {}
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

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
                        clickable = inEditMode,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TimeTextField(
                        time = dateTime.toLocalTime(),
                        onValueChange = {
                            dateTime = dateTime.withHour(it.hour).withMinute(it.minute)
                        },
                        clickable = inEditMode,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    var note by remember { mutableStateOf("Заметка") }

                    NoteTextField(
                        note = note,
                        onValueChange = {
                            note = it
                        },
                        enabled = inEditMode,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(40.dp))

                    if (inEditMode) {
                        MyButton(
                            text = stringResource(id = R.string.confirm),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {

                            }
                        )
                    } else {
                        MyButton(
                            text = stringResource(R.string.recommendations),
                            containerColor = TonalButtonColor,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {

                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewInEditMode() {
    BdTheme {
        Surface {
            var inEditMode by remember {
                mutableStateOf(true)
            }

            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(id = R.dimen.toolbar_padding))
                        .padding(horizontal = dimensionResource(id = R.dimen.toolbar_padding))
                ) {
                    BackButton(
                        onClick = { },
                    )

                    if (inEditMode) {
                        Text(
                            text = stringResource(R.string.edit),
                            fontWeight = FontWeight.Medium,
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 21.sp,
                            color = White,
                        )
                    }

                    MyIconButton(
                        imageVector = Icons.Rounded.ModeEdit,
                        contentDescription = stringResource(R.string.edit),
                        iconTintColor = if (inEditMode) MaterialTheme.colorScheme.primary else White,
                        onClick = {
                            inEditMode = !inEditMode
                        }
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(dimensionResource(id = R.dimen.main_screens_space))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_preview),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(225.dp, 300.dp)
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.emotion),
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 24.sp,
                            color = White,
                        )

                        if (inEditMode) {
                            MyIconButton(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = stringResource(R.string.edit_emotion),
                                onClick = {}
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(20.dp))

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
                        clickable = inEditMode,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TimeTextField(
                        time = dateTime.toLocalTime(),
                        onValueChange = {
                            dateTime = dateTime.withHour(it.hour).withMinute(it.minute)
                        },
                        clickable = inEditMode,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    var note by remember { mutableStateOf("Заметка") }

                    NoteTextField(
                        note = note,
                        onValueChange = {
                            note = it
                        },
                        enabled = inEditMode,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    if (inEditMode) {
                        MyButton(
                            text = stringResource(id = R.string.confirm),
                            containerColor = TonalButtonColor,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {

                            }
                        )
                    } else {
                        MyButton(
                            text = stringResource(id = R.string.recommendations),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {

                            }
                        )
                    }
                }
            }
        }
    }
}