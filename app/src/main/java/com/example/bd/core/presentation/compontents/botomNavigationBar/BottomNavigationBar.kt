package com.example.bd.core.presentation.compontents.botomNavigationBar

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bd.core.presentation.compontents.NavigationItem
import com.example.bd.core.presentation.theme.NavigationBarItemSelectedColor
import com.example.bd.core.presentation.theme.NavigationBarItemUnselectedColor
import com.example.bd.core.presentation.theme.White


@Composable
fun BottomNavigationBar(
    navController: NavController,
    bottomBarVisibilityState: MutableState<Boolean>
) {
    val items = listOf(
        BottomNavigationBarItem.Home,
        BottomNavigationBarItem.History
    )
    var selectedItem by remember { mutableIntStateOf(0) }
    var currentRoute by remember { mutableStateOf(NavigationItem.Home.route) }

    items.forEachIndexed { index, navigationItem ->
        if (navigationItem.route == currentRoute) {
            selectedItem = index
        }
    }

    if (bottomBarVisibilityState.value) {
        BottomAppBar(
            tonalElevation = 0.dp,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.background
        ) {
            NavigationBar(
                tonalElevation = 0.dp,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.background
            ) {
                items.forEachIndexed { index, item ->
                    val title = stringResource(id = item.title)

                    NavigationBarItem(
                        alwaysShowLabel = true,
                        icon = { Icon(item.icon, contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedItem == index,
                        colors = NavigationBarItemDefaults.colors().copy(
                            selectedIconColor = White,
                            selectedTextColor = White,
                            selectedIndicatorColor = NavigationBarItemSelectedColor,
                            unselectedIconColor = NavigationBarItemUnselectedColor,
                            unselectedTextColor = NavigationBarItemUnselectedColor
                        ),
                        onClick = {
                            selectedItem = index
                            currentRoute = item.route
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}