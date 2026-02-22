package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.model.WeatherEntity
import com.example.weatherapp.data.remote.WeatherApi

class Repository(
    private val api: WeatherApi,
    private val dao: WeatherDao
) {

    // Alla olevassa koodissa tarkistetaan, onko tietokantaa päivitetty 30 minuuttiin, ja jos ei
    // niin tiedot päivitetään hakemalla ajantasaiset tiedot API:sta.
    private val cacheTimeout = 30 * 60 * 1000 // 30 minutes in milliseconds

    suspend fun getWeather(name: String, apiKey: String, lang: String): WeatherEntity? {
        val cached = dao.getWeather(name)
        val isCacheValid = cached != null &&
                System.currentTimeMillis() - cached.timestamp < cacheTimeout

        return if(isCacheValid) {
            cached
        } else {
            val response = api.getWeather(name, apiKey, lang = lang)

            val entity = WeatherEntity(
                name = response.name,
                description = response.weather[0].description,
                icon = response.weather[0].icon,
                temp = response.main.temp,
                timestamp = System.currentTimeMillis()
            )
            dao.insertWeather(entity)
            entity
        }
    }

    fun getLatestWeather() = dao.getLatestWeather()
    fun observeWeather(name: String) = dao.getWeatherFlow(name)
}