package com.iqraai.di

import android.app.Application
import androidx.room.Room
import com.iqraai.data.local.IqraDatabase
import com.iqraai.data.local.dao.IqraDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideIqraDatabase(app: Application): IqraDatabase {
        return Room.databaseBuilder(
            app,
            IqraDatabase::class.java,
            "iqra_db"
        )
        // .createFromAsset("database/iqra_db.db") // Might add later
        .build()
    }

    @Provides
    @Singleton
    fun provideIqraDao(db: IqraDatabase): IqraDao {
        return db.iqraDao
    }
}
