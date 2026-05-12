package com.iqraai.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iqraai.ui.components.buttons.GhostButton
import com.iqraai.ui.theme.Amber
import com.iqraai.ui.theme.OffWhite

@Composable
fun FirstLessonPreviewScreen(
    onSkip: () -> Unit,
    onComplete: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(0) }

    val steps = listOf(
        "This is the letter Alif. Touch it to hear the sound!",
        "Press this to hear how to say it.",
        "Now you try! Press and say 'Aaaah'",
        "See your progress here. Keep going!"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Mock Lesson Background (Dimmed)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f)) // Assuming dim over actual lesson
        )

        // Skip Button
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            GhostButton(text = "Skip", onClick = onSkip, contentDescriptionText = "Skip tutorial")
        }

        // Spotlight & Coach Mark
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mock Spotlight element to tap
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent)
                    .clickable {
                        if (currentStep < steps.size - 1) {
                            currentStep++
                        } else {
                            onComplete()
                        }
                    }
                    .padding(8.dp)
            ) {
                // Spotlight highlight
                Box(modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color.White.copy(alpha=0.2f)))
            }

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(tween(300)) + slideInVertically(tween(300)) { it / 2 }
            ) {
                Text(
                    text = steps[currentStep],
                    style = MaterialTheme.typography.bodyLarge,
                    color = OffWhite,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        // Navigation Dots
        RowNavigationDots(
            count = steps.size,
            currentIndex = currentStep,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        )
    }
}

@Composable
fun RowNavigationDots(count: Int, currentIndex: Int, modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Row(modifier = modifier) {
        for (i in 0 until count) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (i == currentIndex) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(if (i == currentIndex) Amber else OffWhite.copy(alpha = 0.5f))
            )
        }
    }
}
