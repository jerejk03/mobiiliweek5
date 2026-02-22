package com.example.weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity (
    @PrimaryKey
    val name: String,

    val description: String,
    val icon: String,
    val temp: Double,

    val timestamp: Long
)