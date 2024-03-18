package com.example.bd.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bd.core.presentation.compontents.appNavigation.NavigationItem
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.SubtitleTextColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.home.presentation.components.HomeActionCard
import com.example.db.R


@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val user by viewModel.user.collectAsStateWithLifecycle(initialValue = null)

    Surface {
        if (user == null) return@Surface

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.main_screens_space))
        ) {
            Text(
                text = stringResource(R.string.main_screen_title, user!!.name),
                fontWeight = FontWeight.Medium,
                fontFamily = AlegreyaFontFamily,
                fontSize = 26.sp,
                color = White,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(R.string.main_screen_subtitle),
                fontSize = 20.sp,
                fontFamily = AlegreyaFontFamily,
                color = SubtitleTextColor,
            )

            Spacer(modifier = Modifier.height(20.dp))

            HomeActionCard(
                title = stringResource(R.string.todays_emotion),
                subtitle = stringResource(R.string.recognize_your_emotion),
                buttonText = stringResource(R.string.recognize),
                onButtonClick = {
                    navController.navigate(NavigationItem.EmotionRecognitionMethodSelection.route)
                },
                cardImageResId = R.drawable.action_card_demo_image,
                cardImageContentDescription = null
            )
        }
    }
}


@Preview
@Composable
private fun Preview() {
    BdTheme {
        HomeScreen(navController = rememberNavController())
    }
}