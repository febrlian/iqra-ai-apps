package com.iqraai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "iqra_volumes")
data class IqraVolumeEntity(
    @PrimaryKey
    val volumeNumber: Int,
    val title: String,
    val totalLessons: Int,
    val isLocked: Boolean = true,
    val progress: Float = 0f
)
