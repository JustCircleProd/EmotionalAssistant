package com.example.bd.history.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BottomSheetCardContainerColor
import com.example.bd.core.presentation.theme.White
import com.example.db.R

@Composable
fun AddEmotionCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = BottomSheetCardContainerColor
        ),
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size)))
            .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
            .clickable { onClick() }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.calendar_day_card_padding))
            ) {
                Text(
                    text = stringResource(R.string.add_emotion),
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = White
                )

                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                )
            }
        }
    }
}