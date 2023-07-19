package com.volo.rovervehicle.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.volo.rovervehicle.data.datasource.repository.PhotoRepository
import com.volo.rovervehicle.data.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PhotoViewModelTest {

    @Mock
    private lateinit var photoRepository: PhotoRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getPhotosWithoutCameraName() = runBlocking {
        val type = "test"
        val expectedPagingData = createMockPagingData()
        val expectedResult = listOf(expectedPagingData)

        `when`(photoRepository.getPhotos(type)).thenReturn(flowOf(expectedPagingData))

        val resultFlow: Flow<PagingData<Photo>> = photoRepository.getPhotos(type)

        val result = mutableListOf<PagingData<Photo>>()
        resultFlow.collect {
            result.add(it)
        }

        assertEquals(expectedResult, result)
    }

    @Test
    fun getPhotosWithCameraName() = runBlocking {
        val type = "test"
        val cameraName = "testCamera"
        val expectedPagingData = createMockPagingData()
        val expectedResult = listOf(expectedPagingData)

        `when`(photoRepository.getPhotosByCamera(type, cameraName)).thenReturn(
            flowOf(
                expectedPagingData
            )
        )

        val resultFlow: Flow<PagingData<Photo>> =
            photoRepository.getPhotosByCamera(type, cameraName)

        val result = mutableListOf<PagingData<Photo>>()
        resultFlow.collect {
            result.add(it)
        }

        assertEquals(expectedResult, result)
    }

    @Test
    fun testCameraList() = runBlocking {
        // Arrange
        val type = "test"
        val cameraList = listOf("camera1", "camera2", "camera3")
        `when`(photoRepository.getCameraList(type)).thenReturn(cameraList.toHashSet())

        // Act
        val result = photoRepository.getCameraList(type)

        // Assert
        assertEquals(cameraList.toHashSet(), result)
    }

    private fun createMockPagingData(): PagingData<Photo> {
        // Create a mock instance of PagingData<Photo> for testing
        return PagingData.from(
            listOf(
                Photo(1, imgSrc = "photo1.jpg"),
                Photo(2, imgSrc = "photo2.jpg"),
                Photo(3, imgSrc = "photo3.jpg")
            )
        )
    }
}

