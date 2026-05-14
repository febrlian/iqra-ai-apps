package com.iqraai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iqraai.data.local.entity.ChildProfileEntity
import com.iqraai.data.local.entity.IqraVolumeEntity
import com.iqraai.data.repository.IqraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppState(
    val childProfile: ChildProfileEntity? = null,
    val volumes: List<IqraVolumeEntity> = emptyList(),
    val selectedVolume: Int = 1,
    val currentLessonNumber: Int = 1,
    val currentLessonStep: Int = 1,
    val totalLessonSteps: Int = 5,
    val feedbackScore: Int = 0,
    val isRecording: Boolean = false,
    val isLoading: Boolean = true
)

sealed class AppIntent {
    data class UpdateChildProfile(val name: String, val avatarColorArgb: Int) : AppIntent()
    object CompleteFirstPreview : AppIntent()
    data class SelectVolume(val volumeNumber: Int) : AppIntent()
    data class StartLesson(val volumeNumber: Int, val lessonNumber: Int) : AppIntent()
    object ToggleRecording : AppIntent()
    object StopRecordingAndEvaluate : AppIntent()
    object NextLessonStep : AppIntent()
    object RetryLessonStep : AppIntent()
    object ContinueAfterFeedback : AppIntent()
    object ContinueAfterCelebration : AppIntent()
    object LoadInitialData : AppIntent()
}

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: IqraRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state: StateFlow<AppState> = _state.asStateFlow()

    init {
        handleIntent(AppIntent.LoadInitialData)
        viewModelScope.launch {
            repository.getChildProfile().collectLatest { profile ->
                _state.update { it.copy(childProfile = profile) }
            }
        }
        viewModelScope.launch {
            repository.getAllVolumes().collectLatest { volumes ->
                _state.update { it.copy(volumes = volumes) }
            }
        }
    }

    fun handleIntent(intent: AppIntent) {
        when (intent) {
            is AppIntent.LoadInitialData -> loadInitialData()
            is AppIntent.UpdateChildProfile -> updateChildProfile(intent.name, intent.avatarColorArgb)
            is AppIntent.CompleteFirstPreview -> completeFirstPreview()
            is AppIntent.SelectVolume -> selectVolume(intent.volumeNumber)
            is AppIntent.StartLesson -> startLesson(intent.volumeNumber, intent.lessonNumber)
            is AppIntent.ToggleRecording -> toggleRecording()
            is AppIntent.StopRecordingAndEvaluate -> stopRecordingAndEvaluate()
            is AppIntent.NextLessonStep -> nextLessonStep()
            is AppIntent.RetryLessonStep -> retryLessonStep()
            is AppIntent.ContinueAfterFeedback -> continueAfterFeedback()
            is AppIntent.ContinueAfterCelebration -> continueAfterCelebration()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            // Check if mock data is needed (if DB is empty)
            repository.getAllVolumes().collect { volumes ->
                 if(volumes.isEmpty()){
                     val mockVolumes = (1..6).map { volNum ->
                        IqraVolumeEntity(
                            volumeNumber = volNum,
                            title = "Volume $volNum",
                            totalLessons = 30,
                            isLocked = volNum > 1,
                            progress = if (volNum == 1) 0.1f else 0f
                        )
                     }
                     repository.insertVolumes(mockVolumes)
                 }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun updateChildProfile(name: String, avatarColorArgb: Int) {
        viewModelScope.launch {
            val newProfile = ChildProfileEntity(
                name = name,
                avatarColorArgb = avatarColorArgb,
                currentVolume = 1,
                currentLesson = 1,
                streakDays = 0,
                points = 0,
                completedVolumes = 0
            )
            repository.insertChildProfile(newProfile)
        }
    }

    private fun completeFirstPreview() {
        // Might update some boolean flag in DataStore or User profile later
    }

    private fun selectVolume(volumeNumber: Int) {
        _state.update { it.copy(selectedVolume = volumeNumber) }
    }

    private fun startLesson(volumeNumber: Int, lessonNumber: Int) {
        _state.update {
            it.copy(
                selectedVolume = volumeNumber,
                currentLessonNumber = lessonNumber,
                currentLessonStep = 1,
                isRecording = false
            )
        }
    }

    private fun toggleRecording() {
        _state.update { it.copy(isRecording = !it.isRecording) }
    }

    private fun stopRecordingAndEvaluate() {
        // Mock evaluation logic for now
         val currentScore = if (_state.value.selectedVolume <= 2) 90 else 72
         _state.update {
             it.copy(
                 isRecording = false,
                 feedbackScore = currentScore
             )
         }
    }

    private fun nextLessonStep() {
        _state.update {
            val nextStep = it.currentLessonStep + 1
            it.copy(
                currentLessonStep = nextStep,
                isRecording = false
            )
        }
    }

    private fun retryLessonStep() {
        _state.update {
            val prevStep = (it.currentLessonStep - 1).coerceAtLeast(1)
            it.copy(currentLessonStep = prevStep)
        }
    }

    private fun continueAfterFeedback() {
        viewModelScope.launch {
            val profile = _state.value.childProfile
            if (profile != null) {
                val earnedPoints = if (_state.value.feedbackScore >= 80) 100 else 60
                val updatedProfile = profile.copy(
                    points = profile.points + earnedPoints,
                    streakDays = profile.streakDays + 1
                )
                repository.updateChildProfile(updatedProfile)
            }
        }
    }

    private fun continueAfterCelebration() {
        viewModelScope.launch {
            val profile = _state.value.childProfile
            if (profile != null) {
                val currentVol = _state.value.selectedVolume
                val updatedProfile = if (currentVol > profile.completedVolumes) {
                    profile.copy(completedVolumes = currentVol)
                } else {
                    profile
                }
                repository.updateChildProfile(updatedProfile)
            }
        }
    }
}
