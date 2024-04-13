package com.example.bd.core.presentation.compontents.botomNavigationBar

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bd.core.presentation.compontents.NavigationItem
import com.example.bd.core.presentation.theme.NavigationBarItemSelectedColor
import com.example.bd.core.presentation.theme.NavigationBarItemUnselectedColor
import com.example.bd.core.presentation.theme.White


@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        BottomNavigationBarItem.Home,
        BottomNavigationBarItem.History
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var bottomBarVisible by remember { mutableStateOf(true) }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        bottomBarVisible = when (destination.route) {
            NavigationItem.Home.route, NavigationItem.History.route -> {
                true
            }

            else -> {
                false
            }
        }
    }

    if (bottomBarVisible) {
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
                items.forEachIndexed { _, item ->
                    val title = stringResource(id = item.title)

                    NavigationBarItem(
                        alwaysShowLabel = true,
                        icon = { Icon(item.icon, contentDescription = title) },
                        label = { Text(title) },
                        selected = currentRoute == item.route,
                        colors = NavigationBarItemDefaults.colors().copy(
                            selectedIconColor = White,
                            selectedTextColor = White,
                            selectedIndicatorColor = NavigationBarItemSelectedColor,
                            unselectedIconColor = NavigationBarItemUnselectedColor,
                            unselectedTextColor = NavigationBarItemUnselectedColor
                        ),
                        onClick = {
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