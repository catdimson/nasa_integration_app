package ru.dkotik.nasaintegrationapp.dto.marsphotos

import com.google.gson.annotations.SerializedName

data class MarsPhotosServerResponseData(

    @SerializedName("photos")
    val photos: ArrayList<MarsServerResponseData>

)
