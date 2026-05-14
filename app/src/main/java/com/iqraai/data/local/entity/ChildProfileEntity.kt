package com.iqraai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "child_profile")
data class ChildProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val avatarColorArgb: Int,
    val currentVolume: Int = 1,
    val currentLesson: Int = 1,
    val streakDays: Int = 0,
    val points: Int = 0,
    val completedVolumes: Int = 0
)
