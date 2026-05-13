package com.iqraai.ui.screens.learning

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
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
import com.iqraai.ui.theme.WarmGray

data class IqraVolumeUiModel(
    val number: Int,
    val progress: Float,
    val isLocked: Boolean
)

data class VolumeSelectionUiState(
    val volumes: List<IqraVolumeUiModel>,
    val selectedVolume: Int? = null
)

@Composable
fun VolumeSelectionScreen(
    state: VolumeSelectionUiState,
    onVolumeSelected: (Int) -> Unit,
    onStartOrContinueClick: (Int) -> Unit,
    onBackToDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedVolume = state.volumes.firstOrNull { it.number == state.selectedVolume }
    val selectedVolumeAvailable = selectedVolume != null && !selectedVolume.isLocked
    val ctaText = if ((selectedVolume?.progress ?: 0f) > 0f) {
        stringResource(R.string.phase2_volume_continue)
    } else {
        stringResource(R.string.phase2_volume_start)
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
            Text(
                text = stringResource(R.string.phase2_volume_title),
                style = MaterialTheme.typography.headlineLarge,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.phase2_volume_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = WarmGray
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(state.volumes, key = { it.number }) { volume ->
                    VolumeCard(
                        volume = volume,
                        isSelected = volume.number == state.selectedVolume,
                        onClick = { onVolumeSelected(volume.number) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (!selectedVolumeAvailable) {
                Text(
                    text = stringResource(R.string.phase2_volume_pick_prompt),
                    style = MaterialTheme.typography.bodyMedium,
                    color = WarmGray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            PrimaryButton(
                text = ctaText,
                onClick = { selectedVolume?.let { onStartOrContinueClick(it.number) } },
                enabled = selectedVolumeAvailable,
                contentDescriptionText = stringResource(R.string.phase2_volume_cta_content_description)
            )
            Spacer(modifier = Modifier.height(8.dp))
            GhostButton(
                text = stringResource(R.string.phase2_volume_back_dashboard),
                onClick = onBackToDashboard,
                contentDescriptionText = stringResource(R.string.phase2_volume_back_dashboard)
            )
        }
    }
}

@Composable
private fun VolumeCard(
    volume: IqraVolumeUiModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val progressPercent = (volume.progress * 100).toInt()
    val statusText = if (volume.isLocked) {
        stringResource(R.string.phase2_volume_locked)
    } else {
        stringResource(R.string.phase2_volume_unlocked)
    }
    val volumeTitle = stringResource(R.string.phase2_volume_title_item, volume.number)
    val cardContentDescription = "$volumeTitle. $statusText. " +
        stringResource(R.string.phase2_volume_progress, progressPercent)

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) Amber else WarmGray.copy(alpha = 0.25f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .semantics {
                contentDescription = cardContentDescription
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.phase2_volume_title_item, volume.number),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Charcoal
                )
                if (volume.isLocked) {
                    Text(
                        text = "🔒",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Text(
                text = statusText,
                style = MaterialTheme.typography.bodyMedium,
                color = if (volume.isLocked) WarmGray else DeepTeal
            )

            Column {
                LinearProgressIndicator(
                    progress = volume.progress.coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(WarmGray.copy(alpha = 0.2f), RoundedCornerShape(999.dp)),
                    color = DeepTeal,
                    trackColor = WarmGray.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(R.string.phase2_volume_progress, progressPercent),
                    style = MaterialTheme.typography.bodyMedium,
                    color = WarmGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VolumeSelectionScreenPreviewDefault() {
    IqraTheme {
        VolumeSelectionScreen(
            state = VolumeSelectionUiState(
                volumes = sampleVolumes(),
                selectedVolume = 2
            ),
            onVolumeSelected = {},
            onStartOrContinueClick = {},
            onBackToDashboard = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VolumeSelectionScreenPreviewAlternate() {
    IqraTheme {
        VolumeSelectionScreen(
            state = VolumeSelectionUiState(
                volumes = List(6) { index ->
                    IqraVolumeUiModel(number = index + 1, progress = 0f, isLocked = false)
                },
                selectedVolume = 1
            ),
            onVolumeSelected = {},
            onStartOrContinueClick = {},
            onBackToDashboard = {}
        )
    }
}

private fun sampleVolumes(): List<IqraVolumeUiModel> = listOf(
    IqraVolumeUiModel(number = 1, progress = 1f, isLocked = false),
    IqraVolumeUiModel(number = 2, progress = 0.45f, isLocked = false),
    IqraVolumeUiModel(number = 3, progress = 0.1f, isLocked = false),
    IqraVolumeUiModel(number = 4, progress = 0f, isLocked = true),
    IqraVolumeUiModel(number = 5, progress = 0f, isLocked = true),
    IqraVolumeUiModel(number = 6, progress = 0f, isLocked = true)
)
