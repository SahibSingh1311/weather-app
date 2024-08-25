package com.example.weather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.api.NetworkResponse
import com.example.weather.api.RetrofitInstance
import com.example.weather.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val TAG = "Weather View Model"

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(
        longitude: Double,
        latitude: Double
    ) {
        Log.d(TAG, "getData: longitude: $longitude, latitude: $latitude")
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            Log.d("Coroutine", "Coroutine started")
            try {
                val response = weatherApi.getWeather(latitude, longitude)
                Log.d("Coroutine", "API call completed")
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("Coroutine", "Response body is not null, updating LiveData")
                        // Assuming WeatherModel can be constructed from the response data
                        _weatherResult.value = NetworkResponse.Success(it)
                        Log.d("weather result", "getData: ${_weatherResult.value}")
                    } ?: run {
                        Log.d("Coroutine", "Response body is null, setting Error state")
                        _weatherResult.value = NetworkResponse.Error("Failed to Load Data: Empty Response Body")
                    }
                } else {
                    Log.d("Coroutine", "Response not Successfull, setting Error state")
                    _weatherResult.value = NetworkResponse.Error("Failed to Load Data")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Failed to Load Data")
            }
            Log.d("Coroutine", "Coroutine Ended")
        }
    }
}