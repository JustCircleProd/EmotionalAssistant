package com.example.bd.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bd.ui.common.MyButton
import com.example.bd.ui.theme.AlegreyaFontFamily
import com.example.bd.ui.theme.BdTheme
import com.example.bd.ui.theme.TonalButtonColor
import com.example.bd.ui.theme.White
import com.example.db.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Objects

/*suspend fun readImage(context: Context): Bitmap? {
    return withContext(Dispatchers.IO) {
        val files = context.filesDir.listFiles()

        val file = files?.first { it.canRead() && it.isFile && it.name == "image.png" }

        return@withContext if (file != null) {
            val bytes = file.readBytes()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } else {
            null
        }
    }
}*/

private suspend fun Context.createImageFile(): File {
    return withContext(Dispatchers.IO) {
        File.createTempFile(
            "image",
            ".png",
            filesDir
        )
    }
}

@Composable
fun EmotionRecognitionScreen(
    navController: NavHostController,
    viewModel: EmotionRecognitionViewModel = hiltViewModel()
) {
    Surface {


        val imageUri = rememberSaveable { mutableStateOf<Uri?>(Uri.EMPTY) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.main_screens_space))
        ) {
            when (imageUri.value) {
                Uri.EMPTY -> {
                    MethodSelection(imageUri)
                }

                null -> {
                    FromList()
                }

                else -> {
                    ByPhoto(imageUri, viewModel)
                }
            }
        }
    }
}

@Composable
private fun MethodSelection(
    imageUri: MutableState<Uri?>
) {
    val context = LocalContext.current
    var uri by rememberSaveable { mutableStateOf<Uri?>(null) }

    LaunchedEffect(true) {
        launch {
            val file = context.createImageFile()
            uri = FileProvider.getUriForFile(
                Objects.requireNonNull(context),
                context.packageName + ".fileprovider", file
            )
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                imageUri.value = uri
            }
        }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            cameraLauncher.launch(uri)
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            imageUri.value = it
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Выберите метод",
            fontWeight = FontWeight.Bold,
            fontFamily = AlegreyaFontFamily,
            fontSize = 27.sp,
            color = White,
        )

        Spacer(Modifier.height(40.dp))

        MyButton(
            iconResId = R.drawable.icon_camera,
            iconContentDescription = "",
            text = "Сделать снимок",
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

        Spacer(modifier = Modifier.height(12.dp))

        MyButton(
            iconResId = R.drawable.icon_photo,
            iconContentDescription = "",
            text = "Выбрать из галереи",
            containerColor = TonalButtonColor,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        MyButton(
            iconResId = R.drawable.icon_checklist,
            iconContentDescription = "",
            text = "Выбрать из списка",
            containerColor = TonalButtonColor,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                imageUri.value = null
            }
        )
    }
}

@Composable
private fun ByPhoto(imageUri: MutableState<Uri?>, viewModel: EmotionRecognitionViewModel) {
    val croppedFace by viewModel.croppedFace.collectAsStateWithLifecycle()

    viewModel.classifyEmotion(LocalContext.current, imageUri.value!!)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (croppedFace != null) {
            Text(
                text = "Определяем эмоцию...",
                fontFamily = AlegreyaFontFamily,
                fontSize = 27.sp
            )

            Image(
                bitmap = croppedFace!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(300.dp, 500.dp)
            )
        }
    }
}

@Composable
private fun FromList() {
    Text(text = "From list")
}

@Preview
@Composable
private fun Preview() {
    BdTheme {
        EmotionRecognitionScreen(navController = rememberNavController())
    }
}