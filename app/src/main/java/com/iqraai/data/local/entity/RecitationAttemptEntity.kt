package com.iqraai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recitation_attempts")
data class RecitationAttemptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val lessonId: Int,
    val score: Int,
    val tips: String, // comma separated for now
    val timestamp: Long
)
