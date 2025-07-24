package com.example.weatherappkotlinxml.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappkotlinxml.data.local.WeatherEntity
import com.example.weatherappkotlinxml.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weather = MutableLiveData<WeatherEntity>()
    val weather: LiveData<WeatherEntity> get() = _weather

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val data = repository.getCurrentWeather(city)
                _weather.postValue(data)
            } catch (e: Exception) {
                // handle error (show message)
            }
        }
    }
}
