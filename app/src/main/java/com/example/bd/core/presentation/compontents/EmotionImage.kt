package com.example.bd.core.presentation.compontents

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.db.R
import java.io.File

@Composable
fun EmotionImage(context: Context, emotionImageFileName: String) {
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
            .size(
                dimensionResource(id = R.dimen.emotion_image_width),
                dimensionResource(id = R.dimen.emotion_image_height)
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
    )
}