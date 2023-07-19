package com.volo.rovervehicle.data.datasource.repository


import androidx.paging.PagingData
import com.volo.rovervehicle.data.datasource.database.AppDatabase
import com.volo.rovervehicle.data.datasource.database.dao.PhotoDao
import com.volo.rovervehicle.data.model.Photo
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class PhotoRepositoryTest {

    @Mock
    private lateinit var db: AppDatabase

    @Mock
    private lateinit var photoRepository: PhotoRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }


    @Test
    fun `getPhotos returns Flow of PagingData`() = runBlocking {
        // Mock the necessary dependencies
        val photoDao = mock(PhotoDao::class.java)
        Mockito.`when`(db.photoDao()).thenReturn(photoDao)

        // Mock the required data
        val expectedPagingData = createMockPagingData()
        val expectedResult = listOf(expectedPagingData)

        Mockito.`when`(photoRepository.getPhotos("test")).thenReturn(flowOf(expectedPagingData))

        // Call the function under test
        val flow = photoRepository.getPhotos("test")

        // Collect the flow to a list of items
        val collectedItems = flow.toList()

        // Assert that the list of items matches the expected photos
        assertEquals(expectedResult, collectedItems.toList())
    }


    @Test
    fun `getPhotosByCamera returns Flow of PagingData`() = runBlocking {
        // Mock the necessary dependencies
        val photoDao = mock(PhotoDao::class.java)
        Mockito.`when`(db.photoDao()).thenReturn(photoDao)

        // Mock the required data
        val cameraName = "camera1"
        val expectedPagingData = createMockPagingData()
        val expectedResult = listOf(expectedPagingData)

        Mockito.`when`(photoRepository.getPhotosByCamera("test",cameraName)).thenReturn(flowOf(expectedPagingData))
        // Call the function under test
        val flow = photoRepository.getPhotosByCamera("test", cameraName)

        // Collect the flow to a list of items
        val collectedItems = flow.toList()

        // Assert that the list of items matches the expected photos
        assertEquals(expectedResult, collectedItems)
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
