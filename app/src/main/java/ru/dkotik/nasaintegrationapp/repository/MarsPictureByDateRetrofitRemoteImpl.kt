package ru.dkotik.nasaintegrationapp.repository

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dkotik.nasaintegrationapp.dto.MarsPhotosServerResponseData


class MarsPictureByDateRetrofitRemoteImpl {

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(NasaRetrofitAPI::class.java)
    }

    fun getMarsPictureByDate(earth_date: String, apiKey: String, marsCallbackByDate: Callback<MarsPhotosServerResponseData>) {
        api.getMarsImageByDate(earth_date, apiKey).enqueue(marsCallbackByDate)
    }

}