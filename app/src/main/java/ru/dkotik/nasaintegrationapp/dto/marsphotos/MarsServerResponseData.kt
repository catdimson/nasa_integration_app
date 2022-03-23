package ru.dkotik.nasaintegrationapp.dto.marsphotos

import com.google.gson.annotations.SerializedName

data class MarsServerResponseData(

    @SerializedName("img_src")
    val imgSrc: String?,
    @SerializedName("earth_date")
    val earthDate: String?,
    @SerializedName("camera")
    val camera: MarsCameraModelServerResponseData?,
    @SerializedName("rover")
    val rover: MarsRoverServerResponseData?

)
