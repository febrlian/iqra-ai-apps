package com.iqraai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iqraai.R
import com.iqraai.ui.screens.home.HomeDashboardScreen
import com.iqraai.ui.screens.home.HomeDashboardUiState
import com.iqraai.ui.screens.learning.AiFeedbackOverlayScreen
import com.iqraai.ui.screens.learning.AiFeedbackUiState
import com.iqraai.ui.screens.learning.CelebrationUiState
import com.iqraai.ui.screens.learning.IqraVolumeUiModel
import com.iqraai.ui.screens.learning.LessonCompleteCelebrationScreen
import com.iqraai.ui.screens.learning.LessonScreen
import com.iqraai.ui.screens.learning.LessonUiState
import com.iqraai.ui.screens.learning.VolumeSelectionScreen
import com.iqraai.ui.screens.learning.VolumeSelectionUiState
import com.iqraai.ui.screens.onboarding.ChildProfileCreationScreen
import com.iqraai.ui.screens.onboarding.FirstLessonPreviewScreen
import com.iqraai.ui.screens.onboarding.ParentGateScreen
import com.iqraai.ui.screens.onboarding.WelcomeScreen
import com.iqraai.ui.screens.settings.SettingsScreen
import com.iqraai.ui.screens.settings.SettingsUiState
import com.iqraai.ui.theme.DeepTeal

object AppRoutes {
    const val Welcome = "welcome"
    const val ChildProfile = "child_profile"
    const val ParentGate = "parent_gate"
    const val FirstPreview = "first_preview"
    const val VolumeSelection = "volume_selection"
    const val Lesson = "lesson"
    const val Feedback = "feedback"
    const val Celebration = "celebration"
    const val Home = "home"
    const val Settings = "settings"
}

@Composable
fun IqraAppNavHost(
    navController: NavHostController = rememberNavController()
) {
    var childName by remember { mutableStateOf("Aisyah") }
    var avatarColor by remember { mutableStateOf(DeepTeal) }

    var selectedVolume by remember { mutableIntStateOf(1) }
    var currentLesson by remember { mutableIntStateOf(1) }
    var lessonStep by remember { mutableIntStateOf(1) }
    val totalLessonSteps = 5
    var feedbackScore by remember { mutableIntStateOf(88) }
    var streakDays by remember { mutableIntStateOf(4) }
    var points by remember { mutableIntStateOf(120) }
    var completedVolumes by remember { mutableIntStateOf(1) }
    var isRecording by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.Welcome
    ) {
        composable(AppRoutes.Welcome) {
            WelcomeScreen(
                onStartLearningClick = { navController.navigate(AppRoutes.ChildProfile) },
                onAlreadyHaveAccountClick = { navController.navigate(AppRoutes.Home) }
            )
        }

        composable(AppRoutes.ChildProfile) {
            ChildProfileCreationScreen(
                onContinueClick = { name, _, color ->
                    childName = name
                    avatarColor = color
                    navController.navigate(AppRoutes.ParentGate)
                }
            )
        }

        composable(AppRoutes.ParentGate) {
            ParentGateScreen(
                onBack = { navController.popBackStack() },
                onSettings = { navController.navigate(AppRoutes.Settings) },
                onSuccess = { navController.navigate(AppRoutes.FirstPreview) },
                onLockedOut = {
                    navController.navigate(AppRoutes.Welcome) {
                        popUpTo(AppRoutes.Welcome) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.FirstPreview) {
            FirstLessonPreviewScreen(
                onSkip = { navController.navigate(AppRoutes.VolumeSelection) },
                onComplete = { navController.navigate(AppRoutes.VolumeSelection) }
            )
        }

        composable(AppRoutes.VolumeSelection) {
            VolumeSelectionScreen(
                state = VolumeSelectionUiState(
                    volumes = buildMockVolumes(selectedVolume),
                    selectedVolume = selectedVolume
                ),
                onVolumeSelected = { selectedVolume = it },
                onStartOrContinueClick = { chosenVolume ->
                    selectedVolume = chosenVolume
                    currentLesson = 1
                    lessonStep = 1
                    navController.navigate(AppRoutes.Lesson)
                },
                onBackToDashboard = { navController.navigate(AppRoutes.Home) }
            )
        }

        composable(AppRoutes.Lesson) {
            LessonScreen(
                state = LessonUiState(
                    volumeNumber = selectedVolume,
                    lessonNumber = currentLesson,
                    arabicText = if (selectedVolume % 2 == 0) "ب" else "ا",
                    transliteration = if (selectedVolume % 2 == 0) {
                        stringResource(R.string.phase2_letter_baa_transliteration)
                    } else {
                        stringResource(R.string.phase2_letter_alif_transliteration)
                    },
                    currentStep = lessonStep,
                    totalSteps = totalLessonSteps,
                    isRecording = isRecording
                ),
                onBack = { navController.popBackStack() },
                onPlayPronunciation = { isRecording = false },
                onRecordTry = {
                    isRecording = !isRecording
                },
                onNext = {
                    if (lessonStep < totalLessonSteps) {
                        lessonStep += 1
                    } else {
                        feedbackScore = if (selectedVolume <= 2) 90 else 72
                        isRecording = false
                        navController.navigate(AppRoutes.Feedback)
                    }
                }
            )
        }

        composable(AppRoutes.Feedback) {
            AiFeedbackOverlayScreen(
                state = AiFeedbackUiState(
                    score = feedbackScore,
                    tips = if (feedbackScore >= 80) {
                        listOf(
                            stringResource(R.string.phase2_feedback_tip_pronounce_slowly),
                            stringResource(R.string.phase2_feedback_tip_listen_before_repeat)
                        )
                    } else {
                        listOf(
                            stringResource(R.string.phase2_feedback_tip_hold_mic_closer),
                            stringResource(R.string.phase2_feedback_tip_repeat_three_times)
                        )
                    }
                ),
                onTryAgain = {
                    lessonStep = (lessonStep - 1).coerceAtLeast(1)
                    navController.popBackStack()
                },
                onContinue = {
                    points += if (feedbackScore >= 80) 100 else 60
                    streakDays += 1
                    navController.navigate(AppRoutes.Celebration)
                }
            )
        }

        composable(AppRoutes.Celebration) {
            LessonCompleteCelebrationScreen(
                state = CelebrationUiState(
                    starsEarned = if (feedbackScore >= 80) 3 else 2,
                    pointsEarned = if (feedbackScore >= 80) 100 else 60,
                    streakDays = streakDays,
                    completedLessonLabel = stringResource(
                        R.string.phase2_lesson_title,
                        selectedVolume,
                        currentLesson
                    )
                ),
                onContinue = {
                    if (selectedVolume > completedVolumes) {
                        completedVolumes = selectedVolume
                    }
                    navController.navigate(AppRoutes.Home) {
                        popUpTo(AppRoutes.VolumeSelection) { inclusive = false }
                    }
                },
                onGoHome = {
                    navController.navigate(AppRoutes.Home) {
                        popUpTo(AppRoutes.VolumeSelection) { inclusive = false }
                    }
                }
            )
        }


        composable(AppRoutes.Settings) {
            SettingsScreen(
                state = SettingsUiState(),
                onBack = { navController.popBackStack() },
                onEditProfile = { /* TODO */ },
                onChangePin = { /* TODO */ },
                onLogout = {
                    navController.navigate(AppRoutes.Welcome) {
                        popUpTo(AppRoutes.Welcome) { inclusive = true }
                    }
                },
                onToggleNotifications = { /* TODO */ },
                onToggleDarkMode = { /* TODO */ }
            )
        }

        composable(AppRoutes.Home) {
            HomeDashboardScreen(
                state = HomeDashboardUiState(
                    childName = childName,
                    avatarColor = avatarColor,
                    currentVolume = selectedVolume,
                    currentLesson = currentLesson,
                    streakDays = streakDays,
                    points = points,
                    completedVolumes = completedVolumes
                ),
                onContinueLearning = {
                    lessonStep = 1
                    navController.navigate(AppRoutes.Lesson)
                },
                onOpenVolumes = { navController.navigate(AppRoutes.VolumeSelection) },
                onOpenProgress = { navController.navigate(AppRoutes.VolumeSelection) }
            )
        }
    }
}

private fun buildMockVolumes(selectedVolume: Int): List<IqraVolumeUiModel> {
    val progressMap = listOf(1f, 0.52f, 0.2f, 0f, 0f, 0f)
    return progressMap.mapIndexed { index, progress ->
        val volumeNumber = index + 1
        IqraVolumeUiModel(
            number = volumeNumber,
            progress = progress,
            isLocked = volumeNumber > 3 && volumeNumber != selectedVolume
        )
    }
}
