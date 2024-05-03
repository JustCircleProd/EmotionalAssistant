package com.justcircleprod.emotionalassistant.core.presentation.compontents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
    viewModelOwnerRoute: String
): T {
    navController.previousBackStackEntry?.destination?.route ?: return hiltViewModel<T>()

    val parentEntry = remember(this) {
        navController.getBackStackEntry(viewModelOwnerRoute)
    }

    return viewModel(parentEntry)
}