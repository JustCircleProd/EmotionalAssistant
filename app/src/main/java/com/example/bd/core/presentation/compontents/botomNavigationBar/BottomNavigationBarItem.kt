package com.example.bd.core.presentation.compontents.botomNavigationBar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bd.core.presentation.compontents.appNavigation.Screen
import com.example.db.R

sealed class BottomNavigationBarItem(
    var route: String,
    val icon: ImageVector,
    @StringRes var title: Int
) {
    data object Home : BottomNavigationBarItem(Screen.HOME.name, Icons.Filled.Home, R.string.home)
    data object History :
        BottomNavigationBarItem(Screen.HISTORY.name, Icons.Filled.History, R.string.history)
}