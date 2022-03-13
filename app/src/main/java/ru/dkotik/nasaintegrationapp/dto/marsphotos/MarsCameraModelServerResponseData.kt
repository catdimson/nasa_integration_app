package ru.dkotik.nasaintegrationapp.dto.marsphotos

import com.google.gson.annotations.SerializedName

data class MarsCameraModelServerResponseData(

    val name: String?,
    @SerializedName("full_name")
    val fullName: String?

)