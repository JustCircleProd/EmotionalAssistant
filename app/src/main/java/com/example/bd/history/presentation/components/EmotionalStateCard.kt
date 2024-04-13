package com.example.bd.history.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bd.core.presentation.compontents.buttons.MyIconButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BottomSheetCardContainerColor
import com.example.bd.core.presentation.theme.MyRippleTheme
import com.example.bd.core.presentation.theme.Red
import com.example.bd.core.presentation.theme.White
import com.example.db.R

@Composable
fun EmotionalStateCard(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = BottomSheetCardContainerColor
        ),
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size)))
            .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.calendar_day_card_padding))
        ) {
            Text(
                text = stringResource(id = R.string.emotional_state),
                fontFamily = AlegreyaFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = White
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CompositionLocalProvider(LocalRippleTheme provides MyRippleTheme(color = Red)) {
                    MyIconButton(
                        imageVector = Icons.Rounded.Delete,
                        iconTintColor = MaterialTheme.colorScheme.error,
                        onClick = {}
                    )
                }

                MyIconButton(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    onClick = {},
                    iconModifier = Modifier.rotate(180f)
                )
            }
        }
    }
}