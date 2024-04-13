package com.example.bd.emotionRecommendation.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.SubtitleTextColor
import com.example.bd.core.presentation.theme.White
import com.example.db.R

@Composable
fun EmotionRecommendationScreen(navController: NavController) {
    Surface {
        Column {
            BackButton(
                onClick = { },
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.toolbar_padding),
                    start = dimensionResource(id = R.dimen.toolbar_padding)
                )
            )

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
                        .size(
                            225.dp,
                            300.dp
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Эмоция",
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 24.sp,
                    color = White,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Грусть — отрицательно окрашенная эмоция. Возникает в случае значительной неудовлетворённости человека в каких-либо аспектах его жизни. Часто возникает после утраты.",
                    fontStyle = FontStyle.Italic,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = SubtitleTextColor,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Рекомендации",
                    fontFamily = AlegreyaFontFamily,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontSize = 19.sp,
                    color = White,
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Совет № 1. Примите свою грусть.\nСовет № 2. Ограничьте социальные сети.\nСовет № 3. Не перегружайте себя.\n...",
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 17.sp,
                    color = White,
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
            Column {
                BackButton(
                    onClick = { },
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.toolbar_padding),
                        start = dimensionResource(id = R.dimen.toolbar_padding)
                    )
                )

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
                            .size(
                                225.dp,
                                300.dp
                            )
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.emotion_image_rounded_corner_size)))
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Эмоция",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 24.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Грусть — отрицательно окрашенная эмоция. Возникает в случае значительной неудовлетворённости человека в каких-либо аспектах его жизни. Часто возникает после утраты.",
                        fontStyle = FontStyle.Italic,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                        color = SubtitleTextColor,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "Рекомендации",
                        fontFamily = AlegreyaFontFamily,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 19.sp,
                        color = White,
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = "Совет № 1. Примите свою грусть.\nСовет № 2. Ограничьте социальные сети.\nСовет № 3. Не перегружайте себя.\n...",
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 17.sp,
                        color = White,
                    )
                }
            }
        }
    }
}