package com.iqraai.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.iqraai.data.local.entity.ChildProfileEntity
import com.iqraai.data.local.entity.IqraVolumeEntity
import com.iqraai.data.local.entity.LessonEntity
import com.iqraai.data.local.entity.RecitationAttemptEntity
import com.iqraai.data.local.entity.SRSItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IqraDao {

    // Child Profile
    @Query("SELECT * FROM child_profile LIMIT 1")
    fun getChildProfile(): Flow<ChildProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChildProfile(profile: ChildProfileEntity)

    @Update
    suspend fun updateChildProfile(profile: ChildProfileEntity)

    // Volumes
    @Query("SELECT * FROM iqra_volumes ORDER BY volumeNumber ASC")
    fun getAllVolumes(): Flow<List<IqraVolumeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVolumes(volumes: List<IqraVolumeEntity>)

    @Update
    suspend fun updateVolume(volume: IqraVolumeEntity)

    // Lessons
    @Query("SELECT * FROM lessons WHERE volumeNumber = :volumeNumber ORDER BY lessonNumber ASC")
    fun getLessonsForVolume(volumeNumber: Int): Flow<List<LessonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Update
    suspend fun updateLesson(lesson: LessonEntity)

    // Recitation Attempts
    @Insert
    suspend fun insertAttempt(attempt: RecitationAttemptEntity)

    @Query("SELECT * FROM recitation_attempts WHERE lessonId = :lessonId ORDER BY timestamp DESC")
    fun getAttemptsForLesson(lessonId: Int): Flow<List<RecitationAttemptEntity>>

    // SRS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSRSItem(srsItem: SRSItemEntity)

    @Query("SELECT * FROM srs_items WHERE nextReviewDate <= :currentDate ORDER BY nextReviewDate ASC")
    fun getDueSRSItems(currentDate: Long): Flow<List<SRSItemEntity>>
}
