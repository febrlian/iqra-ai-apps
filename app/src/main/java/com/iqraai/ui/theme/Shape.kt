package com.iqraai.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Shape Language
// Cards: 20dp radius
// Buttons: 16dp radius (pill-like)
// Avatars: 50% circle (CircleShape)
// Letter display: 16dp radius

val IqraShapes = Shapes(
    small = RoundedCornerShape(16.dp), // Buttons, Letter Display
    medium = RoundedCornerShape(20.dp), // Cards
    large = RoundedCornerShape(24.dp) // Celebration Cards
)
