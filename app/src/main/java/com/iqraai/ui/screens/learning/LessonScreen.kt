package com.iqraai.ui.screens.learning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iqraai.R
import com.iqraai.ui.components.buttons.GhostButton
import com.iqraai.ui.components.buttons.PrimaryButton
import com.iqraai.ui.theme.ArabicDisplay
import com.iqraai.ui.theme.Charcoal
import com.iqraai.ui.theme.DeepTeal
import com.iqraai.ui.theme.IqraTheme
import com.iqraai.ui.theme.OffWhite
import com.iqraai.ui.theme.WarmGray

data class LessonUiState(
    val volumeNumber: Int,
    val lessonNumber: Int,
    val arabicText: String,
    val transliteration: String,
    val currentStep: Int,
    val totalSteps: Int,
    val isRecording: Boolean = false
)

@Composable
fun LessonScreen(
    state: LessonUiState,
    onBack: () -> Unit,
    onPlayPronunciation: () -> Unit,
    onRecordTry: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progressDescription = stringResource(
        R.string.phase2_lesson_progress,
        state.currentStep,
        state.totalSteps
    )
    val arabicCardDescription = stringResource(
        R.string.phase2_lesson_arabic_card_description,
        state.arabicText,
        state.transliteration
    )
    val playDescription = stringResource(R.string.phase2_lesson_play)
    val recordDescription = if (state.isRecording) {
        stringResource(R.string.phase2_lesson_recording)
    } else {
        stringResource(R.string.phase2_lesson_record)
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = OffWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            GhostButton(
                text = stringResource(R.string.phase2_lesson_back),
                onClick = onBack,
                contentDescriptionText = stringResource(R.string.phase2_lesson_back)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(
                    R.string.phase2_lesson_title,
                    state.volumeNumber,
                    state.lessonNumber
                ),
                style = MaterialTheme.typography.headlineLarge,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.phase2_lesson_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = WarmGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            LinearProgressIndicator(
                progress = state.currentStep.toFloat() / state.totalSteps.coerceAtLeast(1),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .semantics { contentDescription = progressDescription },
                color = DeepTeal,
                trackColor = WarmGray.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = progressDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = WarmGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                color = androidx.compose.ui.graphics.Color.White,
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .semantics { contentDescription = arabicCardDescription }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.arabicText,
                        style = ArabicDisplay.copy(fontWeight = FontWeight.Normal),
                        textAlign = TextAlign.Center,
                        color = Charcoal
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = state.transliteration,
                        style = MaterialTheme.typography.bodyLarge,
                        color = DeepTeal
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.phase2_lesson_instruction),
                        style = MaterialTheme.typography.bodyMedium,
                        color = WarmGray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = onPlayPronunciation,
                    modifier = Modifier
                        .weight(1f)
                        .semantics { contentDescription = playDescription },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = playDescription)
                }

                Spacer(modifier = Modifier.width(10.dp))

                OutlinedButton(
                    onClick = onRecordTry,
                    modifier = Modifier
                        .weight(1f)
                        .semantics { contentDescription = recordDescription },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = recordDescription)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            PrimaryButton(
                text = stringResource(R.string.phase2_lesson_next),
                onClick = onNext,
                contentDescriptionText = stringResource(R.string.phase2_lesson_next)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LessonScreenPreviewDefault() {
    IqraTheme {
        LessonScreen(
            state = LessonUiState(
                volumeNumber = 2,
                lessonNumber = 3,
                arabicText = "ب",
                transliteration = "Baa",
                currentStep = 2,
                totalSteps = 5
            ),
            onBack = {},
            onPlayPronunciation = {},
            onRecordTry = {},
            onNext = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LessonScreenPreviewAlternate() {
    IqraTheme {
        LessonScreen(
            state = LessonUiState(
                volumeNumber = 1,
                lessonNumber = 1,
                arabicText = "ا",
                transliteration = "Alif",
                currentStep = 5,
                totalSteps = 5,
                isRecording = true
            ),
            onBack = {},
            onPlayPronunciation = {},
            onRecordTry = {},
            onNext = {}
        )
    }
}
