package com.example.weatherappkotlinxml.data.remote

import com.example.weatherappkotlinxml.model.CurrentWeatherResponse
import com.example.weatherappkotlinxml.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Response<CurrentWeatherResponse>

    @GET("onecall")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("exclude") exclude: String = "minutely,hourly",
        @Query("units") units: String = "metric"
    ): Response<ForecastResponse>
}