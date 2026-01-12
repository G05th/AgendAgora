package com.example.agendagora.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = OnPrimaryDark,
    onSecondary = OnSurfaceDark,
    onTertiary = OnSurfaceDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    background = LightBackground,
    surface = LightSurface,
    onPrimary = OnPrimaryLight,
    onSecondary = OnSurfaceLight,
    onTertiary = OnSurfaceLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight
)

@Composable
fun AgendAgoraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
