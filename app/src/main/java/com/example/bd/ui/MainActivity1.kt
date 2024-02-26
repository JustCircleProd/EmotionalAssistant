package com.example.bd.ui

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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bd.ui.theme.BdTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity1 : ComponentActivity() {

    private val viewModel: MainViewModel1 by viewModels()

    private var isLoading: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition { isLoading }

        setContent {
            BdTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val bottomBarVisibilityState = rememberSaveable { (mutableStateOf(true)) }

                bottomBarVisibilityState.value =  when (navBackStackEntry?.destination?.route) {
                    NavigationItem.Welcome.route, NavigationItem.Register.route, NavigationItem.EmotionRecognition.route -> {
                        false
                    }

                    NavigationItem.Home.route -> {
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
        animatedComposableForNavGraphBuilder(NavigationItem.EmotionRecognition.route) {
            EmotionRecognitionScreen(navController)
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

@Composable
fun BottomNavigationBar(
    navController: NavController,
    bottomBarVisibilityState: MutableState<Boolean>
) {
    val items = listOf(
        NavigationBarItem.Home
    )
    var selectedItem by remember { mutableIntStateOf(0) }
    var currentRoute by remember { mutableStateOf(NavigationItem.Home.route) }

    items.forEachIndexed { index, navigationItem ->
        if (navigationItem.route == currentRoute) {
            selectedItem = index
        }
    }

    if (bottomBarVisibilityState.value) {
        BottomAppBar {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    val title = stringResource(id = item.title)

                    NavigationBarItem(
                        alwaysShowLabel = true,
                        icon = { Icon(item.icon, contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedItem == index,
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

/*
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        item {
            EmotionalStateTable(viewModel = viewModel)
        }
        item {
            EmotionalStateTestWithQuestionsTable(viewModel)
        }
    }
}

*/
/*@Composable
fun UserTable(viewModel: MainViewModel = hiltViewModel()) {
    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = listOf())

    if (user.value.isNotEmpty()) {
        val userData = user.value[0]

        Column(Modifier.fillMaxWidth()) {
            Text(
                text = "User Table",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )

            Row(modifier = Modifier.background(Color.LightGray)) {
                TableCell(text = "name", weight = 0.4f)
                TableCell(text = "age", weight = 0.2f)
                TableCell(text = "gender", weight = 0.4f)
            }

            Row {
                TableCell(text = userData.name, weight = 0.4f)
                TableCell(text = userData.age.toString(), weight = 0.2f)
                TableCell(text = userData.gender, weight = 0.4f)
            }
        }
    }
}

@Composable
fun EmotionTable(viewModel: MainViewModel = hiltViewModel()) {
    val emotions = viewModel.emotions.collectAsStateWithLifecycle(initialValue = listOf())

    Column {
        Text(
            text = "Emotion Table",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )

        Row(modifier = Modifier.background(Color.LightGray)) {
            TableCell(text = "id", weight = 0.3f)
            TableCell(text = "name", weight = 0.7f)
        }

        for (emotion in emotions.value) {
            Row {
                TableCell(text = emotion.id.toString(), weight = 0.3f)
                TableCell(text = emotion.name, weight = 0.7f)
            }
        }
    }
}*//*


@Composable
fun EmotionalStateTable(viewModel: MainViewModel = hiltViewModel()) {
    val emotionalStates =
        viewModel.emotionalStates.collectAsStateWithLifecycle(initialValue = listOf())

    Column {
        Text(
            text = "Emotional State Table",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )

        Row(modifier = Modifier.background(Color.LightGray)) {
            TableCell(text = "id", weight = 0.3f)
            TableCell(text = "name", weight = 0.7f)
        }

        for (emotionalState in emotionalStates.value) {
            Row {
                TableCell(text = emotionalState.id.toString(), weight = 0.3f)
                TableCell(text = emotionalState.name, weight = 0.7f)
            }
        }
    }
}

@Composable
fun EmotionalStateTestWithQuestionsTable(viewModel: MainViewModel) {
    val emotionalStateTestsWithQuestions =
        viewModel.emotionalStateTestsWithQuestions.collectAsStateWithLifecycle(initialValue = listOf<EmotionalStateTestWithQuestions>())

    Column {
        Text(
            text = "Emotional State Test with Questions",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )

        for (emotionalStateTestWithQuestions in emotionalStateTestsWithQuestions.value) {
            Row(modifier = Modifier.background(Color.LightGray)) {
                TableCell(text = "id", weight = 0.1f)
                TableCell(text = "name", weight = 0.4f)
                TableCell(text = "goalScore", weight = 0.3f)
                TableCell(text = "emotional_state_id", weight = 0.2f)
            }

            with(emotionalStateTestWithQuestions.emotionalStateTest) {
                Row {
                    TableCell(text = id.toString(), weight = 0.1f)
                    TableCell(text = name, weight = 0.4f)
                    TableCell(text = goalScore.toString(), weight = 0.3f)
                    TableCell(text = emotionalStateId.toString(), weight = 0.2f)
                }
            }


            Row(modifier = Modifier.background(Color.LightGray)) {
                TableCell(text = "id", weight = 0.2f)
                TableCell(text = "question", weight = 0.4f)
                TableCell(text = "options", weight = 0.3f)
                TableCell(text = "points", weight = 0.3f)
            }

            for (question in emotionalStateTestWithQuestions.questions) {
                Row {
                    TableCell(text = question.id.toString(), weight = 0.2f)
                    TableCell(text = question.question, weight = 0.4f)
                    TableCell(text = question.optionList.options.toString(), weight = 0.3f)
                    TableCell(text = question.pointList.points.toString(), weight = 0.3f)
                }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}*/
