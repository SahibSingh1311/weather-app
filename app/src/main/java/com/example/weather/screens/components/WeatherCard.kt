package com.example.weather.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weather.R
import com.example.weather.api.WeatherModel


@Composable
fun WeatherCard(weatherData: WeatherModel, index: Int) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.3f) // Adjust the aspect ratio as needed
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.Gray
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
        ) {
            when (index) {
                0 -> {
                    CardTitle(painterResource = R.drawable.wind, title = "WIND")
                }

                1 -> {
                    CardTitle(
                        painterResource = R.drawable.temprature_feels_like,
                        title = "FEELS LIKE"
                    )
                }

                2 -> {
                    CardTitle(painterResource = R.drawable.precipitation, title = "PRECIPITATION")
                }

                3 -> {
                    CardTitle(painterResource = R.drawable.visibility, title = "VISIBILITY")
                }
            }
        }
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            CardInfo(index, weatherData)
        }
    }
}