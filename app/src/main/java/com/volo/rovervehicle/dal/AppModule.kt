package com.volo.rovervehicle.dal

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.volo.rovervehicle.util.constant.Constants
import com.volo.rovervehicle.data.datasource.remote.ApiService
import com.volo.voloandroidtask.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)).client(createOkHttpClient())
            .build()

    @Provides
    fun createOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.SECONDS)
        httpClient.connectTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.SECONDS)
        httpClient.connectTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun authService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
