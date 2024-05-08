package com.justcircleprod.emotionalassistant.home.presentation

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.SubtitleTextColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.home.presentation.components.HomeActionCard


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val user by viewModel.user.collectAsStateWithLifecycle(initialValue = null)

    Surface {
        if (user == null) return@Surface

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                .animateContentSize()
        ) {
            Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

            Text(
                text = stringResource(R.string.main_screen_title, user!!.name),
                fontWeight = FontWeight.Medium,
                fontFamily = AlegreyaFontFamily,
                fontSize = 26.sp,
                color = White,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(R.string.main_screen_subtitle),
                fontSize = 20.sp,
                fontFamily = AlegreyaFontFamily,
                color = SubtitleTextColor,
            )

            Spacer(modifier = Modifier.height(20.dp))

            HomeActionCard(
                title = stringResource(R.string.emotion),
                subtitle = stringResource(R.string.recognize_your_emotion),
                buttonText = stringResource(R.string.recognize),
                onButtonClick = {
                    navController.navigate(
                        NavigationItem.EmotionRecognitionMethodSelection.getRouteWithArguments(
                            NavigationItem.Home.route
                        )
                    )
                },
                cardImageResId = R.drawable.action_card_emotion,
                cardImageContentDescription = null
            )

            Spacer(Modifier.height(20.dp))

            HomeActionCard(
                title = stringResource(R.string.emotional_state),
                subtitle = stringResource(R.string.pass_test),
                buttonText = stringResource(R.string.pass),
                onButtonClick = {
                    navController.navigate(
                        NavigationItem.EmotionalStateTest.getRouteWithArguments(
                            NavigationItem.Home.route
                        )
                    )
                },
                cardImageResId = R.drawable.action_card_emotional_state,
                cardImageContentDescription = null
            )

            Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
        }
    }
}


@Preview
@Composable
private fun Preview() {
    BdTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

                Text(
                    text = stringResource(R.string.main_screen_title, "Имя"),
                    fontWeight = FontWeight.Medium,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    color = White,
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = stringResource(R.string.main_screen_subtitle),
                    fontSize = 20.sp,
                    fontFamily = AlegreyaFontFamily,
                    color = SubtitleTextColor,
                )

                Spacer(modifier = Modifier.height(20.dp))

                HomeActionCard(
                    title = stringResource(R.string.emotion),
                    subtitle = stringResource(R.string.recognize_your_emotion),
                    buttonText = stringResource(R.string.recognize),
                    onButtonClick = {

                    },
                    cardImageResId = R.drawable.action_card_emotion,
                    cardImageContentDescription = null
                )

                Spacer(Modifier.height(20.dp))

                HomeActionCard(
                    title = stringResource(R.string.emotional_state),
                    subtitle = stringResource(R.string.pass_test),
                    buttonText = stringResource(R.string.pass),
                    onButtonClick = {

                    },
                    cardImageResId = R.drawable.action_card_emotional_state,
                    cardImageContentDescription = null
                )

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPrototype() {
    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                .animateContentSize()
        ) {
            Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

            Text(
                text = "Lorem ipsum dolor sit.",
                fontWeight = FontWeight.Medium,
                fontFamily = AlegreyaFontFamily,
                fontSize = 26.sp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Lorem ipsum dolor sit.",
                fontSize = 20.sp,
                fontFamily = AlegreyaFontFamily,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.action_card_rounded_corner_size)),
                colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Color.Black,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.action_card_rounded_corner_size))
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.action_card_horizontal_padding),
                            vertical = dimensionResource(id = R.dimen.action_card_vertical_padding)
                        )
                ) {
                    Column(
                        modifier = Modifier.weight(2.5f)
                    ) {
                        Text(
                            text = "Lorem ipsum",
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "Lorem ipsum dolor sit",
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { },
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.action_card_button_rounded_corner_size)),
                            colors = ButtonDefaults.buttonColors()
                                .copy(containerColor = Color.White),
                            modifier = Modifier.border(
                                1.dp,
                                Color.Black,
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner_size))
                            )
                        ) {
                            Text(
                                text = "Lorem",
                                fontSize = 15.sp,
                                fontFamily = AlegreyaFontFamily,
                                color = Color.Black,
                                modifier = Modifier.padding(
                                    horizontal = dimensionResource(id = R.dimen.action_card_button_content_horizontal_text_padding),
                                    vertical = dimensionResource(id = R.dimen.action_card_button_content_vertical_text_padding)
                                )
                            )
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    Image(
                        painter = painterResource(id = R.drawable.prototype_image_placeholder),
                        contentDescription = null,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.action_card_rounded_corner_size)),
                colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Color.Black,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.action_card_rounded_corner_size))
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.action_card_horizontal_padding),
                            vertical = dimensionResource(id = R.dimen.action_card_vertical_padding)
                        )
                ) {
                    Column(
                        modifier = Modifier.weight(2.5f)
                    ) {
                        Text(
                            text = "Lorem ipsum",
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "Lorem ipsum dolor sit",
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { },
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.action_card_button_rounded_corner_size)),
                            colors = ButtonDefaults.buttonColors()
                                .copy(containerColor = Color.White),
                            modifier = Modifier.border(
                                1.dp,
                                Color.Black,
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner_size))
                            )
                        ) {
                            Text(
                                text = "Lorem",
                                fontSize = 15.sp,
                                fontFamily = AlegreyaFontFamily,
                                color = Color.Black,
                                modifier = Modifier.padding(
                                    horizontal = dimensionResource(id = R.dimen.action_card_button_content_horizontal_text_padding),
                                    vertical = dimensionResource(id = R.dimen.action_card_button_content_vertical_text_padding)
                                )
                            )
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    Image(
                        painter = painterResource(id = R.drawable.prototype_image_placeholder),
                        contentDescription = null,
                        modifier = Modifier.weight(1f)
                    )
                }

            }

            Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
        }
    }
}