package com.example.bd.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.db.R

sealed class NavigationBarItem(var route: String, val icon: ImageVector, @StringRes var title: Int) {
    object Home : NavigationBarItem(Screen.HOME.name, Icons.Filled.Home, R.string.home)
}