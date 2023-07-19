package com.volo.rovervehicle.data.datasource.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.volo.rovervehicle.data.datasource.database.dao.RemoteKeysDao
import com.volo.rovervehicle.data.datasource.database.dao.PhotoDao
import com.volo.rovervehicle.data.model.Photo
import com.volo.rovervehicle.data.model.RemoteKeys
import com.volo.rovervehicle.data.datasource.database.converter.Converters

@Database(entities = [Photo::class, RemoteKeys::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}