package com.example.weather

import android.Manifest
import android.annotation.SuppressLint
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.weather.screens.WeatherScreen
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.viewmodel.WeatherViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource


class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val weatherViewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }
    private var hasFetchedData = false

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false)
                        || permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
                -> { Toast.makeText(this,"Location access granted",Toast.LENGTH_LONG).show()
//                    if (isLocationEnabled()){
//                        val result = fusedLocationProviderClient.getCurrentLocation(
//                            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
//                            CancellationTokenSource().token
//                        )
//                        result.addOnCompleteListener{
//                            enableEdgeToEdge()
//                            setContent {
//                                WeatherTheme {
//                                    Surface(modifier = Modifier.fillMaxSize()) {
//                                        WeatherScreen( weatherViewModel,it.result)
//                                    }
//                                }
//                            }
//                        }
//                    }
                    if (isLocationEnabled()) {
                        fetchLocationAndUpdateUI(weatherViewModel)
                    } else {
                        Toast.makeText(this, "Turn On Location", Toast.LENGTH_LONG).show()
                        createLocationRequest()
                    }
                }
                else -> {
                    Toast.makeText(this,"No Location Access",Toast.LENGTH_LONG).show()
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocationAndUpdateUI(weatherViewModel: WeatherViewModel) {
//
//        if (!hasFetchedData) { // Ensure getData() is called only once
//            val result = fusedLocationProviderClient.getCurrentLocation(
//                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
//                CancellationTokenSource().token
//            )
//
//            result.addOnCompleteListener {
//                val location = it.result
//                if (location != null) {
//                    // Fetch the weather data with the obtained location
//                    if (!hasFetchedData) {
//                        weatherViewModel.getData(location.longitude, location.latitude)
//                        hasFetchedData = true
//                    }
//                } else {
//                    Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
//                }
////                val location = it.result
////                if (location != null) {
////                    // Fetch the weather data with the obtained location
////                    weatherViewModel.getData(location.longitude, location.latitude)
////
////                    // Mark that data has been fetched
////                    hasFetchedData = true
////
////                    // Update UI
////                    enableEdgeToEdge()
////                    setContent {
////                        WeatherTheme {
////                            Surface(modifier = Modifier.fillMaxSize()) {
////                                WeatherScreen(weatherViewModel, location)
////                            }
////                        }
////                    }
////                } else {
////                    Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
////                }
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            CancellationTokenSource().token
        ).addOnCompleteListener { task ->
            val location = task.result
            if (location != null) {
                // Fetch the weather data with the obtained location
                weatherViewModel.getData(location.longitude, location.latitude)

                // Set the content using Jetpack Compose
                setContent {
                    WeatherTheme {
                        Surface(modifier = Modifier.fillMaxSize()) {
                            WeatherScreen(weatherViewModel, location)
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createLocationRequest() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000
        ).setMinUpdateIntervalMillis(5000).build()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {  }

        task.addOnFailureListener{ e ->
            if (e is ResolvableApiException){
                try{
                    e.startResolutionForResult(
                        this,
                        100
                    )
                }catch (sendEx: java.lang.Exception){}
            }
        }
    }

    private fun isLocationEnabled(): Boolean {

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        try{
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception){
            e.printStackTrace()
        }

        return false

    }

}




