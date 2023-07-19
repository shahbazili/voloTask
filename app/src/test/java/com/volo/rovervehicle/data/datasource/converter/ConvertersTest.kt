package com.volo.rovervehicle.data.datasource.converter


import com.volo.rovervehicle.data.datasource.database.converter.Converters
import com.volo.rovervehicle.data.model.Camera
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ConvertersTest {

    private lateinit var converters: Converters

    @Before
    fun setup() {
        converters = Converters()
    }

    @Test
    fun testFromTaskMemberList() {
        // Arrange
        val cameraList = listOf(
            Camera("camera1", 1, "", 0),
            Camera("camera2", 2, "", 0),
            Camera("camera3", 3, "", 0)
        )
        val expectedJson =
            "[{\"full_name\":\"camera1\",\"id\":1,\"name\":\"\",\"rover_id\":0}," +
                    "{\"full_name\":\"camera2\",\"id\":2,\"name\":\"\",\"rover_id\":0}," +
                    "{\"full_name\":\"camera3\",\"id\":3,\"name\":\"\",\"rover_id\":0}]"

        // Act
        val result = converters.fromGroupTaskMemberList(cameraList)

        // Assert
        assertEquals(expectedJson, result)
    }

    @Test
    fun testToTaskMemberList() {
        // Arrange
        val json =
            "[{\"full_name\":\"camera1\",\"id\":1,\"name\":\"\",\"rover_id\":0}," +
                    "{\"full_name\":\"camera2\",\"id\":2,\"name\":\"\",\"rover_id\":0}," +
                    "{\"full_name\":\"camera3\",\"id\":3,\"name\":\"\",\"rover_id\":0}]"
        val expectedCameraList = listOf(
            Camera("camera1", 1, "", 0),
            Camera("camera2", 2, "", 0),
            Camera("camera3", 3, "", 0)
        )

        // Act
        val result = converters.toGroupTaskMemberList(json)

        // Assert
        assertEquals(expectedCameraList, result)
    }
}
