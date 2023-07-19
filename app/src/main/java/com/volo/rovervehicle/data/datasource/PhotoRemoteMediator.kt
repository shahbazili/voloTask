package com.volo.rovervehicle.data.datasource


import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.volo.rovervehicle.data.datasource.remote.ApiService
import com.volo.rovervehicle.data.datasource.database.AppDatabase
import com.volo.rovervehicle.data.model.Photo
import com.volo.rovervehicle.data.model.RemoteKeys
import com.volo.rovervehicle.util.constant.Constants.FIRST_PAGE
import com.volo.voloandroidtask.BuildConfig
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalPagingApi::class)
class PhotoRemoteMediator(
    private val apiService: ApiService,
    private val db: AppDatabase,
    private val type: String
) : RemoteMediator<Int, Photo>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Photo>): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: FIRST_PAGE
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey

                    prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey

                    if (nextKey == null && remoteKeys?.type == type) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    } else nextKey ?: FIRST_PAGE
                }
            }

            val items = apiService.getPhotos(type, apiKey = BuildConfig.API_KEY ,page = page)
            val photos = items.body()?.photos!!
            val endOfPaginationReached = photos.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.getRemoteKeysDao().clearRemoteKeys()
                    db.photoDao().clearAllPhotos()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = photos.map {
                    RemoteKeys(
                        photoID = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey,
                        type = type
                    )
                }

                db.getRemoteKeysDao().insertAll(remoteKeys)
                db.photoDao().insertPhotos(photos.onEachIndexed { _, photo ->
                    photo.page = page;photo.type = type;photo.cameraType = photo.camera?.cameraName!!
                })
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Photo>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.getRemoteKeysDao().getRemoteKeyByPhotoID(id)
            }
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Photo>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { photo ->
            db.getRemoteKeysDao().getRemoteKeyByPhotoID(photo.id)
        }
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Photo>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { photo ->
            db.getRemoteKeysDao().getRemoteKeyByPhotoID(photo.id)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (db.getRemoteKeysDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
}