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

//    weatherViewModel.getData(longitude = location.longitude, latitude = location.latitude)

//    val weatherResult = weatherViewModel.weatherResult.observeAsState()

//
//
//    Column(modifier = Modifier
//        .fillMaxWidth()
//        .padding(8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally){
//        Text(text = "Current Location")
//        Text(text = "${location.latitude} and ${location.longitude}")
//    }

//    when(val result = weatherResult.value){
//        is NetworkResponse.Error -> {
//            Text(text = result.message)
//        }
//        NetworkResponse.Loading -> {
//            Log.d("Live Data Observer", "WeatherScreen: Loading triggered.")
////            CircularProgressIndicator(modifier = Modifier.height(40.dp))
//        }
//        is NetworkResponse.Success -> {
//            Log.d("Live Data Observer", "WeatherScreen: Success")
//            Text(text = result.data.toString())
//        }
//        null -> {}
//        }
    val weatherResult by weatherViewModel.weatherResult.observeAsState(NetworkResponse.Loading)

    when (weatherResult) {
        is NetworkResponse.Loading -> {
            // Show a loading indicator
            CircularProgressIndicator()
        }
        is NetworkResponse.Success -> {
            val weatherData = (weatherResult as NetworkResponse.Success).data
            // Display weather data
            WeatherContent(weatherData)
        }
        is NetworkResponse.Error -> {
            val errorMessage = (weatherResult as NetworkResponse.Error).message
            // Show an error message
            Text(text = "Error: $errorMessage")
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



