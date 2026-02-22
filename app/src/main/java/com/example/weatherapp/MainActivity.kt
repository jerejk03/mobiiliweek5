package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.UserInterface.WeatherScreen
import com.example.weatherapp.data.local.AppDatabase
import com.example.weatherapp.data.remote.RetrofitInstance
import com.example.weatherapp.data.repository.Repository
import com.example.weatherapp.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "weather_database"
        ).build()

        val repository = Repository(
            RetrofitInstance.api,
            database.weatherDao()
        )

        val viewModel = WeatherViewModel(repository)

        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                    WeatherScreen(viewModel)
                }
            }
        }
    }