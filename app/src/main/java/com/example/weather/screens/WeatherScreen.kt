package com.example.weather.screens

import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.weather.api.NetworkResponse
import com.example.weather.screens.components.WeatherContent
import com.example.weather.viewmodel.WeatherViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel,
    location: Location
) {

    val weatherResult by weatherViewModel.weatherResult.observeAsState(NetworkResponse.Loading)

    // Call getData only once
    LaunchedEffect(location) {
        weatherViewModel.getData(location.longitude, location.latitude)
    }

    when (weatherResult) {
        is NetworkResponse.Loading -> {
            CircularProgressIndicator()  // Show loading indicator
        }

        is NetworkResponse.Success -> {
            val weatherData = (weatherResult as NetworkResponse.Success).data
            WeatherContent(weatherData)  // Display the weather data
        }

        is NetworkResponse.Error -> {
            val errorMessage = (weatherResult as NetworkResponse.Error).message
            Text(text = "Error: $errorMessage")  // Show error message
        }
    }
}





