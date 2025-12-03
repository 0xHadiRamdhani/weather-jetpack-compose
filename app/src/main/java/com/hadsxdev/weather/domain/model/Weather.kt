package com.hadsxdev.weather.domain.model

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class CurrentWeather(
    val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val windDirection: Int,
    val visibility: Int,
    val uvIndex: Double,
    val weather: List<Weather>,
    val sunrise: Long,
    val sunset: Long,
    val timestamp: Long
)

data class ForecastWeather(
    val cityName: String,
    val list: List<DailyForecast>
)

data class DailyForecast(
    val date: Long,
    val temperature: Temperature,
    val weather: List<Weather>,
    val humidity: Int,
    val windSpeed: Double,
    val pop: Double // Probability of precipitation
)

data class Temperature(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)