package ru.binnyatoff.avitointerntest.screens.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.binnyatoff.avitointerntest.models.Model
import ru.binnyatoff.avitointerntest.retrofit.Api
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private  val api: Api) :ViewModel() {
    val temp = MutableLiveData<Double>()
    val humidity = MutableLiveData<Int>()
    val wind = MutableLiveData<Double>()
    val location = MutableLiveData<String>()

    init {
        getWeather()
    }

    private fun getWeather() {
        viewModelScope.launch {
            try {
                val response = api.getWeather()
                if (response.isSuccessful) {
                    val body = response.body()
                    temp.postValue(body?.main?.temp) // температура
                    humidity.postValue(body?.main?.humidity)//влажность
                    wind.postValue(body?.wind?.speed)//
                    location.postValue(body?.name)//название города
                    Log.e("TAG", response.body().toString())

                }
            } catch (e: Exception){
                Log.e("TAG", "Error connection")
            }
        }
    }
}