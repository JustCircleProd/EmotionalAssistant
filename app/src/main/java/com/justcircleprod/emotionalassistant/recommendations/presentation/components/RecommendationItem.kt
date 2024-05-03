package com.justcircleprod.emotionalassistant.recommendations.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.SubtitleTextColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White

@Composable
fun RecommendationItem(recommendationTitle: String, recommendationText: String) {
    Text(
        text = recommendationTitle,
        fontFamily = AlegreyaFontFamily,
        fontSize = 18.sp,
        color = White,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(Modifier.height(5.dp))

    Text(
        text = recommendationText,
        fontFamily = AlegreyaFontFamily,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        color = SubtitleTextColor,
        modifier = Modifier.fillMaxWidth()
    )
}