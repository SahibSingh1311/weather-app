package com.example.weather.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.api.local.data.WeatherDao

class WeatherViewModelFactory(
    private val application: Application,
    private val weatherDao: WeatherDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(application, weatherDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}