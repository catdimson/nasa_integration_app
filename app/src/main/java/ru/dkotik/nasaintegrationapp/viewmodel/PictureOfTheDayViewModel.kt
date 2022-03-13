package ru.dkotik.nasaintegrationapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.dkotik.nasaintegrationapp.BuildConfig.NASA_API_KEY
import ru.dkotik.nasaintegrationapp.repository.PODRetrofitRemoteImpl
import ru.dkotik.nasaintegrationapp.dto.pod.PODServerResponseData

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitRemoteImpl = PODRetrofitRemoteImpl()
): ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    fun sendServerRequest() {
        liveDataForViewToObserve.postValue(AppState.Loading(null))
        val apiKey: String = NASA_API_KEY

        if (apiKey.isBlank()) {
            AppState.Error(Throwable("You need API key"))

        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(
                object : Callback<PODServerResponseData> {
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
            })
        }
    }
}
