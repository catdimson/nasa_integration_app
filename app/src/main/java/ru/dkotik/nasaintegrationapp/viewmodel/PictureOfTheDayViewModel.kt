package ru.dkotik.nasaintegrationapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.dkotik.nasaintegrationapp.BuildConfig.NASA_API_KEY
import ru.dkotik.nasaintegrationapp.dto.pod.PODServerResponseData
import ru.dkotik.nasaintegrationapp.repository.NasaRetrofitAPIImpl

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: NasaRetrofitAPIImpl = NasaRetrofitAPIImpl()
): ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    fun sendServerRequest() {
        liveDataForViewToObserve.value = AppState.Loading(0)
        val apiKey: String = NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = AppState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(callback)
        }
    }

    fun sendServerRequest(date: String) {
        liveDataForViewToObserve.postValue(AppState.Loading(0))
        val apiKey: String = NASA_API_KEY

        if (apiKey.isBlank()) {
            AppState.Error(Throwable("You need API key"))

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
                    liveDataForViewToObserve.postValue(AppState.SuccessPOD(it))
                }
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserve.value = AppState.Error(Throwable("Unidentified error"))
                } else {
                    liveDataForViewToObserve.value = AppState.Error(Throwable(message))
                }
            }
        }
        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            liveDataForViewToObserve.value = AppState.Error(t)
        }
    }
}
