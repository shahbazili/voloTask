package com.volo.rovervehicle.data.datasource.database.dao


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.volo.rovervehicle.data.model.Photo

@Dao
interface PhotoDao {

    @Query("Select * From photo  WHERE type = :type Order By id ASC")
    fun getAllPhotos(type: String): PagingSource<Int, Photo>

    @Query("SELECT * FROM photo")
    suspend fun getAllPhotos(): List<Photo>

    @Query("SELECT * FROM photo WHERE type like :type and cameraType LIKE :filter ORDER BY id ASC")
    fun getFilterPhoto(type: String, filter: String): PagingSource<Int, Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<Photo>)

    @Query("Delete From Photo")
   suspend fun clearAllPhotos()

    @Query("SELECT cameraType FROM photo WHERE type like :type")
    suspend fun getCameraNames(type: String): List<String>
}