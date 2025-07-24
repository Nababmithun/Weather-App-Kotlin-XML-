package com.example.weatherappkotlinxml.data.repository

import com.example.weatherappkotlinxml.data.local.WeatherDao
import com.example.weatherappkotlinxml.data.local.WeatherEntity
import com.example.weatherappkotlinxml.data.remote.WeatherApiService
import com.google.gson.Gson

class WeatherRepository(
    private val api: WeatherApiService,
    private val dao: WeatherDao,
    private val apiKey: String
) {

    suspend fun getCurrentWeather(city: String): WeatherEntity {
        return try {
            val response = api.getCurrentWeather(city, apiKey)
            if (response.isSuccessful) {
                val body = response.body()!!
                val entity = WeatherEntity(
                    city = city,
                    temperature = body.main.temp,
                    description = body.weather[0].description,
                    humidity = body.main.humidity,
                    windSpeed = body.wind.speed,
                    date = System.currentTimeMillis(),
                    json = Gson().toJson(body)
                )
                dao.insert(entity)
                entity
            } else {
                dao.getWeather(city) ?: throw Exception("Invalid city or API error")
            }
        } catch (e: Exception) {
            dao.getWeather(city) ?: throw e
        }
    }
}
