package ru.dkotik.nasaintegrationapp.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.dkotik.nasaintegrationapp.dto.MarsPhotosServerResponseData
import ru.dkotik.nasaintegrationapp.dto.PODServerResponseData

const val BASE_URL = "https://api.nasa.gov/"

interface NasaRetrofitAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODServerResponseData>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImageByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>
}