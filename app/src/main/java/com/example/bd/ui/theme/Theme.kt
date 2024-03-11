package com.example.bd.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import com.example.db.R

val AlegreyaFontFamily = FontFamily(
    Font(
        resId = R.font.alegreya_black,
        weight = FontWeight.Black,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.alegreya_extra_bold,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.alegreya_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.alegreya_semi_bold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.alegreya_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.alegreya_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    background = BackgroundDark,
    primaryContainer = PrimaryDark,
    surface = BackgroundDark,
    onPrimary = White,
    onSecondary = White,
    onBackground = TextDark,
    onSurface = TextDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    background = BackgroundDark,
    primaryContainer = PrimaryDark,
    surface = BackgroundDark,
    onPrimary = White,
    onSecondary = White,
    onBackground = TextDark,
    onSurface = TextDark
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun BdTheme(
    darkTheme: Boolean = false, //isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}