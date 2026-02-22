package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.WeatherEntity
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weather: WeatherResponse? = null,
    val error: String? = null
)

class WeatherViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _selectedCity = MutableStateFlow<String?>(null)

    init {
        viewModelScope.launch {
            repository.getLatestWeather().firstOrNull()?.name?.let { lastCity ->
                if (_selectedCity.value == null) {
                    _selectedCity.value = lastCity
                }
            }
        }
    }

    val weather: StateFlow<WeatherEntity?> =
        _selectedCity
            .flatMapLatest { city ->
                if (city == null) {
                    emptyFlow()
                } else {
                    repository.observeWeather(city)
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )

    fun searchWeather(city: String, apiKey: String, lang: String = "fi") {
        if (city.isBlank()) return

        _selectedCity.value = city

        viewModelScope.launch {
            repository.getWeather(city, apiKey, lang)
        }
    }
}
