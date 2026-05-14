package com.iqraai.data.repository

import com.iqraai.data.local.dao.IqraDao
import com.iqraai.data.local.entity.ChildProfileEntity
import com.iqraai.data.local.entity.IqraVolumeEntity
import com.iqraai.data.local.entity.LessonEntity
import com.iqraai.data.local.entity.RecitationAttemptEntity
import com.iqraai.data.local.entity.SRSItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IqraRepository @Inject constructor(
    private val dao: IqraDao
) {
    // Child Profile
    fun getChildProfile(): Flow<ChildProfileEntity?> = dao.getChildProfile()

    suspend fun insertChildProfile(profile: ChildProfileEntity) {
        dao.insertChildProfile(profile)
    }

    suspend fun updateChildProfile(profile: ChildProfileEntity) {
        dao.updateChildProfile(profile)
    }

    // Volumes
    fun getAllVolumes(): Flow<List<IqraVolumeEntity>> = dao.getAllVolumes()

    suspend fun insertVolumes(volumes: List<IqraVolumeEntity>) {
        dao.insertVolumes(volumes)
    }

    suspend fun updateVolume(volume: IqraVolumeEntity) {
        dao.updateVolume(volume)
    }

    // Lessons
    fun getLessonsForVolume(volumeNumber: Int): Flow<List<LessonEntity>> = dao.getLessonsForVolume(volumeNumber)

    suspend fun insertLessons(lessons: List<LessonEntity>) {
        dao.insertLessons(lessons)
    }

    suspend fun updateLesson(lesson: LessonEntity) {
        dao.updateLesson(lesson)
    }

    // Recitation Attempts
    suspend fun insertAttempt(attempt: RecitationAttemptEntity) {
        dao.insertAttempt(attempt)
    }

    fun getAttemptsForLesson(lessonId: Int): Flow<List<RecitationAttemptEntity>> = dao.getAttemptsForLesson(lessonId)

    // SRS
    suspend fun insertSRSItem(srsItem: SRSItemEntity) {
        dao.insertSRSItem(srsItem)
    }

    fun getDueSRSItems(currentDate: Long): Flow<List<SRSItemEntity>> = dao.getDueSRSItems(currentDate)
}
