package com.ayesh.leveintest.presantation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

data class LocalTypography(
    val displayLarge: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
    val textFieldNormal: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
    val bodyLarge: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
    val bodyUnderLine: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            textDecoration = TextDecoration.Underline,
        ),
    val bodyNormal: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
    val titleLarge: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
        ),
    val labelSmall: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
    val titleMedium: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
    val headlineSmallNormal: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 24.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
)

val LocalTextTypo = staticCompositionLocalOf { LocalTypography() }

val typoLocal: LocalTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTextTypo.current
