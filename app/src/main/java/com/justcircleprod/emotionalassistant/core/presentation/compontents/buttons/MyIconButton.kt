package com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.presentation.theme.White

@Composable
fun MyIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconTintColor: Color = White
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(dimensionResource(id = R.dimen.icon_button_size))
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = iconTintColor,
            modifier = iconModifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
        )
    }
}