package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.model.WeatherEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE name = :name LIMIT 1")
    suspend fun getWeather(name: String): WeatherEntity?

    @Query("SELECT * FROM weather WHERE name = :name")
    fun getWeatherFlow(name: String): Flow<WeatherEntity?>

    @Query("SELECT * FROM weather ORDER BY timestamp DESC LIMIT 1")
    fun getLatestWeather(): Flow<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

}