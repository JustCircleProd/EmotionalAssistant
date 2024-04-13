package com.example.bd.emotionRecognition.presentation.methodSelection

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bd.core.presentation.compontents.NavigationItem
import com.example.bd.core.presentation.compontents.buttons.BackButton
import com.example.bd.core.presentation.compontents.buttons.MyButton
import com.example.bd.core.presentation.theme.AlegreyaFontFamily
import com.example.bd.core.presentation.theme.BdTheme
import com.example.bd.core.presentation.theme.TonalButtonColor
import com.example.bd.core.presentation.theme.White
import com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel.EmotionRecognitionEvent
import com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel.EmotionRecognitionViewModel
import com.example.bd.emotionRecognition.presentation.util.createImageFile
import com.example.db.R
import kotlinx.coroutines.launch


@Composable
fun EmotionRecognitionMethodSelectionScreen(
    navController: NavHostController,
    returnRoute: String,
    viewModel: EmotionRecognitionViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Surface {
        Column {
            BackButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.toolbar_padding),
                    start = dimensionResource(id = R.dimen.toolbar_padding)
                )
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(id = R.dimen.main_screens_space))
            ) {
                Text(
                    text = stringResource(R.string.choose_a_method),
                    fontWeight = FontWeight.Bold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    color = White,
                )

                Spacer(Modifier.height(40.dp))

                CameraButton(
                    onPictureTook = {
                        viewModel.onEvent(EmotionRecognitionEvent.OnUriReady(context, it))
                        navController.navigate(
                            NavigationItem.EmotionRecognitionByPhoto.getRouteWithArguments(
                                returnRoute
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                GalleryButton(
                    onImagePicked = {
                        viewModel.onEvent(EmotionRecognitionEvent.OnUriReady(context, it))
                        navController.navigate(
                            NavigationItem.EmotionRecognitionByPhoto.getRouteWithArguments(
                                returnRoute
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ListButton(
                    onClick = {
                        navController.navigate(
                            NavigationItem.EmotionSelectionFromList.getRouteWithArguments(
                                returnRoute
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun CameraButton(
    onPictureTook: (Uri) -> Unit
) {
    val context = LocalContext.current
    var uri by rememberSaveable { mutableStateOf<Uri>(Uri.EMPTY) }

    LaunchedEffect(Unit) {
        launch {
            uri = context.createImageFile()
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                onPictureTook(uri)
            }
        }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            cameraLauncher.launch(uri)
        }
    }

    MyButton(
        iconImageVector = Icons.Rounded.CameraAlt,
        iconContentDescription = null,
        text = stringResource(R.string.take_a_picture),
        containerColor = TonalButtonColor,
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            val permissionCheckResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                cameraLauncher.launch(uri)
            } else {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    )
}

@Composable
private fun GalleryButton(onImagePicked: (Uri) -> Unit) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            onImagePicked(it)
        }
    }

    MyButton(
        iconImageVector = Icons.Rounded.Image,
        iconContentDescription = null,
        text = stringResource(R.string.pick_from_the_gallery),
        containerColor = TonalButtonColor,
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    )
}


@Composable
private fun ListButton(
    onClick: () -> Unit
) {
    MyButton(
        iconImageVector = Icons.Rounded.Checklist,
        iconContentDescription = null,
        text = stringResource(R.string.select_from_the_list),
        containerColor = TonalButtonColor,
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    )
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
        Column {
            BackButton(
                onClick = { },
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.toolbar_padding),
                    start = dimensionResource(id = R.dimen.toolbar_padding)
                )
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(id = R.dimen.main_screens_space))
            ) {
                Text(
                    text = stringResource(R.string.choose_a_method),
                    fontWeight = FontWeight.Bold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    color = White,
                )

                Spacer(Modifier.height(40.dp))

                CameraButton(onPictureTook = {

                })

                Spacer(modifier = Modifier.height(12.dp))

                GalleryButton(onImagePicked = {

                })

                Spacer(modifier = Modifier.height(12.dp))

                ListButton(
                    onClick = {

                    }
                )
            }
        }
    }
}