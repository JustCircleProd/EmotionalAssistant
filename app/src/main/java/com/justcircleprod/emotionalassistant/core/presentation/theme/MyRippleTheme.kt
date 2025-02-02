package com.justcircleprod.emotionalassistant.core.presentation.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class MyRippleTheme(private val color: Color) : RippleTheme {
    // Here you should return the ripple color you want
    // and not use the defaultRippleColor extension on RippleTheme.
    // Using that will override the ripple color set in DarkMode
    // or when you set light parameter to false
    @Composable
    override fun defaultColor(): Color = color

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(
        focusedAlpha = 0.4f,
        draggedAlpha = 0.4f,
        hoveredAlpha = 0.4f,
        pressedAlpha = 0.4f
    )
}