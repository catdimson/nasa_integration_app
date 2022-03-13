package ru.dkotik.nasaintegrationapp.dto.pod

import com.google.gson.annotations.SerializedName

data class PODServerResponseData(
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String,
    @SerializedName("mediaType")
    val mediaType: String,
    @SerializedName("serviceVersion")
    val serviceVersion: String,
    val title: String,
    val url: String
)

