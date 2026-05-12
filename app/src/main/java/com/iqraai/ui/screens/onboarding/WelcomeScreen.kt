package com.iqraai.ui.screens.onboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iqraai.ui.components.buttons.GhostButton
import com.iqraai.ui.components.buttons.PrimaryButton
import com.iqraai.ui.theme.Amber
import com.iqraai.ui.theme.DeepTeal
import com.iqraai.ui.theme.OffWhite

@Composable
fun WelcomeScreen(
    onStartLearningClick: () -> Unit,
    onAlreadyHaveAccountClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "pulse_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DeepTeal, OffWhite)
                )
            )
            .semantics { contentDescription = "Welcome to Iqra AI. Double tap to start learning." }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Abstract light shape
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(pulseScale)
                    .background(Amber.copy(alpha = 0.3f), CircleShape)
                    .padding(16.dp)
                    .background(Amber.copy(alpha = 0.6f), CircleShape)
                    .padding(16.dp)
                    .background(Amber, CircleShape)
            )

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "Assalamualaikum!",
                style = MaterialTheme.typography.displayLarge,
                color = OffWhite,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Let's learn Quran together, one letter at a time.",
                style = MaterialTheme.typography.bodyLarge,
                color = OffWhite.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = "Start Learning",
                onClick = onStartLearningClick
            )
            Spacer(modifier = Modifier.height(8.dp))
            GhostButton(
                text = "I already have an account",
                onClick = onAlreadyHaveAccountClick
            )
        }
    }
}
