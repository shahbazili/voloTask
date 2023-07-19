package com.volo.rovervehicle.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.volo.rovervehicle.data.model.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(remoteKey: RemoteKeys)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("Select * From remote_key Where photo_id = :id")
    fun getRemoteKeyByPhotoID(id: Int): RemoteKeys?

    @Query("Select * From remote_key")
    fun getAllRemoteKeys(): List<RemoteKeys>

    @Query("Delete From remote_key")
    fun clearRemoteKeys()

    @Query("Select created_at From remote_key Order By created_at DESC LIMIT 1")
    fun getCreationTime(): Long?
}