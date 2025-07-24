package com.example.weatherappkotlinxml

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.qasystemappxml.R
import com.example.weatherappkotlinxml.data.local.WeatherDatabase
import com.example.weatherappkotlinxml.data.remote.RetrofitInstance
import com.example.weatherappkotlinxml.data.repository.WeatherRepository
import com.example.weatherappkotlinxml.data.viewmodel.WeatherViewModel
import com.example.weatherappkotlinxml.data.viewmodel.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(applicationContext, WeatherDatabase::class.java, "weather-db").build()
        val repo = WeatherRepository(RetrofitInstance.api, db.weatherDao(), "3c34bd509df95614061e11d3f5dab162")
        viewModel = WeatherViewModelFactory(repo).create(WeatherViewModel::class.java)

        // Observe data
        viewModel.weather.observe(this) { data ->
            findViewById<TextView>(R.id.temperatureText).text = "${data.temperature}°C"
            findViewById<TextView>(R.id.descriptionText).text = data.description
            findViewById<TextView>(R.id.humidityText).text = "Humidity: ${data.humidity}%"
            findViewById<TextView>(R.id.windText).text = "Wind: ${data.windSpeed} m/s"
        }

        // Search button
        findViewById<Button>(R.id.searchButton).setOnClickListener {
            val city = findViewById<EditText>(R.id.searchEditText).text.toString()
            if (city.isNotBlank()) {
                viewModel.fetchWeather(city)
            }
        }
    }
}