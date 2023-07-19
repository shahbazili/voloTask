package com.volo.rovervehicle.data.datasource.remote

import com.volo.rovervehicle.util.constant.ApiUrls
import com.volo.rovervehicle.data.model.PhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(ApiUrls.GET_PHOTOS)
    suspend fun getPhotos(
        @Path("type") type: String,
        @Query("api_key") apiKey: String ,
        @Query("sol") sol: Int = 1000,
        @Query("page") page: Int = 1,
    ): Response<PhotosResponse>

}
