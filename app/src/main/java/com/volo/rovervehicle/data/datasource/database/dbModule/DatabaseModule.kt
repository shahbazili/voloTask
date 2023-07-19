package com.volo.rovervehicle.data.datasource.database.dbModule

import android.content.Context
import androidx.room.Room
import com.volo.rovervehicle.data.datasource.database.AppDatabase
import com.volo.rovervehicle.data.datasource.database.dao.RemoteKeysDao
import com.volo.rovervehicle.data.datasource.database.dao.PhotoDao
import com.volo.voloandroidtask.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun providePhotoDao(appDatabase: AppDatabase): PhotoDao {
        return appDatabase.photoDao()
    }

    @Singleton
    @Provides
    fun provideRemoteKeysDao(appDatabase: AppDatabase): RemoteKeysDao =
        appDatabase.getRemoteKeysDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            appContext.getString(R.string.app_name)
        ).allowMainThreadQueries().build()
    }
}