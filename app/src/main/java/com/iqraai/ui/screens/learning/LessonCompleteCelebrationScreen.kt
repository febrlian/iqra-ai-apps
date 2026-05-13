package com.iqraai.ui.screens.learning

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqraai.R
import com.iqraai.ui.components.buttons.GhostButton
import com.iqraai.ui.components.buttons.PrimaryButton
import com.iqraai.ui.theme.Amber
import com.iqraai.ui.theme.Charcoal
import com.iqraai.ui.theme.DeepTeal
import com.iqraai.ui.theme.IqraTheme
import com.iqraai.ui.theme.OffWhite
import com.iqraai.ui.theme.Sage
import com.iqraai.ui.theme.WarmGray

data class CelebrationUiState(
    val starsEarned: Int,
    val pointsEarned: Int,
    val streakDays: Int,
    val completedLessonLabel: String
)

@Composable
fun LessonCompleteCelebrationScreen(
    state: CelebrationUiState,
    onContinue: () -> Unit,
    onGoHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val summaryDescription = stringResource(
        R.string.phase2_celebration_summary_description,
        state.starsEarned,
        state.pointsEarned,
        state.streakDays
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = OffWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(DeepTeal.copy(alpha = 0.15f), OffWhite)
                    )
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Amber.copy(alpha = 0.25f))
                    .fillMaxWidth(0.4f)
                    .height(120.dp)
            )
            Text(
                text = "🎉",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.phase2_celebration_title),
                style = MaterialTheme.typography.headlineLarge,
                color = Charcoal,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.phase2_celebration_subtitle, state.completedLessonLabel),
                style = MaterialTheme.typography.bodyLarge,
                color = WarmGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                color = androidx.compose.ui.graphics.Color.White,
                shape = MaterialTheme.shapes.large,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = summaryDescription }
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(
                            R.string.phase2_celebration_points,
                            state.starsEarned,
                            state.pointsEarned
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = DeepTeal
                    )
                    Text(
                        text = stringResource(R.string.phase2_celebration_streak, state.streakDays),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Sage
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            PrimaryButton(
                text = stringResource(R.string.phase2_celebration_continue),
                onClick = onContinue,
                contentDescriptionText = stringResource(R.string.phase2_celebration_continue)
            )
            Spacer(modifier = Modifier.height(8.dp))
            GhostButton(
                text = stringResource(R.string.phase2_celebration_go_home),
                onClick = onGoHome,
                contentDescriptionText = stringResource(R.string.phase2_celebration_go_home)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LessonCompleteCelebrationPreviewDefault() {
    IqraTheme {
        LessonCompleteCelebrationScreen(
            state = CelebrationUiState(
                starsEarned = 3,
                pointsEarned = 120,
                streakDays = 4,
                completedLessonLabel = "Iqra' 2 - Lesson 3"
            ),
            onContinue = {},
            onGoHome = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LessonCompleteCelebrationPreviewAlternate() {
    IqraTheme {
        LessonCompleteCelebrationScreen(
            state = CelebrationUiState(
                starsEarned = 5,
                pointsEarned = 220,
                streakDays = 12,
                completedLessonLabel = "Iqra' 4 - Lesson 10"
            ),
            onContinue = {},
            onGoHome = {}
        )
    }
}
