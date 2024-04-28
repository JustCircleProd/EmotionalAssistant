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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bd.core.domain.models.EmotionalStateName
import com.example.bd.core.presentation.compontents.NavigationItem
import com.example.bd.core.presentation.compontents.botomNavigationBar.BottomNavigationBar
import com.example.bd.core.presentation.compontents.sharedViewModel
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.MyRippleTheme
import com.example.bd.emotionAdditionalInfo.presentation.EmotionAdditionalInfoScreen
import com.example.bd.emotionDetail.presentation.EmotionDetailScreen
import com.example.bd.emotionRecognition.presentation.byPhoto.EmotionRecognitionByPhotoScreen
import com.example.bd.emotionRecognition.presentation.methodSelection.EmotionRecognitionMethodSelectionScreen
import com.example.bd.emotionRecognition.presentation.selectionFromList.EmotionSelectionFromListScreen
import com.example.bd.emotionRecognition.presentation.viewModel.EmotionRecognitionViewModel
import com.example.bd.emotionalStateTest.presentation.test.EmotionalStateTestScreen
import com.example.bd.emotionalStateTest.presentation.testResult.EmotionalStateTestResultScreen
import com.example.bd.history.presentation.HistoryScreen
import com.example.bd.home.presentation.HomeScreen
import com.example.bd.recommendations.presentation.emotion.EmotionRecommendationScreen
import com.example.bd.recommendations.presentation.emotionalState.EmotionalStateRecommendationScreen
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
                CompositionLocalProvider(
                    LocalRippleTheme provides MyRippleTheme(color = MaterialTheme.colorScheme.primary)
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
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
                                        delay(500)
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
        animatedComposable(NavigationItem.Welcome.route) {
            WelcomeScreen(navController)
        }

        animatedComposable(NavigationItem.Register.route) {
            RegisterScreen(navController)
        }

        animatedComposable(NavigationItem.Home.route) {
            HomeScreen(navController)
        }

        animatedComposable(NavigationItem.History.route) {
            HistoryScreen(navController)
        }

        animatedComposable(
            NavigationItem.EmotionRecognitionMethodSelection.route,
            arguments = listOf(
                navArgument(NavigationItem.EmotionRecognitionMethodSelection.RETURN_ROUTE_ARGUMENT_NAME) {
                    type = NavType.StringType
                },
                navArgument(NavigationItem.EmotionRecognitionMethodSelection.DATE_ARGUMENT_NAME) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(NavigationItem.EmotionRecognitionMethodSelection.EMOTION_ID_TO_UPDATE_ARGUMENT_NAME) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            val returnRoute =
                it.arguments?.getString(NavigationItem.EmotionRecognitionMethodSelection.RETURN_ROUTE_ARGUMENT_NAME)

            EmotionRecognitionMethodSelectionScreen(navController, returnRoute)
        }

        animatedComposable(
            NavigationItem.EmotionRecognitionByPhoto.route,
            arguments = listOf(
                navArgument(NavigationItem.EmotionRecognitionByPhoto.RETURN_ROUTE_ARGUMENT_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = it.sharedViewModel<EmotionRecognitionViewModel>(
                navController = navController,
                viewModelOwnerRoute = NavigationItem.EmotionRecognitionMethodSelection.route
            )

            val returnRoute =
                it.arguments?.getString(NavigationItem.EmotionRecognitionByPhoto.RETURN_ROUTE_ARGUMENT_NAME)

            EmotionRecognitionByPhotoScreen(navController, viewModel, returnRoute)
        }

        animatedComposable(
            NavigationItem.EmotionSelectionFromList.route,
            arguments = listOf(
                navArgument(NavigationItem.EmotionSelectionFromList.RETURN_ROUTE_ARGUMENT_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = it.sharedViewModel<EmotionRecognitionViewModel>(
                navController = navController,
                viewModelOwnerRoute = NavigationItem.EmotionRecognitionMethodSelection.route
            )

            val returnRoute =
                it.arguments?.getString(NavigationItem.EmotionSelectionFromList.RETURN_ROUTE_ARGUMENT_NAME)

            EmotionSelectionFromListScreen(navController, viewModel, returnRoute)
        }

        animatedComposable(
            NavigationItem.EmotionAdditionalInfo.route,
            arguments = listOf(
                navArgument(NavigationItem.EmotionAdditionalInfo.RETURN_ROUTE_ARGUMENT_NAME) {
                    type = NavType.StringType
                },
                navArgument(NavigationItem.EmotionAdditionalInfo.EMOTION_ID_ARGUMENT_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            val returnRoute =
                it.arguments?.getString(NavigationItem.EmotionAdditionalInfo.RETURN_ROUTE_ARGUMENT_NAME)

            EmotionAdditionalInfoScreen(navController, returnRoute)
        }

        animatedComposable(
            NavigationItem.EmotionRecommendation.route,
            arguments = listOf(
                navArgument(NavigationItem.EmotionRecommendation.EMOTION_ID_ARGUMENT_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            EmotionRecommendationScreen(navController)
        }

        animatedComposable(
            NavigationItem.EmotionDetail.route,
            arguments = listOf(
                navArgument(NavigationItem.EmotionDetail.IN_EDIT_MODE_ARGUMENT_NAME) {
                    type = NavType.BoolType
                },
                navArgument(NavigationItem.EmotionDetail.EMOTION_ID_ARGUMENT_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            val inEditMode =
                it.arguments?.getBoolean(NavigationItem.EmotionDetail.IN_EDIT_MODE_ARGUMENT_NAME)
                    ?: false

            EmotionDetailScreen(navController, inEditMode)
        }

        animatedComposable(
            NavigationItem.EmotionalStateTest.route,
            arguments = listOf(
                navArgument(NavigationItem.EmotionalStateTest.RETURN_ROUTE_ARGUMENT_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            val returnRoute =
                it.arguments?.getString(NavigationItem.EmotionalStateTest.RETURN_ROUTE_ARGUMENT_NAME)

            EmotionalStateTestScreen(navController, returnRoute)
        }

        animatedComposable(
            NavigationItem.EmotionalStateTestResult.route,
            arguments = listOf(
                navArgument(NavigationItem.EmotionalStateTestResult.DATE_ARGUMENT_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            EmotionalStateTestResultScreen(navController)
        }

        animatedComposable(
            NavigationItem.EmotionalStateRecommendation.route,
            arguments = listOf(
                navArgument(NavigationItem.EmotionalStateRecommendation.EMOTIONAL_STATE_NAME_ARGUMENT_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            val emotionalStateName =
                it.arguments?.getString(NavigationItem.EmotionalStateRecommendation.EMOTIONAL_STATE_NAME_ARGUMENT_NAME)
                    ?.let { emotionalStateNameString ->
                        EmotionalStateName.valueOf(emotionalStateNameString)
                    }

            EmotionalStateRecommendationScreen(navController, emotionalStateName)
        }
    }
}

private fun NavGraphBuilder.animatedComposable(
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