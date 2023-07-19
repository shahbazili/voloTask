package com.volo.rovervehicle.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Entity
@Parcelize
data class Camera(
    @SerializedName("full_name")
    val fullName: String="",
    @SerializedName("id")
    val cameraId: Int=0,
    @SerializedName("name")
    val cameraName: String="",
    @SerializedName("rover_id")
    val roverId: Int=0
):Parcelable