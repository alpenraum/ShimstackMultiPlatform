package com.alpenraum.shimstack.ui.base.compose.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.surt_black
import shimstackmultiplatform.composeapp.generated.resources.surt_black_italic
import shimstackmultiplatform.composeapp.generated.resources.surt_bold
import shimstackmultiplatform.composeapp.generated.resources.surt_bold_italic
import shimstackmultiplatform.composeapp.generated.resources.surt_regular
import shimstackmultiplatform.composeapp.generated.resources.surt_regular_italic

// Replace with your font locations
val Roboto = FontFamily.Default

@Composable
fun Surt() =
    FontFamily(
        Font(Res.font.surt_regular, FontWeight.Normal),
        Font(Res.font.surt_regular_italic, FontWeight.Normal, FontStyle.Italic),
        Font(Res.font.surt_bold, FontWeight.Bold),
        Font(Res.font.surt_bold_italic, FontWeight.Bold, FontStyle.Italic),
        Font(Res.font.surt_black, FontWeight.Black),
        Font(Res.font.surt_black_italic, FontWeight.Black, FontStyle.Italic)
    )

@Composable
fun AppTypography() =
    Typography(
        labelLarge =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                lineHeight = 20.sp,
                fontSize = 14.sp
            ),
        labelMedium =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.10000000149011612.sp,
                lineHeight = 16.sp,
                fontSize = 12.sp
            ),
        labelSmall =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.10000000149011612.sp,
                lineHeight = 16.sp,
                fontSize = 11.sp
            ),
        bodyLarge =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                letterSpacing = 0.sp,
                lineHeight = 24.sp,
                fontSize = 16.sp
            ),
        bodyMedium =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                letterSpacing = 0.sp,
                lineHeight = 20.sp,
                fontSize = 14.sp
            ),
        bodySmall =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                letterSpacing = 0.10000000149011612.sp,
                lineHeight = 16.sp,
                fontSize = 12.sp
            ),
        headlineLarge =
            TextStyle(
                fontFamily = Surt(),
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp,
                lineHeight = 40.sp,
                fontSize = 32.sp
            ),
        headlineMedium =
            TextStyle(
                fontFamily = Surt(),
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp,
                lineHeight = 36.sp,
                fontSize = 28.sp
            ),
        headlineSmall =
            TextStyle(
                fontFamily = Surt(),
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp,
                lineHeight = 32.sp,
                fontSize = 24.sp
            ),
        displayLarge =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.W800,
                letterSpacing = 0.sp,
                lineHeight = 64.sp,
                fontSize = 57.sp
            ),
        displayMedium =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.W600,
                letterSpacing = 0.sp,
                lineHeight = 52.sp,
                fontSize = 45.sp
            ),
        displaySmall =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                letterSpacing = 0.sp,
                lineHeight = 44.sp,
                fontSize = 36.sp
            ),
        titleLarge =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.W400,
                letterSpacing = 0.sp,
                lineHeight = 28.sp,
                fontSize = 22.sp
            ),
        titleMedium =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                lineHeight = 24.sp,
                fontSize = 16.sp
            ),
        titleSmall =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                lineHeight = 20.sp,
                fontSize = 14.sp
            )
    )