package com.example.weather.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.api.Clouds
import com.example.weather.api.CoOrd
import com.example.weather.api.Main
import com.example.weather.api.NetworkResponse
import com.example.weather.api.RetrofitInstance
import com.example.weather.api.Sys
import com.example.weather.api.Weather
import com.example.weather.api.WeatherModel
import com.example.weather.api.Wind
import com.example.weather.api.local.data.WeatherDao
import com.example.weather.api.local.data.WeatherEntity
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application, private val weatherDao: WeatherDao) : AndroidViewModel(application) {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(
        longitude: Double,
        latitude: Double
    ) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(latitude, longitude)
                if (response.isSuccessful) {
                    response.body()?.let { weatherModel ->
                        val weatherEntity = WeatherEntity(
                            lon = weatherModel.coOrd?.lon?.toDouble(),
                            lat = weatherModel.coOrd?.lat?.toDouble(),
                            temp = weatherModel.main?.temp?.toDoubleOrNull(),
                            feelsLike = weatherModel.main?.feelsLike?.toDoubleOrNull(),
                            tempMin = weatherModel.main?.tempMin?.toDoubleOrNull(),
                            tempMax = weatherModel.main?.tempMax?.toDoubleOrNull(),
                            pressure = weatherModel.main?.pressure?.toIntOrNull(),
                            humidity = weatherModel.main?.humidity?.toIntOrNull(),
                            windSpeed = weatherModel.wind?.speed?.toDoubleOrNull(),
                            windDeg = weatherModel.wind?.deg?.toIntOrNull(),
                            windGust = weatherModel.wind?.gust?.toDoubleOrNull(),
                            cloudsAll = weatherModel.clouds?.all?.toIntOrNull(),
                            weatherMain = weatherModel.weather.firstOrNull()?.main,
                            weatherDescription = weatherModel.weather.firstOrNull()?.description,
                            weatherIcon = weatherModel.weather.firstOrNull()?.icon,
                            name = weatherModel.name,
                            country = weatherModel.sys?.country,
                            sunrise = weatherModel.sys?.sunrise?.toLongOrNull(),
                            sunset = weatherModel.sys?.sunset?.toLongOrNull(),
                            dt = weatherModel.dt?.toLongOrNull()
                        )

                        weatherDao.insertWeather(weatherEntity)

                        // Assuming WeatherModel can be constructed from the response data
                        _weatherResult.value = NetworkResponse.Success(weatherModel)
                    } ?: run {
                        loadCachedData()
                    }
                } else {
                    loadCachedData()
                }
            } catch (e: Exception) {
                loadCachedData()
            }
        }
    }

    suspend fun loadCachedData() {
        // Fetch cached data from the database
        val cachedWeather = weatherDao.getWeatherById(1) // Assuming a single entry for simplicity
        if (cachedWeather != null) {
            // Convert the cached WeatherEntity back to WeatherModel
            val weatherModel = WeatherModel(
                coOrd = CoOrd(lon = cachedWeather.lon.toString(), lat = cachedWeather.lat.toString()),
                main = Main(
                    temp = cachedWeather.temp?.toString(),
                    feelsLike = cachedWeather.feelsLike?.toString(),
                    tempMin = cachedWeather.tempMin?.toString(),
                    tempMax = cachedWeather.tempMax?.toString(),
                    pressure = cachedWeather.pressure?.toString(),
                    humidity = cachedWeather.humidity?.toString()
                ),
                wind = Wind(
                    speed = cachedWeather.windSpeed?.toString(),
                    deg = cachedWeather.windDeg?.toString(),
                    gust = cachedWeather.windGust?.toString()
                ),
                clouds = Clouds(all = cachedWeather.cloudsAll?.toString()),
                weather = arrayListOf(
                    Weather(
                    main = cachedWeather.weatherMain,
                    description = cachedWeather.weatherDescription,
                    icon = cachedWeather.weatherIcon
                )
                ),
                name = cachedWeather.name,
                sys = Sys(
                    country = cachedWeather.country,
                    sunrise = cachedWeather.sunrise?.toString(),
                    sunset = cachedWeather.sunset?.toString()
                ),
                dt = cachedWeather.dt?.toString()
            )
            _weatherResult.postValue(NetworkResponse.Success(weatherModel))
        } else {
            _weatherResult.postValue(NetworkResponse.Error("Failed to Load Data"))
        }
    }
}