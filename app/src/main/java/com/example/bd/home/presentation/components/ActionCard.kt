package com.example.bd.home.presentation.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bd.ui.theme.ActionCardButtonContainerColor
import com.example.bd.ui.theme.ActionCardContainerColor
import com.example.bd.ui.theme.AlegreyaFontFamily
import com.example.bd.ui.theme.White
import com.example.db.R

@Composable
fun ActionCard(
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
                    fontSize = 18.sp
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