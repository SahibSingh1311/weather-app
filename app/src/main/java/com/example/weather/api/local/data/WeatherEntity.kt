package com.example.weather.api.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "weather")
@TypeConverters(Converters::class)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lon: Double?,
    val lat: Double?,
    val temp: Double?,
    val feelsLike: Double?,
    val tempMin: Double?,
    val tempMax: Double?,
    val pressure: Int?,
    val humidity: Int?,
    val windSpeed: Double?,
    val windDeg: Int?,
    val windGust: Double?,
    val cloudsAll: Int?,
    val weatherMain: String?,
    val weatherDescription: String?,
    val weatherIcon: String?,
    val name: String?,
    val country: String?,
    val sunrise: Long?,
    val sunset: Long?,
    val dt: Long?
)
