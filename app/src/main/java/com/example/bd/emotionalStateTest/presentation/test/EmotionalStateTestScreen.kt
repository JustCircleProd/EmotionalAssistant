package com.example.bd.emotionalStateTest.presentation.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.compontents.buttons.OptionButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.TonalButtonColor
import com.example.bd.core.presentation.theme.White
import com.example.db.R

@Composable
fun EmotionalStateTestScreen(navController: NavHostController) {
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
                QuestionNumber()

                Spacer(Modifier.height(12.dp))

                QuestionCard(question = "Вопрос\nВопрос\nВопрос")

                Spacer(Modifier.height(20.dp))

                OptionButton(
                    "Вариант ответа 1",
                    "Вариант ответа 1",
                    selected = { true },
                    onClick = {}
                )

                Spacer(Modifier.height(8.dp))

                OptionButton(
                    "Вариант ответа 2",
                    "Вариант ответа 2",
                    selected = { false },
                    onClick = {}
                )

                Spacer(Modifier.height(8.dp))

                OptionButton(
                    "Вариант ответа 3",
                    "Вариант ответа 3",
                    selected = { false },
                    onClick = {}
                )

                Spacer(Modifier.height(8.dp))

                OptionButton(
                    "Вариант ответа 4",
                    "Вариант ответа 4",
                    selected = { false },
                    onClick = {}
                )

                Spacer(Modifier.height(8.dp))

                OptionButton(
                    "Вариант ответа 5",
                    "Вариант ответа 5",
                    selected = { false },
                    onClick = {}
                )

                Spacer(Modifier.height(40.dp))

                MyButton(
                    text = "Следующий вопрос",
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun QuestionNumber() {
    Text(
        text = "Вопрос 1",
        fontFamily = AlegreyaFontFamily,
        fontSize = 17.sp,
        color = White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun QuestionCard(question: String) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = TonalButtonColor
        ),
        modifier = Modifier.clip(RoundedCornerShape(20.dp))
    ) {
        Text(
            text = question,
            fontFamily = AlegreyaFontFamily,
            fontSize = 21.sp,
            fontWeight = FontWeight.Medium,
            color = White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
        EmotionalStateTestScreen(navController = rememberNavController())
    }
}