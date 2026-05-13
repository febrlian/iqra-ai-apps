package com.iqraai.ui.screens.learning

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqraai.R
import com.iqraai.ui.components.buttons.GhostButton
import com.iqraai.ui.components.buttons.PrimaryButton
import com.iqraai.ui.theme.Charcoal
import com.iqraai.ui.theme.DeepTeal
import com.iqraai.ui.theme.IqraTheme
import com.iqraai.ui.theme.Sage
import com.iqraai.ui.theme.WarmGray

data class AiFeedbackUiState(
    val score: Int,
    val tips: List<String>
)

@Composable
fun AiFeedbackOverlayScreen(
    state: AiFeedbackUiState,
    onTryAgain: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isGreat = state.score >= 80
    val title = if (isGreat) {
        stringResource(R.string.phase2_feedback_title_amazing)
    } else {
        stringResource(R.string.phase2_feedback_title_keep_trying)
    }
    val message = if (isGreat) {
        stringResource(R.string.phase2_feedback_message_amazing)
    } else {
        stringResource(R.string.phase2_feedback_message_keep_trying)
    }
    val overlayDescription = stringResource(R.string.phase2_feedback_overlay_description)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .semantics { contentDescription = overlayDescription },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 8.dp,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isGreat) "⭐" else "🌱",
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Charcoal
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.phase2_feedback_score, state.score),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = if (isGreat) Sage else DeepTeal,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = WarmGray
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = stringResource(R.string.phase2_feedback_tips_title),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Charcoal
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.tips.forEach { tip ->
                        Text(
                            text = stringResource(R.string.phase2_feedback_tip_item, tip),
                            style = MaterialTheme.typography.bodyMedium,
                            color = WarmGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GhostButton(
                        text = stringResource(R.string.phase2_feedback_try_again),
                        onClick = onTryAgain,
                        modifier = Modifier.weight(1f),
                        contentDescriptionText = stringResource(R.string.phase2_feedback_try_again)
                    )
                    PrimaryButton(
                        text = stringResource(R.string.phase2_feedback_continue),
                        onClick = onContinue,
                        modifier = Modifier.weight(1f),
                        contentDescriptionText = stringResource(R.string.phase2_feedback_continue)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AiFeedbackOverlayPreviewDefault() {
    IqraTheme {
        AiFeedbackOverlayScreen(
            state = AiFeedbackUiState(
                score = 91,
                tips = listOf(
                    stringResource(R.string.phase2_feedback_tip_pronounce_slowly),
                    stringResource(R.string.phase2_feedback_tip_listen_before_repeat)
                )
            ),
            onTryAgain = {},
            onContinue = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AiFeedbackOverlayPreviewAlternate() {
    IqraTheme {
        AiFeedbackOverlayScreen(
            state = AiFeedbackUiState(
                score = 63,
                tips = listOf(
                    stringResource(R.string.phase2_feedback_tip_hold_mic_closer),
                    stringResource(R.string.phase2_feedback_tip_repeat_three_times)
                )
            ),
            onTryAgain = {},
            onContinue = {}
        )
    }
}
