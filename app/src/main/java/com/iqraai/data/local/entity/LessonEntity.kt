package com.iqraai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val volumeNumber: Int,
    val lessonNumber: Int,
    val arabicText: String,
    val transliteration: String,
    val totalSteps: Int,
    val isCompleted: Boolean = false,
    val score: Int = 0
)
