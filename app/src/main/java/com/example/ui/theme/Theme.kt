package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldPrimary,
    secondary = IndigoActive,
    tertiary = GoldAccent,
    background = ReaderBgDark,
    surface = CardBgDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextLight,
    onSurface = TextLight
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimary,
    secondary = IndigoMedium,
    tertiary = GoldAccent,
    background = BgOffWhite,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color(0xFF111827),
    onSurface = Color(0xFF111827)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Disable dynamic colors to ensure our highly stylized custom palette brand is always perfectly presented!
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
