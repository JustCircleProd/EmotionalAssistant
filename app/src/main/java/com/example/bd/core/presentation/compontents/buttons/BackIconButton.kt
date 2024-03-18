package com.example.bd.core.presentation.compontents.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.db.R

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MyIconButton(
        imageVector = Icons.Rounded.ArrowBackIosNew,
        contentDescription = stringResource(R.string.back),
        onClick = onClick,
        modifier = modifier
    )
}