package com.example.bd.history.presentation.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.presentation.compontents.buttons.MyIconButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BottomSheetCardContainerColor
import com.example.bd.core.presentation.theme.Red
import com.example.bd.core.presentation.theme.SubtitleTextColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.utils.getEmotionNameString
import com.example.db.R
import java.io.File

@Composable
fun EmotionResultCard(
    emotion: Emotion,
    onClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = BottomSheetCardContainerColor
        ),
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size)))
            .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
            .clickable { }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
        ) {
            val context = LocalContext.current

            val emotionImageFileName =
                emotion.imageFileName

            if (emotionImageFileName != null) {
                EmotionResultImage(context, emotionImageFileName)
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.calendar_day_card_padding))
            ) {
                EmotionResultInfo(context, emotion)

                EmotionResultActionButtons(
                    onEditButtonClick = {},
                    onDeleteButtonClick = onDeleteButtonClick,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun EmotionResultImage(context: Context, emotionImageFileName: String) {
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
}

@Composable
private fun EmotionResultInfo(
    context: Context,
    emotion: Emotion
) {
    Column {
        Text(
            text = getEmotionNameString(
                context,
                emotion.name
            ),
            fontFamily = AlegreyaFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = White
        )

        val hourStr = emotion.dateTime.hour.let {
            if (it.toString().length == 1) {
                "0$it"
            } else {
                it
            }
        }
        val minuteStr = emotion.dateTime.minute.let {
            if (it.toString().length == 1) {
                "0$it"
            } else {
                it
            }
        }

        Text(
            text = "$hourStr:$minuteStr",
            fontFamily = AlegreyaFontFamily,
            fontSize = 15.sp,
            color = SubtitleTextColor
        )
    }
}

@Composable
private fun EmotionResultActionButtons(
    onEditButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onClick: () -> Unit
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

            MyIconButton(
                imageVector = Icons.Rounded.Delete,
                iconTintColor = Red,
                onClick = onDeleteButtonClick
            )
        }

        MyIconButton(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            onClick = onClick,
            iconModifier = Modifier.rotate(180f)
        )
    }
}