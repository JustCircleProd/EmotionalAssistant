package com.example.bd.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bd.ui.theme.AlegreyaFontFamily
import com.example.bd.ui.theme.ButtonColor
import com.example.bd.ui.theme.White
import com.example.db.R

@Composable
fun MyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = ButtonColor,
    fontSize: TextUnit = 20.sp,
    @DrawableRes iconResId: Int? = null,
    iconContentDescription: String? = null,
) {
    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corners)),
        colors = ButtonDefaults.buttonColors().copy(containerColor = containerColor),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.button_content_vertical_text_padding),
                horizontal = dimensionResource(id = R.dimen.button_content_horizontal_text_padding)
            )
        ) {
            if (iconResId != null) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = iconContentDescription
                )

                Spacer(modifier = Modifier.width(6.dp))
            }

            Text(
                text = text,
                fontFamily = AlegreyaFontFamily,
                fontSize = fontSize,
                color = White
            )
        }
    }
}