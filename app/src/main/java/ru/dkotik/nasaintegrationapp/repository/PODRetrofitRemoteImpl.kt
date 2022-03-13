package ru.dkotik.nasaintegrationapp.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PODRetrofitRemoteImpl {

    private val baseUrl = "https://api.nasa.gov/"

    fun getRetrofitImpl(): NasaRetrofitAPI {
        val podRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return podRetrofit.create(NasaRetrofitAPI::class.java)
    }
}