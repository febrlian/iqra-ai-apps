package com.iqraai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqraai.ui.viewmodel.AppIntent
import com.iqraai.ui.viewmodel.AppViewModel
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
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

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
                    viewModel.handleIntent(AppIntent.UpdateChildProfile(name, color.value.toInt()))
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
                onSkip = {
                    viewModel.handleIntent(AppIntent.CompleteFirstPreview)
                    navController.navigate(AppRoutes.VolumeSelection)
                },
                onComplete = {
                    viewModel.handleIntent(AppIntent.CompleteFirstPreview)
                    navController.navigate(AppRoutes.VolumeSelection)
                }
            )
        }

        composable(AppRoutes.VolumeSelection) {
            VolumeSelectionScreen(
                state = VolumeSelectionUiState(
                    volumes = state.volumes.map { vol ->
                        IqraVolumeUiModel(
                            number = vol.volumeNumber,
                            progress = vol.progress,
                            isLocked = vol.isLocked
                        )
                    },
                    selectedVolume = state.selectedVolume
                ),
                onVolumeSelected = { viewModel.handleIntent(AppIntent.SelectVolume(it)) },
                onStartOrContinueClick = { chosenVolume ->
                    viewModel.handleIntent(AppIntent.StartLesson(chosenVolume, 1))
                    navController.navigate(AppRoutes.Lesson)
                },
                onBackToDashboard = { navController.navigate(AppRoutes.Home) }
            )
        }

        composable(AppRoutes.Lesson) {
            LessonScreen(
                state = LessonUiState(
                    volumeNumber = state.selectedVolume,
                    lessonNumber = state.currentLessonNumber,
                    arabicText = if (state.selectedVolume % 2 == 0) "ب" else "ا",
                    transliteration = if (state.selectedVolume % 2 == 0) {
                        stringResource(R.string.phase2_letter_baa_transliteration)
                    } else {
                        stringResource(R.string.phase2_letter_alif_transliteration)
                    },
                    currentStep = state.currentLessonStep,
                    totalSteps = state.totalLessonSteps,
                    isRecording = state.isRecording
                ),
                onBack = { navController.popBackStack() },
                onPlayPronunciation = { if(state.isRecording) viewModel.handleIntent(AppIntent.ToggleRecording) },
                onRecordTry = {
                    viewModel.handleIntent(AppIntent.ToggleRecording)
                },
                onNext = {
                    if (state.currentLessonStep < state.totalLessonSteps) {
                        viewModel.handleIntent(AppIntent.NextLessonStep)
                    } else {
                        viewModel.handleIntent(AppIntent.StopRecordingAndEvaluate)
                        navController.navigate(AppRoutes.Feedback)
                    }
                }
            )
        }

        composable(AppRoutes.Feedback) {
            AiFeedbackOverlayScreen(
                state = AiFeedbackUiState(
                    score = state.feedbackScore,
                    tips = if (state.feedbackScore >= 80) {
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
                    viewModel.handleIntent(AppIntent.RetryLessonStep)
                    navController.popBackStack()
                },
                onContinue = {
                    viewModel.handleIntent(AppIntent.ContinueAfterFeedback)
                    navController.navigate(AppRoutes.Celebration)
                }
            )
        }

        composable(AppRoutes.Celebration) {
            LessonCompleteCelebrationScreen(
                state = CelebrationUiState(
                    starsEarned = if (state.feedbackScore >= 80) 3 else 2,
                    pointsEarned = if (state.feedbackScore >= 80) 100 else 60,
                    streakDays = state.childProfile?.streakDays ?: 0,
                    completedLessonLabel = stringResource(
                        R.string.phase2_lesson_title,
                        state.selectedVolume,
                        state.currentLessonNumber
                    )
                ),
                onContinue = {
                    viewModel.handleIntent(AppIntent.ContinueAfterCelebration)
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
                    childName = state.childProfile?.name ?: "Aisyah",
                    avatarColor = androidx.compose.ui.graphics.Color(state.childProfile?.avatarColorArgb ?: DeepTeal.value.toInt()),
                    currentVolume = state.selectedVolume,
                    currentLesson = state.currentLessonNumber,
                    streakDays = state.childProfile?.streakDays ?: 0,
                    points = state.childProfile?.points ?: 0,
                    completedVolumes = state.childProfile?.completedVolumes ?: 0
                ),
                onContinueLearning = {
                    viewModel.handleIntent(AppIntent.StartLesson(state.selectedVolume, state.currentLessonNumber))
                    navController.navigate(AppRoutes.Lesson)
                },
                onOpenVolumes = { navController.navigate(AppRoutes.VolumeSelection) },
                onOpenProgress = { navController.navigate(AppRoutes.VolumeSelection) }
            )
        }
    }
}

