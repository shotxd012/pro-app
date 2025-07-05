package com.tokyomc.shot.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = FinanceGreen,
    onPrimary = FinanceBlack,
    primaryContainer = FinanceGreenDark,
    onPrimaryContainer = FinanceBlack,
    secondary = FinanceGreenLight,
    onSecondary = FinanceBlack,
    secondaryContainer = FinanceGreenTransparent,
    onSecondaryContainer = FinanceGreen,
    tertiary = FinanceBlue,
    onTertiary = FinanceBlack,
    tertiaryContainer = FinanceBlue,
    onTertiaryContainer = FinanceBlack,
    background = FinanceBlack,
    onBackground = FinanceWhite,
    surface = FinanceBlackLight,
    onSurface = FinanceWhite,
    surfaceVariant = FinanceGray,
    onSurfaceVariant = FinanceWhiteDim,
    outline = FinanceGreen,
    outlineVariant = FinanceGrayLight,
    error = FinanceRed,
    onError = FinanceBlack,
    errorContainer = FinanceRed,
    onErrorContainer = FinanceBlack
)

private val LightColorScheme = lightColorScheme(
    primary = FinanceGreen,
    onPrimary = FinanceBlack,
    primaryContainer = FinanceGreenLight,
    onPrimaryContainer = FinanceBlack,
    secondary = FinanceGreenDark,
    onSecondary = FinanceBlack,
    secondaryContainer = FinanceGreenTransparent,
    onSecondaryContainer = FinanceGreen,
    tertiary = FinanceBlue,
    onTertiary = FinanceBlack,
    tertiaryContainer = FinanceBlue,
    onTertiaryContainer = FinanceBlack,
    background = FinanceWhite,
    onBackground = FinanceBlack,
    surface = FinanceWhite,
    onSurface = FinanceBlack,
    surfaceVariant = FinanceGrayLight,
    onSurfaceVariant = FinanceBlack,
    outline = FinanceGreen,
    outlineVariant = FinanceGray,
    error = FinanceRed,
    onError = FinanceBlack,
    errorContainer = FinanceRed,
    onErrorContainer = FinanceBlack
)

@Composable
fun ShotAppTheme(
    darkTheme: Boolean = true, // Default to dark theme for finance app
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic color to maintain our custom theme
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
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}