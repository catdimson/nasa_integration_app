package ru.dkotik.nasaintegrationapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.dkotik.nasaintegrationapp.BuildConfig.NASA_API_KEY
import ru.dkotik.nasaintegrationapp.repository.PODRetrofitRemoteImpl
import ru.dkotik.nasaintegrationapp.repository.PODServerResponseData

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayDataState> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitRemoteImpl = PODRetrofitRemoteImpl()
): ViewModel() {

    fun getData(): LiveData<PictureOfTheDayDataState> {
        return liveDataForViewToObserve
    }

    fun sendServerRequest() {
        liveDataForViewToObserve.value = PictureOfTheDayDataState.Loading(0)
        val apiKey: String = NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = PictureOfTheDayDataState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(callback)
        }
    }

    fun sendServerRequest(date: String) {
        liveDataForViewToObserve.postValue(PictureOfTheDayDataState.Loading(0))
        val apiKey: String = NASA_API_KEY

        if (apiKey.isBlank()) {
            PictureOfTheDayDataState.Error(Throwable("You need API key"))

        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, date).enqueue(callback)
        }
    }

    private val callback = object : Callback<PODServerResponseData> {
        override fun onResponse(
            call: Call<PODServerResponseData>,
            response: Response<PODServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    liveDataForViewToObserve.postValue(PictureOfTheDayDataState.Success(it))
                }
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserve.value = PictureOfTheDayDataState.Error(Throwable("Unidentified error"))
                } else {
                    liveDataForViewToObserve.value = PictureOfTheDayDataState.Error(Throwable(message))
                }
            }
        }
        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            liveDataForViewToObserve.value = PictureOfTheDayDataState.Error(t)
        }
    }
}
