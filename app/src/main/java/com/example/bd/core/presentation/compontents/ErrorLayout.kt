package com.example.bd.core.presentation.compontents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.db.R

@Composable
fun ErrorLayout(
    onBackButtonClick: () -> Unit,
    errorText: String = stringResource(id = R.string.error)
) {
    Column {
        BackButton(
            onClick = onBackButtonClick,
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.toolbar_padding),
                start = dimensionResource(id = R.dimen.toolbar_padding)
            )
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.main_screens_space))
        ) {
            Text(
                text = errorText,
                fontFamily = AlegreyaFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
        Surface {
            Column {
                BackButton(
                    onClick = {},
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.toolbar_padding),
                        start = dimensionResource(id = R.dimen.toolbar_padding)
                    )
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.main_screens_space))
                ) {
                    Text(
                        text = stringResource(id = R.string.error),
                        fontFamily = AlegreyaFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

}