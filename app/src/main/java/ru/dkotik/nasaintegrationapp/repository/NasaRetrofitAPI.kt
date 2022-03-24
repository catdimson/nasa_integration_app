package ru.dkotik.nasaintegrationapp.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.dkotik.nasaintegrationapp.dto.marsphotos.MarsPhotosServerResponseData
import ru.dkotik.nasaintegrationapp.dto.pod.PODServerResponseData

interface NasaRetrofitAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODServerResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String, @Query("date") date: String): Call<PODServerResponseData>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImageByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>

}