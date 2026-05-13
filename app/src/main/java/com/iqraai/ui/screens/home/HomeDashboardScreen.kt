package com.iqraai.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.iqraai.ui.components.buttons.PrimaryButton
import com.iqraai.ui.theme.Amber
import com.iqraai.ui.theme.Charcoal
import com.iqraai.ui.theme.DeepTeal
import com.iqraai.ui.theme.IqraTheme
import com.iqraai.ui.theme.OffWhite
import com.iqraai.ui.theme.WarmGray

data class HomeDashboardUiState(
    val childName: String,
    val avatarColor: Color,
    val currentVolume: Int,
    val currentLesson: Int,
    val streakDays: Int,
    val points: Int,
    val completedVolumes: Int
)

@Composable
fun HomeDashboardScreen(
    state: HomeDashboardUiState,
    onContinueLearning: () -> Unit,
    onOpenVolumes: () -> Unit,
    onOpenProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val avatarDescription = stringResource(
        R.string.phase2_dashboard_avatar_description,
        state.childName
    )
    val continueDescription = stringResource(
        R.string.phase2_dashboard_continue_description,
        state.currentVolume,
        state.currentLesson
    )
    val openVolumesDescription = stringResource(R.string.phase2_dashboard_open_volumes)
    val openProgressDescription = stringResource(R.string.phase2_dashboard_open_progress)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = OffWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = avatarDescription }
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(state.avatarColor, CircleShape)
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column {
                    Text(
                        text = stringResource(R.string.phase2_dashboard_greeting, state.childName),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Charcoal
                    )
                    Text(
                        text = stringResource(R.string.phase2_dashboard_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = WarmGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onContinueLearning)
                    .semantics { contentDescription = continueDescription }
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = stringResource(R.string.phase2_dashboard_continue_title),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = DeepTeal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(
                            R.string.phase2_dashboard_continue_subtitle,
                            state.currentVolume,
                            state.currentLesson
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = WarmGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    PrimaryButton(
                        text = stringResource(R.string.phase2_dashboard_continue_cta),
                        onClick = onContinueLearning,
                        contentDescriptionText = continueDescription
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DashboardStatCard(
                    title = stringResource(R.string.phase2_dashboard_streak_label),
                    value = state.streakDays.toString(),
                    accentColor = Amber,
                    modifier = Modifier.weight(1f)
                )
                DashboardStatCard(
                    title = stringResource(R.string.phase2_dashboard_points_label),
                    value = state.points.toString(),
                    accentColor = DeepTeal,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.phase2_dashboard_quick_actions),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Charcoal
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = onOpenVolumes,
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics { contentDescription = openVolumesDescription },
                        shape = MaterialTheme.shapes.small,
                        border = androidx.compose.foundation.BorderStroke(1.dp, DeepTeal)
                    ) {
                        Text(text = openVolumesDescription)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = onOpenProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics { contentDescription = openProgressDescription },
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(text = openProgressDescription)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(
                            R.string.phase2_dashboard_completed_volumes,
                            state.completedVolumes
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = WarmGray
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardStatCard(
    title: String,
    value: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                color = accentColor
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = WarmGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeDashboardPreviewDefault() {
    IqraTheme {
        HomeDashboardScreen(
            state = HomeDashboardUiState(
                childName = "Amina",
                avatarColor = DeepTeal,
                currentVolume = 2,
                currentLesson = 3,
                streakDays = 5,
                points = 320,
                completedVolumes = 1
            ),
            onContinueLearning = {},
            onOpenVolumes = {},
            onOpenProgress = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeDashboardPreviewAlternate() {
    IqraTheme {
        HomeDashboardScreen(
            state = HomeDashboardUiState(
                childName = "Ahmad",
                avatarColor = Amber,
                currentVolume = 4,
                currentLesson = 8,
                streakDays = 14,
                points = 890,
                completedVolumes = 3
            ),
            onContinueLearning = {},
            onOpenVolumes = {},
            onOpenProgress = {}
        )
    }
}
