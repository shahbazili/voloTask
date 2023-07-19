package com.volo.rovervehicle.viewmodel



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.volo.rovervehicle.data.model.Photo
import com.volo.rovervehicle.data.datasource.repository.PhotoRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    var filter = MutableLiveData<String?>(null)

    fun getPhotos(type: String, cameraName: String?): Flow<PagingData<Photo>> {
        return if (cameraName == null) {
            photoRepository.getPhotos(type)
                .cachedIn(viewModelScope)
        } else {
            photoRepository.getPhotosByCamera(type, cameraName)
                .cachedIn(viewModelScope)
        }
    }

    suspend fun getCameraList(type: String): HashSet<String> = withContext(IO){
        return@withContext photoRepository.getCameraList(type).toHashSet()
    }

}