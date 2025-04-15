package com.pupilmesh.assignment.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pupilmesh.assignment.data.local.AppDatabase
import com.pupilmesh.assignment.data.local.FaceDetectionRepositoryImpl
import com.pupilmesh.assignment.data.local.MangaDao
import com.pupilmesh.assignment.data.local.UserDao
import com.pupilmesh.assignment.data.local.UserRepositoryImpl
import com.pupilmesh.assignment.data.local.preferences.MangaOfflRepositoryImpl
import com.pupilmesh.assignment.data.remote.MangaRemoteDataSource
import com.pupilmesh.assignment.data.remote.MangaRemoteMediator
import com.pupilmesh.assignment.data.remote.api.MangaApiService
import com.pupilmesh.assignment.data.remote.repository.MangaRepositoryImpl
import com.pupilmesh.assignment.domain.repository.FaceDetectionRepository
import com.pupilmesh.assignment.domain.repository.MangaOfflRepository
import com.pupilmesh.assignment.domain.repository.MangaRepository
import com.pupilmesh.assignment.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE manga ADD COLUMN new_column INTEGER NOT NULL DEFAULT 0")
        }
    }
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pupilmesh_database"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun providesMangaDao(db: AppDatabase): MangaDao = db.mangaDao()

    @Provides
    fun provideMangaRepository(
        remoteDataSource: MangaRemoteDataSource,
        offlRepository: MangaOfflRepository
    ): MangaRepository {
        return MangaRepositoryImpl(
            remoteMediator = MangaRemoteMediator(remoteDataSource, offlRepository),
            offlRepository = offlRepository
        )
    }

    @Provides
    fun provideFaceDetectionRepository(
        @ApplicationContext context: Context
    ): FaceDetectionRepository {
        return FaceDetectionRepositoryImpl(context)
    }

    @Provides
    fun provideMangaOfflRepository(mangaDao: MangaDao): MangaOfflRepository {
        return MangaOfflRepositoryImpl(mangaDao)
    }

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    fun provideMangaRemoteDataSource(
        apiService: MangaApiService
    ): MangaRemoteDataSource {
        return MangaRemoteDataSource(apiService)
    }

}