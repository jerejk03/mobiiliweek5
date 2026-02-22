package com.example.weatherapp.UserInterface

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    var city by remember { mutableStateOf("") }
    val weather by viewModel.weather.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Kaupunki") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.searchWeather(
                    city,
                    BuildConfig.OPENWEATHER_API_KEY
                )
            }
        ) {
            Text("Hae sää")
        }

        Spacer(modifier = Modifier.height(16.dp))

        weather?.let {

            val iconUrl =
                "https://openweathermap.org/img/wn/${it.icon}@4x.png"

            AsyncImage(
                model = iconUrl,
                contentDescription = "Sääkuvake",
                modifier = Modifier.size(120.dp)
            )

            Text(text = it.description)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.name)
            Text(text = "Lämpötila: ${it.temp}°C")
        }
    }
}
