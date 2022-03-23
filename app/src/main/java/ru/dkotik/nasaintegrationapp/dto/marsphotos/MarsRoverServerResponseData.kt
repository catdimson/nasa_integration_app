package ru.dkotik.nasaintegrationapp.dto.marsphotos

import com.google.gson.annotations.SerializedName

data class MarsRoverServerResponseData(

    val name: String?,
    @SerializedName("landing_date")
    val landingDate: String?,
    val status: String

)