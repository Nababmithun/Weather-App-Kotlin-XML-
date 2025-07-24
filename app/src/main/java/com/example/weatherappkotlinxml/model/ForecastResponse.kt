package com.example.weatherappkotlinxml.model

data class ForecastResponse(
    val daily: List<Daily>
)

data class Daily(
    val dt: Long,
    val temp: Temp,
    val weather: List<Weather>
)

data class Temp(
    val min: Double,
    val max: Double
)
