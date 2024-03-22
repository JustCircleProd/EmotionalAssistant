package com.example.bd.emotionRecognition.presentation.selectionFromList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.bd.core.domain.models.EmotionName
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.compontents.buttons.OptionButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.White
import com.example.bd.core.utils.getEmotionNameString
import com.example.db.R

@Composable
fun EmotionSelectionFromListScreen(
    navController: NavHostController,
    viewModel: EmotionSelectionFromListViewModel = hiltViewModel()
) {
    Surface {
        Column {
            BackButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.back_button_layout_padding),
                    start = dimensionResource(id = R.dimen.back_button_layout_padding)
                )
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(id = R.dimen.main_screens_space))
            ) {
                Text(
                    text = stringResource(R.string.select_your_emotion),
                    fontWeight = FontWeight.Bold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    color = White,
                )

                Spacer(Modifier.height(40.dp))

                val emotions = EmotionName.entries
                val emotionListItems = mutableMapOf<EmotionName, String>()

                emotions.forEach {
                    emotionListItems[it] = getEmotionNameString(LocalContext.current, it)
                }

                val emotion by viewModel.emotion.collectAsStateWithLifecycle()

                val selected: (Any) -> Boolean = {
                    emotion == it as EmotionName
                }

                val onClick: (Any) -> Unit = {
                    viewModel.emotion.value = it as EmotionName
                }

                emotionListItems.onEachIndexed { index, entry ->
                    OptionButton(
                        value = entry.key,
                        text = entry.value,
                        selected = selected,
                        onClick = onClick
                    )

                    if (index != emotionListItems.size) {
                        Spacer(Modifier.height(8.dp))
                    }
                }

                Spacer(Modifier.height(50.dp))

                MyButton(
                    text = stringResource(id = R.string.confirm),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.onEvent(EmotionSelectionFromListEvent.OnEmotionResultConfirmed)
                    }
                )
            }
        }
    }
}