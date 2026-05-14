package com.iqraai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iqraai.data.local.dao.IqraDao
import com.iqraai.data.local.entity.ChildProfileEntity
import com.iqraai.data.local.entity.IqraVolumeEntity
import com.iqraai.data.local.entity.LessonEntity
import com.iqraai.data.local.entity.RecitationAttemptEntity
import com.iqraai.data.local.entity.SRSItemEntity

@Database(
    entities = [
        ChildProfileEntity::class,
        IqraVolumeEntity::class,
        LessonEntity::class,
        RecitationAttemptEntity::class,
        SRSItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class IqraDatabase : RoomDatabase() {
    abstract val iqraDao: IqraDao
}
