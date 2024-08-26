package com.example.weather.api

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("coord") val coOrd: CoOrd? = CoOrd(),
    @SerializedName("weather") val weather: Weather? = Weather(),
    @SerializedName("base") val base: String? = null,
    @SerializedName("main") val main: Main? = Main(),
    @SerializedName("visibility") val visibility: String? = null,
    @SerializedName("wind") val wind: Wind? = Wind(),
    @SerializedName("rain") val rain: Rain? = Rain(),
    @SerializedName("snow") val snow: Snow? = Snow(),
    @SerializedName("clouds") val clouds: Clouds? = Clouds(),
    @SerializedName("dt") val dt: String? = null,
    @SerializedName("sys") val sys: Sys? = Sys(),
    @SerializedName("timezone") val timezone: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("cod") val cod: String? = null
)

data class Clouds(
    @SerializedName("all") val all: String? = null
)

data class CoOrd(
    @SerializedName("lon") val lon: String? = null,
    @SerializedName("lat") val lat: String? = null
)

data class Main(
    @SerializedName("temp") val temp: String? = null,
    @SerializedName("feels_like") val feelsLike: String? = null,
    @SerializedName("temp_min") val tempMin: String? = null,
    @SerializedName("temp_max") val tempMax: String? = null,
    @SerializedName("pressure") val pressure: String? = null,
    @SerializedName("humidity") val humidity: String? = null,
    @SerializedName("sea_level") val seaLevel: String? = null,
    @SerializedName("grnd_level") val grndLevel: String? = null
)

data class Rain(
    @SerializedName("1h") val oneH: String? = null,
    @SerializedName("3h") val threeH: String? = null
)


data class Snow(
    @SerializedName("1h") val oneH: String? = null,
    @SerializedName("3h") val threeH: String? = null
)

data class Sys(
    @SerializedName("type") val type: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("sunrise") val sunrise: String? = null,
    @SerializedName("sunset") val sunset: String? = null
)

data class Weather(
    @SerializedName("id") val id: String? = null,
    @SerializedName("main") val main: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("icon") val icon: String? = null
)

data class Wind(
    @SerializedName("speed") val speed: String? = null,
    @SerializedName("deg") val deg: String? = null,
    @SerializedName("gust") val gust: String? = null
)
