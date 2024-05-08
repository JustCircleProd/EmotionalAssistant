package com.justcircleprod.emotionalassistant.emotionDetail.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ModeEdit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
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
import com.justcircleprod.emotionalassistant.core.presentation.compontents.EmotionImage
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.BackButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyIconButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.textFields.emotionAdditionalInfo.DateTextField
import com.justcircleprod.emotionalassistant.core.presentation.compontents.textFields.emotionAdditionalInfo.NoteTextField
import com.justcircleprod.emotionalassistant.core.presentation.compontents.textFields.emotionAdditionalInfo.TimeTextField
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.SnackbarContainerColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.SnackbarContentColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.TonalButtonColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.formatLocalDate
import com.justcircleprod.emotionalassistant.core.presentation.util.formatLocalTime
import com.justcircleprod.emotionalassistant.core.presentation.util.getEmotionNameString
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun EmotionDetailScreen(
    navController: NavController,
    inEditMode: Boolean,
    viewModel: EmotionDetailViewModel = hiltViewModel()
) {
    Surface {
        val emotion by viewModel.emotion.collectAsStateWithLifecycle(null)

        if (emotion == null) return@Surface

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
                        .padding(dimensionResource(id = R.dimen.toolbar_padding))
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
                        .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                        .animateContentSize()
                ) {
                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

                    val context = LocalContext.current

                    val emotionImageFileName = emotion!!.imageFileName

                    if (emotionImageFileName != null) {
                        EmotionImage(context, emotionImageFileName)
                    }

                    Spacer(Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = getEmotionNameString(
                                context,
                                emotion!!.name
                            ),
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 24.sp,
                            color = White,
                        )

                        if (inEditModeState) {
                            Spacer(Modifier.width(2.dp))

                            MyIconButton(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = stringResource(R.string.edit_emotion),
                                onClick = {
                                    navController.navigate(
                                        NavigationItem.EmotionRecognitionMethodSelection.getRouteWithArguments(
                                            returnRoute = NavigationItem.EmotionDetail.getRouteWithArguments(
                                                emotion!!.id,
                                                inEditMode = true
                                            ),
                                            emotionId = emotion!!.id
                                        )
                                    )
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

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

                    var note by remember { mutableStateOf(emotion!!.note ?: "") }

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
                                navController.navigate(
                                    NavigationItem.EmotionRecommendation.getRouteWithArguments(
                                        emotion!!.id
                                    )
                                )
                            }
                        )
                    }

                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                }
            }
        }
    }
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
                        .padding(dimensionResource(id = R.dimen.toolbar_padding))
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
                        .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                        .animateContentSize()
                ) {
                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

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
                            Spacer(Modifier.width(2.dp))

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

                    Spacer(Modifier.height(30.dp))

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

                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
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
                        .padding(dimensionResource(id = R.dimen.toolbar_padding))
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
                        .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                        .animateContentSize()
                ) {
                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

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
                            Spacer(Modifier.width(2.dp))

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

                    Spacer(modifier = Modifier.height(30.dp))

                    if (inEditMode) {
                        MyButton(
                            text = stringResource(id = R.string.confirm),
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

                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPrototype() {
    Surface {
        val inEditMode by remember {
            mutableStateOf(false)
        }

        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.toolbar_padding))
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.toolbar_padding))
                        .size(dimensionResource(id = R.dimen.icon_button_size))
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                    )
                }

                if (inEditMode) {
                    Text(
                        text = stringResource(R.string.edit),
                        fontWeight = FontWeight.Medium,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 21.sp,
                        color = Color.Black,
                    )
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_size))
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

                Image(
                    painter = painterResource(id = R.drawable.prototype_image_placeholder),
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
                        text = "Lorem",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 24.sp,
                        color = Color.Black,
                    )

                    if (inEditMode) {
                        Spacer(Modifier.width(2.dp))

                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_size))
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

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

                Spacer(Modifier.height(30.dp))

                if (inEditMode) {
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
                } else {
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
                                text = stringResource(id = R.string.recommendations),
                                fontFamily = AlegreyaFontFamily,
                                fontSize = 19.sp,
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewInEditModePrototype() {
    Surface {
        val inEditMode by remember {
            mutableStateOf(true)
        }

        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.toolbar_padding))
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.toolbar_padding))
                        .size(dimensionResource(id = R.dimen.icon_button_size))
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                    )
                }

                if (inEditMode) {
                    Text(
                        text = stringResource(R.string.edit),
                        fontWeight = FontWeight.Medium,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 21.sp,
                        color = Color.Black,
                    )
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_size))
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

                Image(
                    painter = painterResource(id = R.drawable.prototype_image_placeholder),
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
                        text = "Lorem",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 24.sp,
                        color = Color.Black,
                    )

                    if (inEditMode) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_size))
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

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

                Spacer(Modifier.height(30.dp))

                if (inEditMode) {
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
                } else {
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
                                text = stringResource(id = R.string.recommendations),
                                fontFamily = AlegreyaFontFamily,
                                fontSize = 19.sp,
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}