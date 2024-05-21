package com.pomolistapp.di

import android.app.Application
import androidx.room.Room
import com.pomolistapp.feature_task.data.local.AppDataBase
import com.pomolistapp.feature_task.data.local.AppDataBase.Companion.DATABASE_NAME
import com.pomolistapp.feature_task.data.repository.PomodoroRepositoryImpl
import com.pomolistapp.feature_task.data.repository.RepositoryImpl
import com.pomolistapp.feature_task.domain.repository.PomodoroRepository
import com.pomolistapp.feature_task.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDataBase {
        return Room.databaseBuilder(
            context = app,
            klass = AppDataBase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: AppDataBase): TaskRepository {
        return RepositoryImpl(database.taskDAO)
    }

    @Provides
    @Singleton
    fun providePomodoroRepository(database: AppDataBase): PomodoroRepository {
        return PomodoroRepositoryImpl(database.pomodoroDAO)
    }
}