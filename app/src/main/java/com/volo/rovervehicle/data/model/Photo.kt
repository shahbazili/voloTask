package com.volo.rovervehicle.data.model


import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Photo(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @Embedded(prefix = "camera_")
    @SerializedName("camera")
    val camera: Camera? = null,
    @SerializedName("earth_date")
    val earthDate: String = "",
    @SerializedName("img_src")
    val imgSrc: String = "",
    @Embedded(prefix = "rover_")
    @SerializedName("rover")
    val rover: Rover? = null,
    @SerializedName("sol")
    val sol: Int = 0,
    var page: Int = 1,
    var type: String = "",
    var cameraType: String = "",
) : Parcelable