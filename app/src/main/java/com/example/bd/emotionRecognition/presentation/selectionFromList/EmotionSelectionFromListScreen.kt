package com.example.bd.emotionRecognition.presentation.selectionFromList

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.TonalButtonColor
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
                    fontSize = 27.sp,
                    color = White,
                )

                Spacer(Modifier.height(40.dp))

                val emotions = viewModel.emotions.collectAsStateWithLifecycle(listOf())

                val emotionListItems = mutableMapOf<Emotion, String>()

                emotions.value.forEach {
                    emotionListItems[it] = getEmotionNameString(LocalContext.current, it.name)
                }

                val emotion by viewModel.emotion.collectAsStateWithLifecycle()

                val selected: (Emotion) -> Boolean = {
                    emotion == it
                }

                val onClick: (Emotion) -> Unit = {
                    viewModel.emotion.value = it
                }

                emotionListItems.onEachIndexed { index, entry ->
                    EmotionListItem(
                        item = entry,
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

@Composable
private fun EmotionListItem(
    item: Map.Entry<Emotion, String>,
    selected: (Emotion) -> Boolean,
    onClick: (Emotion) -> Unit
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corners_size)),
        colors = CardDefaults.cardColors().copy(containerColor = TonalButtonColor),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (selected(item.key)) 2.dp else 0.dp,
                color = if (selected(item.key)) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corners_size))
            )
            .clickable { onClick(item.key) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.button_content_vertical_text_padding),
                horizontal = dimensionResource(id = R.dimen.button_content_horizontal_text_padding)
            )
        ) {
            RadioButton(
                selected = selected(item.key),
                colors = RadioButtonDefaults.colors().copy(unselectedColor = White),
                onClick = { }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = item.value,
                fontFamily = AlegreyaFontFamily,
                fontSize = 20.sp,
                color = White
            )
        }
    }
}