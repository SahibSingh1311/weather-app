package com.example.weather

import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.weather.api.local.data.WeatherDao
import com.example.weather.api.local.data.WeatherDatabase
import com.example.weather.screens.WeatherScreen
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.viewmodel.WeatherViewModel
import com.example.weather.viewmodel.WeatherViewModelFactory


class MainActivity : ComponentActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var weatherDao: WeatherDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the location data from the intent
        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")

        weatherDao = WeatherDatabase.getDatabase(application).weatherDao()
        weatherViewModel = ViewModelProvider(this, WeatherViewModelFactory(application,weatherDao))[WeatherViewModel::class.java]

        setContent {
            WeatherTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WeatherScreen(weatherViewModel, location = Location("").apply {
                        this.latitude = latitude!!.toDouble()
                        this.longitude = longitude!!.toDouble()
                    })
                }
            }
        }
    }

}




