package com.example.bd.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bd.core.presentation.compontents.appNavigation.NavigationItem
import com.example.bd.core.presentation.compontents.botomNavigationBar.BottomNavigationBar
import com.example.bd.core.presentation.compontents.sharedViewModel
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.MyRippleTheme
import com.example.bd.emotionRecognition.presentation.byPhoto.EmotionRecognitionByPhotoScreen
import com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel.EmotionRecognitionViewModel
import com.example.bd.emotionRecognition.presentation.methodSelection.EmotionRecognitionMethodSelectionScreen
import com.example.bd.emotionRecognition.presentation.selectionFromList.EmotionSelectionFromListScreen
import com.example.bd.history.presentation.HistoryScreen
import com.example.bd.home.presentation.HomeScreen
import com.example.bd.registration.presentation.RegisterScreen
import com.example.bd.welcome.presentation.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var isLoading: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition { isLoading }

        setContent {
            BdTheme {
                CompositionLocalProvider(LocalRippleTheme provides MyRippleTheme) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val bottomBarVisibilityState = rememberSaveable { (mutableStateOf(true)) }

                    bottomBarVisibilityState.value = when (navBackStackEntry?.destination?.route) {
                        NavigationItem.Welcome.route, NavigationItem.Register.route, NavigationItem.EmotionRecognitionMethodSelection.route -> {
                            false
                        }

                        NavigationItem.Home.route, NavigationItem.History.route -> {
                            true
                        }

                        else -> {
                            false
                        }
                    }

                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                navController = navController,
                                bottomBarVisibilityState = bottomBarVisibilityState
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            val isUserTableNotEmpty by viewModel.isUserTableNotEmpty.collectAsStateWithLifecycle(
                                initialValue = null
                            )
                            var startDestination by remember { mutableStateOf(NavigationItem.Welcome.route) }

                            LaunchedEffect(key1 = isUserTableNotEmpty) {
                                if (isUserTableNotEmpty != null) {
                                    startDestination = if (isUserTableNotEmpty!!) {
                                        NavigationItem.Home.route
                                    } else {
                                        NavigationItem.Welcome.route
                                    }

                                    launch {
                                        delay(300)
                                        isLoading = false
                                    }
                                }
                            }

                            if (isUserTableNotEmpty != null) {
                                AppNavHost(
                                    navController = navController,
                                    startDestination = startDestination
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        animatedComposableForNavGraphBuilder(NavigationItem.Welcome.route) {
            WelcomeScreen(navController)
        }
        animatedComposableForNavGraphBuilder(NavigationItem.Register.route) {
            RegisterScreen(navController)
        }
        animatedComposableForNavGraphBuilder(NavigationItem.Home.route) {
            HomeScreen(navController)
        }
        animatedComposableForNavGraphBuilder(NavigationItem.EmotionRecognitionMethodSelection.route) {
            EmotionRecognitionMethodSelectionScreen(navController)
        }
        animatedComposableForNavGraphBuilder(NavigationItem.EmotionRecognitionByPhoto.route) {
            val viewModel = it.sharedViewModel<EmotionRecognitionViewModel>(
                navController = navController,
                viewModelOwnerRoute = NavigationItem.EmotionRecognitionMethodSelection.route
            )
            EmotionRecognitionByPhotoScreen(navController, viewModel)
        }
        animatedComposableForNavGraphBuilder(NavigationItem.EmotionSelectionFromList.route) {
            EmotionSelectionFromListScreen(navController)
        }
        animatedComposableForNavGraphBuilder(NavigationItem.History.route) {
            HistoryScreen(navController)
        }
    }
}

fun NavGraphBuilder.animatedComposableForNavGraphBuilder(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable(
        route,
        arguments = arguments,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(200)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(200)
            )

        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        },
        content = content
    )
}