package com.justcircleprod.emotionalassistant.emotionRecognition.presentation.methodSelection

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
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
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.BackButton
import com.justcircleprod.emotionalassistant.core.presentation.compontents.buttons.MyButton
import com.justcircleprod.emotionalassistant.core.presentation.theme.AlegreyaFontFamily
import com.justcircleprod.emotionalassistant.core.presentation.theme.BdTheme
import com.justcircleprod.emotionalassistant.core.presentation.theme.TonalButtonColor
import com.justcircleprod.emotionalassistant.core.presentation.theme.White
import com.justcircleprod.emotionalassistant.emotionRecognition.presentation.util.createImageFile
import com.justcircleprod.emotionalassistant.emotionRecognition.presentation.viewModel.EmotionRecognitionEvent
import com.justcircleprod.emotionalassistant.emotionRecognition.presentation.viewModel.EmotionRecognitionViewModel
import kotlinx.coroutines.launch


@Composable
fun EmotionRecognitionMethodSelectionScreen(
    navController: NavHostController,
    returnRoute: String?,
    viewModel: EmotionRecognitionViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Surface {
        Column {
            BackButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
            )

            if (returnRoute == null) return@Surface

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

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

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
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
        Surface {
            Column {
                BackButton(
                    onClick = { },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.toolbar_padding))
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                        .animateContentSize()
                ) {
                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

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

                    Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPrototype() {
    Surface {
        Column {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.toolbar_padding))
                    .size(dimensionResource(id = R.dimen.icon_button_size))
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_button_icon_size))
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dimensionResource(id = R.dimen.main_screens_space))
                    .animateContentSize()
            ) {
                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))

                Text(
                    text = stringResource(R.string.choose_a_method),
                    fontWeight = FontWeight.Bold,
                    fontFamily = AlegreyaFontFamily,
                    fontSize = 26.sp,
                    color = Color.Black,
                )

                Spacer(Modifier.height(40.dp))

                Button(
                    onClick = { },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner_size)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            Color.Black,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner_size))
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(
                            vertical = dimensionResource(id = R.dimen.button_content_vertical_text_padding),
                            horizontal = dimensionResource(id = R.dimen.button_content_horizontal_text_padding)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CameraAlt,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(dimensionResource(id = R.dimen.button_icon_size))
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = stringResource(R.string.take_a_picture),
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 19.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner_size)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            Color.Black,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner_size))
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(
                            vertical = dimensionResource(id = R.dimen.button_content_vertical_text_padding),
                            horizontal = dimensionResource(id = R.dimen.button_content_horizontal_text_padding)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Image,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(dimensionResource(id = R.dimen.button_icon_size))
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = stringResource(R.string.pick_from_the_gallery),
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 19.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner_size)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            Color.Black,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner_size))
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(
                            vertical = dimensionResource(id = R.dimen.button_content_vertical_text_padding),
                            horizontal = dimensionResource(id = R.dimen.button_content_horizontal_text_padding)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Checklist,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(dimensionResource(id = R.dimen.button_icon_size))
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = stringResource(R.string.select_from_the_list),
                            fontFamily = AlegreyaFontFamily,
                            fontSize = 19.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(Modifier.height(dimensionResource(id = R.dimen.main_screens_space)))
            }
        }
    }
}