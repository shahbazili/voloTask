package com.volo.rovervehicle.data.datasource.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.volo.rovervehicle.data.datasource.PhotoRemoteMediator
import com.volo.rovervehicle.data.datasource.remote.ApiService
import com.volo.rovervehicle.data.datasource.database.AppDatabase
import com.volo.rovervehicle.data.model.Photo
import com.volo.rovervehicle.util.constant.Constants.PAGE_SIZE
import com.volo.rovervehicle.util.constant.Constants.PRE_PAGE_SIZE
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class PhotoRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPhotos(type: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PRE_PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
            ),
            remoteMediator = PhotoRemoteMediator(apiService, db, type)
        ) { db.photoDao().getAllPhotos(type) }.flow
    }

    fun getPhotosByCamera(type: String, cameraName: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PRE_PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
            )
        ) { db.photoDao().getFilterPhoto(type, cameraName) }.flow
    }

    suspend fun getCameraList(type: String): HashSet<String> = withContext(IO){
        return@withContext db.photoDao().getCameraNames(type).toHashSet()
    }

}