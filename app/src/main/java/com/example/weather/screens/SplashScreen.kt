package com.example.weather.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weather.MainActivity
import com.example.weather.api.NetworkResponse
import com.example.weather.viewmodel.WeatherViewModelFactory
import com.example.weather.api.local.data.WeatherDatabase
import com.example.weather.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherDao = WeatherDatabase.getDatabase(application).weatherDao()

        weatherViewModel = ViewModelProvider(this, WeatherViewModelFactory(application,weatherDao))[WeatherViewModel::class.java]

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
                        || permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    fetchLocationAndWeather()
                }
                else -> {
                    Toast.makeText(this, "No Location Access", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }

        // Launch location permission request
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        // Optional: Add splash screen UI
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                Box(contentAlignment = Alignment.Center) {
                    // Add your splash screen logo or animation here
                    Text("Loading...", fontSize = 24.sp)
                }
            }
        }
    }

@SuppressLint("MissingPermission")
private fun fetchLocationAndWeather() {
    if (isLocationEnabled()) {
        val result = fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            CancellationTokenSource().token
        )
        result.addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val location = task.result
                weatherViewModel.getData(location.longitude, location.latitude)
            } else {
                Toast.makeText(this, "Failed to get location", Toast.LENGTH_LONG).show()
                lifecycleScope.launch {
                    weatherViewModel.loadCachedData()
                }
            }
        }

        // Observe the weather data and transition when it's ready
        weatherViewModel.weatherResult.observe(this) { result ->
            when (result) {
                is NetworkResponse.Success -> {
                    // Weather data is ready, transition to MainActivity
                    startMainActivity()
                }
                is NetworkResponse.Error -> {
                    Toast.makeText(this, "Failed to fetch weather data: ${result.message}", Toast.LENGTH_LONG).show()
                    finish()  // Handle error as needed
                }
                is NetworkResponse.Loading -> {
                    // Show loading if needed, although splash screen already implies loading
                }
            }
        }
    } else {
        Toast.makeText(this, "Location is disabled. Please enable it.", Toast.LENGTH_LONG).show()
        finish()
    }
}
private fun startMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()  // Close splash screen activity
}

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return try {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}