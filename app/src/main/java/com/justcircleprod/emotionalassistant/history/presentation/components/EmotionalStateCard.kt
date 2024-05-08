package com.justcircleprod.emotionalassistant.history.presentation.components

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateName
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyIconButton
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BottomSheetCardContainerColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.MyRippleTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.Red
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.core.presentation.util.getEmotionalStateNameString

@Composable
fun EmotionalStateCard(
    emotionalStateName: EmotionalStateName,
    onDeleteButtonClick: () -> Unit,
    onRecommendationButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = BottomSheetCardContainerColor
        ),
        onClick = { onRecommendationButtonClick() },
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.calendar_day_card_rounded_corner_size)))
            .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .height(dimensionResource(id = R.dimen.calendar_day_emotion_card_height))
                .padding(dimensionResource(id = R.dimen.calendar_day_card_padding))
        ) {
            Text(
                text = getEmotionalStateNameString(
                    context,
                    emotionalStateName
                ),
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
                        onClick = onDeleteButtonClick
                    )
                }

                MyIconButton(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    onClick = onRecommendationButtonClick,
                    iconModifier = Modifier.rotate(180f)
                )
            }
        }
    }
}