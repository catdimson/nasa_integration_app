package ru.dkotik.nasaintegrationapp.viewmodel

import ru.dkotik.nasaintegrationapp.dto.marsphotos.MarsPhotosServerResponseData
import ru.dkotik.nasaintegrationapp.dto.pod.PODServerResponseData

sealed class AppState {

    data class SuccessPOD(val serverResponseData: PODServerResponseData) : AppState()

    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : AppState()

    data class Error(val error: Throwable) : AppState()

    data class Loading(val progress: Int?) : AppState()

}