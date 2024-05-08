package com.justcircleprod.emotionalassistant.welcome.presentation

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
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
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyButton
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.White

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Surface {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.welcome_background),
                    contentScale = ContentScale.Crop
                )
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensionResource(id = R.dimen.registration_screens_space))
                .animateContentSize()
        ) {
            val (welcomeColumn, startButton) = createRefs()

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.constrainAs(welcomeColumn) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(startButton.top)
                }
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.registration_screens_space)))

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

            Column(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(startButton) {
                        start.linkTo(parent.start)
                        top.linkTo(welcomeColumn.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                MyButton(
                    text = stringResource(id = R.string.start),
                    onClick = {
                        navController.navigate(NavigationItem.Registration.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.registration_screens_space)))
            }
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

@Preview
@Composable
private fun PreviewPrototype() {
    Surface(color = Color.White) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensionResource(id = R.dimen.registration_screens_space))
                .animateContentSize()
        ) {
            val (welcomeColumn, startButton) = createRefs()

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.constrainAs(welcomeColumn) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(startButton.top)
                }
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.registration_screens_space)))

                Image(
                    painter = painterResource(id = R.drawable.prototype_image_placeholder),
                    contentDescription = stringResource(id = R.string.icon_emotion_content_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(
                            dimensionResource(id = R.dimen.start_screen_icon_size),
                            dimensionResource(id = R.dimen.start_screen_icon_size),
                        )
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(id = R.string.welcome_title),
                    fontWeight = FontWeight.Bold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 27.sp,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer eu.",
                    textAlign = TextAlign.Center,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 19.sp,
                    color = Color.Black,
                )
            }


            Spacer(modifier = Modifier.height(12.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(startButton) {
                        start.linkTo(parent.start)
                        top.linkTo(welcomeColumn.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
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
                            text = "Начать",
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 19.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(Modifier.height(dimensionResource(id = R.dimen.registration_screens_space)))
            }
        }
    }
}

