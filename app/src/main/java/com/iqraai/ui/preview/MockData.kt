package com.iqraai.ui.preview

import com.iqraai.ui.screens.learning.IqraVolumeUiModel

fun sampleVolumes(): List<IqraVolumeUiModel> = listOf(
    IqraVolumeUiModel(number = 1, progress = 1f, isLocked = false),
    IqraVolumeUiModel(number = 2, progress = 0.45f, isLocked = false),
    IqraVolumeUiModel(number = 3, progress = 0.1f, isLocked = false),
    IqraVolumeUiModel(number = 4, progress = 0f, isLocked = true),
    IqraVolumeUiModel(number = 5, progress = 0f, isLocked = true),
    IqraVolumeUiModel(number = 6, progress = 0f, isLocked = true)
)
