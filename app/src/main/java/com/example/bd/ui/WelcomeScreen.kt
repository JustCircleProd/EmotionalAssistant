package com.example.bd.ui

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bd.ui.common.MyButton
import com.example.bd.ui.theme.AlegreyaFontFamily
import com.example.bd.ui.theme.BdTheme
import com.example.bd.ui.theme.White
import com.example.db.R

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Surface {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.start_screens_background),
                    contentScale = ContentScale.Crop
                )
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.welcome_screens_space))
        ) {
            val (welcomeColumn, startButton) = createRefs()

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.constrainAs(welcomeColumn) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(startButton.top)
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_emotion),
                    contentDescription = stringResource(id = R.string.icon_emotion_content_description),
                    modifier = Modifier.size(
                        dimensionResource(id = R.dimen.start_screen_icon_size),
                        dimensionResource(id = R.dimen.start_screen_icon_size),
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(id = R.string.welcome_title),
                    fontWeight = FontWeight.Bold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 27.sp,
                    color = White,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(id = R.string.welcome_text),
                    textAlign = TextAlign.Center,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 19.sp,
                    color = White,
                )
            }


            Spacer(modifier = Modifier.height(12.dp))

            MyButton(
                text = stringResource(id = R.string.start),
                onClick = {
                    navController.navigate(NavigationItem.Register.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(startButton) {
                        start.linkTo(parent.start)
                        top.linkTo(welcomeColumn.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
        WelcomeScreen(rememberNavController())
    }
}

