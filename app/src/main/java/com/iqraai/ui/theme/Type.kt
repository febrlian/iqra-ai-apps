package com.iqraai.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using default for prototype, but mimicking scales
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default, // Nunito
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        color = Charcoal
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default, // Nunito
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = Charcoal
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default, // Nunito
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = Charcoal
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default, // Nunito
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = WarmGray
    )
)

// Arabic specific text style
val ArabicDisplay = TextStyle(
    fontFamily = FontFamily.Default, // Uthmanic Naskh
    fontWeight = FontWeight.Normal,
    fontSize = 80.sp, // Large for children
    color = Charcoal
)

val ArabicBody = TextStyle(
    fontFamily = FontFamily.Default, // Uthmanic Naskh
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    color = Charcoal
)
