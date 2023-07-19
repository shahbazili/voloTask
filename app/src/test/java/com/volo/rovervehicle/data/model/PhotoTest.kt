package com.volo.rovervehicle.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoTest {

    @Test
    fun testPhotoWithDefaultValues() {
        // Arrange
        val id = 1
        val photo = Photo(id = id)

        // Assert
        assertEquals(id, photo.id)
        assertEquals(null, photo.camera)
        assertEquals("", photo.earthDate)
        assertEquals("", photo.imgSrc)
        assertEquals(null, photo.rover)
        assertEquals(0, photo.sol)
        assertEquals(1, photo.page)
        assertEquals("", photo.type)
        assertEquals("", photo.cameraType)
    }

    @Test
    fun testPhotoWithUpdatedPageAndType() {
        // Arrange
        val id = 1
        val page = 3
        val type = "test"
        val photo = Photo(id = id, page = page, type = type)

        // Assert
        assertEquals(id, photo.id)
        assertEquals(null, photo.camera)
        assertEquals("", photo.earthDate)
        assertEquals("", photo.imgSrc)
        assertEquals(null, photo.rover)
        assertEquals(0, photo.sol)
        assertEquals(page, photo.page)
        assertEquals(type, photo.type)
        assertEquals("", photo.cameraType)
    }

    @Test
    fun testPhotoWithPopulatedCameraAndRover() {
        // Arrange
        val id = 1
        val camera = Camera("camera_model")
        val rover = Rover(roverName = "rover_name")
        val photo = Photo(id = id, camera = camera, rover = rover)

        // Assert
        assertEquals(id, photo.id)
        assertEquals(camera, photo.camera)
        assertEquals("", photo.earthDate)
        assertEquals("", photo.imgSrc)
        assertEquals(rover, photo.rover)
        assertEquals(0, photo.sol)
        assertEquals(1, photo.page)
        assertEquals("", photo.type)
        assertEquals("", photo.cameraType)
    }
}
