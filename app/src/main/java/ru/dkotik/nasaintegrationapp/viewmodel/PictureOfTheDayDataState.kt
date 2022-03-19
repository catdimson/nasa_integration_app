package ru.dkotik.nasaintegrationapp.viewmodel

import ru.dkotik.nasaintegrationapp.repository.PODServerResponseData

sealed class PictureOfTheDayDataState {

    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayDataState()

    data class Error(val error: Throwable) : PictureOfTheDayDataState()

    data class Loading(val progress: Int?) : PictureOfTheDayDataState()

}