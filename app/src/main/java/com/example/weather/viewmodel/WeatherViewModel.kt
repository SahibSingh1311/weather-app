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

class WeatherViewModel(application: Application, private val weatherDao: WeatherDao) :
    AndroidViewModel(application) {

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
                            lon = weatherModel.coOrd?.lon,
                            lat = weatherModel.coOrd?.lat,
                            temp = weatherModel.main?.temp,
                            feelsLike = weatherModel.main?.feelsLike,
                            tempMin = weatherModel.main?.tempMin,
                            tempMax = weatherModel.main?.tempMax,
                            pressure = weatherModel.main?.pressure,
                            humidity = weatherModel.main?.humidity,
                            windSpeed = weatherModel.wind?.speed,
                            windDeg = weatherModel.wind?.deg,
                            windGust = weatherModel.wind?.gust,
                            cloudsAll = weatherModel.clouds?.all,
                            weatherMain = weatherModel.weather?.main,
                            weatherDescription = weatherModel.weather?.description,
                            weatherIcon = weatherModel.weather?.icon,
                            name = weatherModel.name,
                            country = weatherModel.sys?.country,
                            sunrise = weatherModel.sys?.sunrise,
                            sunset = weatherModel.sys?.sunset,
                            dt = weatherModel.dt
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
                coOrd = CoOrd(
                    lon = cachedWeather.lon.toString(),
                    lat = cachedWeather.lat.toString()
                ),
                main = Main(
                    temp = cachedWeather.temp,
                    feelsLike = cachedWeather.feelsLike,
                    tempMin = cachedWeather.tempMin,
                    tempMax = cachedWeather.tempMax,
                    pressure = cachedWeather.pressure,
                    humidity = cachedWeather.humidity
                ),
                wind = Wind(
                    speed = cachedWeather.windSpeed,
                    deg = cachedWeather.windDeg,
                    gust = cachedWeather.windGust
                ),
                clouds = Clouds(all = cachedWeather.cloudsAll),
                weather = Weather(
                    main = cachedWeather.weatherMain,
                    description = cachedWeather.weatherDescription,
                    icon = cachedWeather.weatherIcon
                ),
                name = cachedWeather.name,
                sys = Sys(
                    country = cachedWeather.country,
                    sunrise = cachedWeather.sunrise,
                    sunset = cachedWeather.sunset
                ),
                dt = cachedWeather.dt
            )
            _weatherResult.postValue(NetworkResponse.Success(weatherModel))
        } else {
            _weatherResult.postValue(NetworkResponse.Error("Failed to Load Data"))
        }
    }
}