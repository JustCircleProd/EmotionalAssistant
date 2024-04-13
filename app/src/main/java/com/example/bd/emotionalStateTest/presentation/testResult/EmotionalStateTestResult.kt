package com.example.bd.emotionalStateTest.presentation.testResult

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.SubtitleTextColor
import com.example.bd.core.presentation.theme.White
import com.example.db.R

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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(dimensionResource(id = R.dimen.main_screens_space))
                ) {
                    Text(
                        text = "Вот, что нам удалось обнаружить",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 24.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(30.dp))

                    Text(
                        text = "Депрессия",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 21.sp,

                        )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = "Вы набрали более 11 баллов, что может указывать на наличие депрессии.",
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 17.sp,
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(15.dp))

                    Image(
                        painter = painterResource(id = R.drawable.depression),
                        contentDescription = null,
                        modifier = Modifier.height(300.dp)
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "В случае депрессии человек обычно страдает от сниженного настроения, потери интереса и жизнерадостности, уменьшения энергии, что в свою очередь приводит к повышенной усталости и снижению активности.",
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 17.sp,
                        color = SubtitleTextColor,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(Modifier.height(20.dp))

                    MyButton(
                        text = "Рекомендации",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}