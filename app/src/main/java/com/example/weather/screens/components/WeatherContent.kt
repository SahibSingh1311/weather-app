package com.example.weather.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weather.R
import com.example.weather.api.WeatherModel
import java.time.LocalTime
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherContent(weatherData: WeatherModel) {
    // Customize this composable to display the weather data
    val currentTime = LocalTime.now().toString()
    Box(modifier = Modifier.fillMaxSize()) {
        if (currentTime < weatherData.sys?.sunset.toString()) {
            Image(
                painter = painterResource(id = R.drawable.clear),
                contentDescription = "Clear Background.",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.clear_night),
                contentDescription = "Clear Background.",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "My Location",
            modifier = Modifier.shadow(elevation = 20.dp),
            color = Color.White,
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = "${weatherData.main?.temp.toString()}°C",
            modifier = Modifier.shadow(elevation = 20.dp),
            color = Color.White,
            style = MaterialTheme.typography.displayMedium
        )
        val weatherIconRes = weatherData.weather?.id?.toInt()?.let { getWeatherIconResource(it) }
        if (weatherIconRes != null) {
            Image(
                painter = painterResource(id = weatherIconRes),
                contentDescription = weatherData.weather.description.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.clear_sun),  // Default image
                contentDescription = "No weather icon available",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp)
            )
        }
        Text(
            text = weatherData.weather?.description.toString()
                .replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                },
            modifier = Modifier.shadow(elevation = 20.dp),
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
        Row {
            Text(
                text = "H: ${weatherData.main?.tempMax}°C",
                color = Color.White,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.shadow(elevation = 20.dp)
            )

            Text(
                text = "L: ${weatherData.main?.tempMin}°C",
                color = Color.White,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .shadow(elevation = 20.dp)
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(4) { index ->
                WeatherCard(weatherData, index)
            }
        }
    }
}

fun getWeatherIconResource(weatherCode: Int): Int {
    return when (weatherCode) {
        in 200..232 -> R.drawable.thunderstorm
        in 300..321 -> R.drawable.shower_rain
        in 500..531 -> R.drawable.rain
        in 600..622 -> R.drawable.snow_fall
        in 701..781 -> R.drawable.mist
        800 -> R.drawable.clear_sun
        801 -> R.drawable.few_clouds
        in 802..804 -> R.drawable.few_clouds
        else -> R.drawable.clear_sun
    }
}
