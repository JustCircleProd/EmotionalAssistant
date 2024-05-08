package com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.TonalButtonColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White

@Composable
fun OptionButton(
    value: Any,
    text: String,
    selected: Boolean,
    onClick: (Any) -> Unit,
    isError: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.option_button_rounded_corner_size)),
        colors = CardDefaults.cardColors().copy(containerColor = TonalButtonColor),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (selected || isError) 2.dp else 0.dp,
                color = when {
                    selected -> MaterialTheme.colorScheme.primary
                    isError -> MaterialTheme.colorScheme.error
                    else -> Color.Transparent
                },
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.option_button_rounded_corner_size))
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.option_button_rounded_corner_size)))
            .clickable { onClick(value) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.button_content_vertical_text_padding),
                horizontal = dimensionResource(id = R.dimen.button_content_horizontal_text_padding)
            )
        ) {
            RadioButton(
                selected = selected,
                colors = RadioButtonDefaults.colors(unselectedColor = White),
                onClick = { onClick(value) }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = text,
                fontFamily = AlegreyaFontFamily,
                fontSize = 18.sp,
                color = White
            )
        }
    }
}