package com.volo.rovervehicle.data.datasource.database

import com.volo.rovervehicle.data.datasource.database.dao.PhotoDao
import com.volo.rovervehicle.data.datasource.database.dao.RemoteKeysDao
import com.volo.rovervehicle.data.model.Photo
import com.volo.rovervehicle.data.model.RemoteKeys
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
@RunWith(JUnit4::class)
class AppDatabaseTest {

    @Mock
    private lateinit var photoDao: PhotoDao

    @Mock
    private lateinit var remoteKeysDao: RemoteKeysDao

    @Mock
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(appDatabase.photoDao()).thenReturn(photoDao)
        `when`(appDatabase.getRemoteKeysDao()).thenReturn(remoteKeysDao)
    }

    @Test
    fun testInsertAndRetrievePhotos() = runBlocking {
        // Arrange
        val photos = listOf(
            Photo(1, imgSrc = "photo1.jpg"),
            Photo(2, imgSrc = "photo2.jpg"),
            Photo(3, imgSrc = "photo3.jpg")
        )
        `when`(photoDao.getAllPhotos()).thenReturn(photos)

        // Act
        val retrievedPhotos = photoDao.getAllPhotos()

        // Assert
        assertEquals(photos, retrievedPhotos)
    }

    @Test
    fun testInsertAndRetrieveRemoteKeys() = runBlocking {
        // Arrange
        val remoteKey = RemoteKeys(1, null, 1, 2, 0,"example")
        `when`(remoteKeysDao.getRemoteKeyByPhotoID(remoteKey.photoID)).thenReturn(remoteKey)

        // Act
        val retrievedRemoteKey = remoteKeysDao.getRemoteKeyByPhotoID(remoteKey.photoID)

        // Assert
        assertEquals(remoteKey, retrievedRemoteKey)
    }
}
