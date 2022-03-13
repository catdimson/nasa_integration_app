package ru.dkotik.nasaintegrationapp.dto

import com.google.gson.annotations.SerializedName

data class MarsServerResponseData(

    @SerializedName("img_src")
    val imgSrc: String?,
    @SerializedName("earth_date")
    val earth_date: String?

)
