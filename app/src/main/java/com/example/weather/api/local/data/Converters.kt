package com.example.weather.api.local.data

import androidx.room.TypeConverter
import com.example.weather.api.Clouds
import com.example.weather.api.CoOrd
import com.example.weather.api.Main
import com.example.weather.api.Rain
import com.example.weather.api.Snow
import com.example.weather.api.Sys
import com.example.weather.api.Weather
import com.example.weather.api.Wind
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    // Convert a list of Weather objects to a JSON string
    @TypeConverter
    fun fromWeatherList(value: List<Weather>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {}.type
        return gson.toJson(value, type)
    }

    // Convert a JSON string back to a list of Weather objects
    @TypeConverter
    fun toWeatherList(value: String?): List<Weather>? {
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(value, type)
    }

    // Convert CoOrd object to JSON string
    @TypeConverter
    fun fromCoOrd(value: CoOrd?): String? {
        return Gson().toJson(value)
    }

    // Convert JSON string back to CoOrd object
    @TypeConverter
    fun toCoOrd(value: String?): CoOrd? {
        return Gson().fromJson(value, CoOrd::class.java)
    }

    // Convert Main object to JSON string
    @TypeConverter
    fun fromMain(value: Main?): String? {
        return Gson().toJson(value)
    }

    // Convert JSON string back to Main object
    @TypeConverter
    fun toMain(value: String?): Main? {
        return Gson().fromJson(value, Main::class.java)
    }

    // Convert Clouds object to JSON string
    @TypeConverter
    fun fromClouds(value: Clouds?): String? {
        return Gson().toJson(value)
    }

    // Convert JSON string back to Clouds object
    @TypeConverter
    fun toClouds(value: String?): Clouds? {
        return Gson().fromJson(value, Clouds::class.java)
    }

    // Convert Wind object to JSON string
    @TypeConverter
    fun fromWind(value: Wind?): String? {
        return Gson().toJson(value)
    }

    // Convert JSON string back to Wind object
    @TypeConverter
    fun toWind(value: String?): Wind? {
        return Gson().fromJson(value, Wind::class.java)
    }

    // Convert Rain object to JSON string
    @TypeConverter
    fun fromRain(value: Rain?): String? {
        return Gson().toJson(value)
    }

    // Convert JSON string back to Rain object
    @TypeConverter
    fun toRain(value: String?): Rain? {
        return Gson().fromJson(value, Rain::class.java)
    }

    // Convert Snow object to JSON string
    @TypeConverter
    fun fromSnow(value: Snow?): String? {
        return Gson().toJson(value)
    }

    // Convert JSON string back to Snow object
    @TypeConverter
    fun toSnow(value: String?): Snow? {
        return Gson().fromJson(value, Snow::class.java)
    }

    // Convert Sys object to JSON string
    @TypeConverter
    fun fromSys(value: Sys?): String? {
        return Gson().toJson(value)
    }

    // Convert JSON string back to Sys object
    @TypeConverter
    fun toSys(value: String?): Sys? {
        return Gson().fromJson(value, Sys::class.java)
    }
}