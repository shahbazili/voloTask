package com.volo.rovervehicle.data.model.generic

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseResponse<T>(
    @SerializedName("success")
    var success: Boolean? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("data")
    var payLoad: T? = null
) : Serializable
