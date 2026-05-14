package com.iqraai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "srs_items")
data class SRSItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val lessonId: Int,
    val intervalDays: Int,
    val easeFactor: Float,
    val nextReviewDate: Long
)
