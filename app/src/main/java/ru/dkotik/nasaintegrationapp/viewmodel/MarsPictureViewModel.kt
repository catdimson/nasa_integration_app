package ru.dkotik.nasaintegrationapp.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.dkotik.nasaintegrationapp.BuildConfig
import ru.dkotik.nasaintegrationapp.dto.marsphotos.MarsPhotosServerResponseData
import ru.dkotik.nasaintegrationapp.repository.NasaRetrofitAPIImpl
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MarsPictureViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: NasaRetrofitAPIImpl = NasaRetrofitAPIImpl(),
) : ViewModel() {

    fun getMarsPicture() {
        liveDataToObserve.postValue(AppState.Loading(null))
        val earthDate = getDayBeforeYesterday()
        retrofitImpl.getRetrofitImpl().getMarsImageByDate(earthDate, BuildConfig.NASA_API_KEY).enqueue(marsCallback)
    }

    fun getData(): LiveData<AppState> {
        return liveDataToObserve
    }

    @SuppressLint("SimpleDateFormat")
    fun getDayBeforeYesterday(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(2)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, -2)
            return s.format(cal.time)
        }
    }

    private val marsCallback = object : Callback<MarsPhotosServerResponseData> {

        override fun onResponse(
            call: Call<MarsPhotosServerResponseData>,
            response: Response<MarsPhotosServerResponseData>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(AppState.SuccessMars(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(AppState.Error(Throwable(UNKNOWN_ERROR)))
                } else {
                    liveDataToObserve.postValue(AppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(t))
        }
    }

    companion object {
        private const val API_ERROR = "You need API Key"
        private const val UNKNOWN_ERROR = "Unidentified error"
    }

}