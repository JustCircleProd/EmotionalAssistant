package com.example.bd.core.presentation.navigationBar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bd.core.presentation.appNavigation.Screen
import com.example.db.R

sealed class NavigationBarItem(
    var route: String,
    val icon: ImageVector,
    @StringRes var title: Int
) {
    object Home : NavigationBarItem(Screen.HOME.name, Icons.Filled.Home, R.string.home)
}