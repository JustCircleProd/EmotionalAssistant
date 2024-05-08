package com.justcircleprod.emotionalassistant.history.presentation.components

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.domain.models.Emotion
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyIconButton
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BottomSheetCardContainerColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.MyRippleTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.Red
import com.justcircleprod.emotionalassistant.core.presentation.theme.SubtitleTextColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.formatLocalTime
import com.justcircleprod.emotionalassistant.core.presentation.util.getEmotionNameString
import java.io.File

@Composable
fun EmotionCard(
    emotion: Emotion,
    onEditButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onDetailButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = BottomSheetCardContainerColor
        ),
        onClick = { onDetailButtonClick() },
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size)))
            .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
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
                EmotionImage(context, emotionImageFileName)
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.calendar_day_card_padding))
            ) {
                EmotionInfo(context, emotion)

                EmotionActionButtons(
                    onEditButtonClick = onEditButtonClick,
                    onDeleteButtonClick = onDeleteButtonClick,
                    onDetailButtonClick = onDetailButtonClick
                )
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
private fun EmotionInfo(
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

        Text(
            text = formatLocalTime(emotion.dateTime.toLocalTime()),
            fontFamily = AlegreyaFontFamily,
            fontSize = 15.sp,
            color = SubtitleTextColor
        )
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