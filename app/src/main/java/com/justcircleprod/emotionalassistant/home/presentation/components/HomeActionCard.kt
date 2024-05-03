package com.justcircleprod.emotionalassistant.home.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.justcircleprod.emotionalassistant.core.presentation.theme.ActionCardContainerColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.HomeActionCardButtonContainerColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.R

@Composable
fun HomeActionCard(
    title: String,
    subtitle: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    @DrawableRes cardImageResId: Int,
    cardImageContentDescription: String?
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.action_card_rounded_corner_size)),
        colors = CardDefaults.cardColors().copy(containerColor = ActionCardContainerColor),
        modifier = Modifier.fillMaxWidth()
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
                    text = title,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = subtitle,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onButtonClick,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.action_card_button_rounded_corner_size)),
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = HomeActionCardButtonContainerColor)
                ) {
                    Text(
                        text = buttonText,
                        fontSize = 15.sp,
                        fontFamily = AlegreyaFontFamily,
                        color = White,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(id = R.dimen.action_card_button_content_horizontal_text_padding),
                            vertical = dimensionResource(id = R.dimen.action_card_button_content_vertical_text_padding)
                        )
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