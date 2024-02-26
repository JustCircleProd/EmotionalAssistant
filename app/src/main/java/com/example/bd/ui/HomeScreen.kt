package com.example.bd.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bd.ui.theme.ActionCardButtonContainerColor
import com.example.bd.ui.theme.ActionCardContainerColor
import com.example.bd.ui.theme.AlegreyaFontFamily
import com.example.bd.ui.theme.BdTheme
import com.example.bd.ui.theme.SubtitleTextColor
import com.example.bd.ui.theme.White
import com.example.db.R


@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = null)

    Surface {


        if (user.value != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(id = R.dimen.main_screens_space))
            ) {
                Text(
                    text = stringResource(R.string.main_screen_title, user.value!!.name),
                    fontWeight = FontWeight.Medium,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 27.sp,
                    color = White,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.main_screen_subtitle),
                    fontSize = 19.sp,
                    fontFamily = AlegreyaFontFamily,
                    color = SubtitleTextColor,
                )

                Spacer(modifier = Modifier.height(30.dp))

                ActionCard(
                    title = "Сегодняшняя эмоция",
                    subtitle = "Определите вашу эмоцию",
                    buttonText = "Определить",
                    onButtonClick = {
                        navController.navigate(NavigationItem.EmotionRecognition.route)
                    },
                    cardImageResId = R.drawable.action_card_demo_image,
                    cardImageContentDescription = null
                )
            }
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    subtitle: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    @DrawableRes cardImageResId: Int,
    cardImageContentDescription: String?
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors().copy(containerColor = ActionCardContainerColor),
        modifier = Modifier.fillMaxWidth()

    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 22.dp,
                    vertical = 16.dp
                )
        ) {
            Column(
                modifier = Modifier.weight(2.5f)
            ) {
                Text(
                    text = title,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 19.sp
                )

                Text(
                    text = subtitle,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onButtonClick,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corners)),
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = ActionCardButtonContainerColor)
                ) {
                    Text(
                        text = buttonText,
                        fontSize = 15.sp,
                        fontFamily = AlegreyaFontFamily,
                        color = White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            Image(
                painter = painterResource(id = cardImageResId),
                contentDescription = cardImageContentDescription,
                modifier = Modifier.weight(1f)
            )
        }

    }
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
        HomeScreen(navController = rememberNavController())
    }
}