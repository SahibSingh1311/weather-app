package com.example.weather.api.local.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "weather")
@TypeConverters(Converters::class)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "lon")val lon: String?,
    @ColumnInfo(name = "lat")val lat: String?,
    @ColumnInfo(name = "temp")val temp: String?,
    @ColumnInfo(name = "feels_like")val feelsLike: String?,
    @ColumnInfo(name = "temp_min")val tempMin: String?,
    @ColumnInfo(name = "temp_max")val tempMax: String?,
    @ColumnInfo(name = "pressure")val pressure: String?,
    @ColumnInfo(name = "humidity")val humidity: String?,
    @ColumnInfo(name = "speed")val windSpeed: String?,
    @ColumnInfo(name = "deg")val windDeg: String?,
    @ColumnInfo(name = "gust")val windGust: String?,
    @ColumnInfo(name = "all")val cloudsAll: String?,
    @ColumnInfo(name = "main")val weatherMain: String?,
    @ColumnInfo(name = "description")val weatherDescription: String?,
    @ColumnInfo(name = "icon")val weatherIcon: String?,
    @ColumnInfo(name = "name")val name: String?,
    @ColumnInfo(name = "country")val country: String?,
    @ColumnInfo(name = "sunrise")val sunrise: String?,
    @ColumnInfo(name = "sunset")val sunset: String?,
    @ColumnInfo(name = "dt")val dt: String?
)
