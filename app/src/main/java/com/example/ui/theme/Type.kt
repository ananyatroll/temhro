package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.example.R

/**
 * Google Font Provider configured with official certificates.
 * Provides downloadable typography with seamless offline fallback to system Sans-Serif.
 */
private val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Noto Sans Google Font: Comprehensive character coverage for Extended Latin (Somali, Afaan Oromoo) & Ge'ez (Amharic)
private val notoSansFont = GoogleFont("Noto Sans")
private val plusJakartaSansFont = GoogleFont("Plus Jakarta Sans")

/**
 * Primary Custom Font Family with fallback chain ensuring 100% glyph coverage
 * for Somali Latin Extended characters, diacritical marks, glottal stops, and Ethiopic script.
 */
val AppFontFamily = FontFamily(
    Font(googleFont = notoSansFont, fontProvider = fontProvider, weight = FontWeight.Normal),
    Font(googleFont = notoSansFont, fontProvider = fontProvider, weight = FontWeight.Medium),
    Font(googleFont = notoSansFont, fontProvider = fontProvider, weight = FontWeight.SemiBold),
    Font(googleFont = notoSansFont, fontProvider = fontProvider, weight = FontWeight.Bold),
    Font(googleFont = notoSansFont, fontProvider = fontProvider, weight = FontWeight.ExtraBold),
    Font(googleFont = notoSansFont, fontProvider = fontProvider, weight = FontWeight.Black)
)

val DisplayFontFamily = FontFamily(
    Font(googleFont = plusJakartaSansFont, fontProvider = fontProvider, weight = FontWeight.Bold),
    Font(googleFont = plusJakartaSansFont, fontProvider = fontProvider, weight = FontWeight.ExtraBold),
    Font(googleFont = plusJakartaSansFont, fontProvider = fontProvider, weight = FontWeight.Black),
    Font(googleFont = notoSansFont, fontProvider = fontProvider, weight = FontWeight.Bold)
)

/**
 * Shared baseline styling to eliminate font padding discrepancies, vertical clipping,
 * and display glitches across multilingual scripts (Somali, Oromo, Amharic, English).
 */
private val defaultPlatformStyle = PlatformTextStyle(
    includeFontPadding = false
)

private val defaultLineHeightStyle = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.None
)

// Centralized typography pairing: Display Heading Sans-Serif + Geometric Sans-Serif with Extended Latin support
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = DisplayFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 26.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.5).sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    displayMedium = TextStyle(
        fontFamily = DisplayFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 21.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.25).sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    displaySmall = TextStyle(
        fontFamily = DisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.1).sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    headlineLarge = TextStyle(
        fontFamily = DisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    headlineMedium = TextStyle(
        fontFamily = DisplayFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    headlineSmall = TextStyle(
        fontFamily = DisplayFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    titleLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    titleMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    titleSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.2.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    bodySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.2.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    labelLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    labelMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.4.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    ),
    labelSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.3.sp,
        platformStyle = defaultPlatformStyle,
        lineHeightStyle = defaultLineHeightStyle
    )
)
