package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.remote.RetrofitInstance

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weather: WeatherResponse? = null,
    val error: String? = null
)

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun searchWeather(city: String, lang: String = "fi") {
        if(city.isBlank()) return

        viewModelScope.launch {
            _uiState.value = WeatherUiState(isLoading = true)
            try {
                val weather = RetrofitInstance.api.getWeather(city, BuildConfig.OPENWEATHER_API_KEY, lang = lang)
                _uiState.value = WeatherUiState(weather = weather)
            } catch (e: Exception) {
                _uiState.value = WeatherUiState(error = e.message)
            }
        }
    }
}