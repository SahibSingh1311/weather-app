package com.example.weather.screens

import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weather.api.NetworkResponse
import com.example.weather.api.WeatherModel
import com.example.weather.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel,
    location: Location
){

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

@Composable
fun WeatherContent(weatherData: WeatherModel) {
    // Customize this composable to display the weather data
    Column {
        Text(text = "Temperature: ${weatherData.main?.temp}°C")
        Text(text = "Name: ${weatherData.name}")
        // Add more weather details as needed
    }
}



