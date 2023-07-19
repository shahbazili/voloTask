package com.volo.rovervehicle.data.model


import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Rover(
    @SerializedName("cameras")
    val cameras: List<Camera>? = null,
    @SerializedName("id")
    val roverId: Int = 0,
    @SerializedName("landing_date")
    val landingDate: String = "",
    @SerializedName("launch_date")
    val launchDate: String = "",
    @SerializedName("max_date")
    val maxDate: String = "",
    @SerializedName("max_sol")
    val maxSol: Int = 0,
    @SerializedName("name")
    val roverName: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("total_photos")
    val totalPhotos: Int = 0
) : Parcelable