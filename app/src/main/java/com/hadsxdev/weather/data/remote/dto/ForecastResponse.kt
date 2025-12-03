package com.hadsxdev.weather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("cod")
    val responseCode: String,
    
    @SerializedName("message")
    val message: Int,
    
    @SerializedName("cnt")
    val count: Int,
    
    @SerializedName("list")
    val forecastList: List<ForecastItemDto>,
    
    @SerializedName("city")
    val city: CityDto
)

data class ForecastItemDto(
    @SerializedName("dt")
    val timestamp: Long,
    
    @SerializedName("main")
    val main: MainDto,
    
    @SerializedName("weather")
    val weather: List<WeatherDto>,
    
    @SerializedName("clouds")
    val clouds: CloudsDto,
    
    @SerializedName("wind")
    val wind: WindDto,
    
    @SerializedName("visibility")
    val visibility: Int,
    
    @SerializedName("pop")
    val probabilityOfPrecipitation: Double,
    
    @SerializedName("sys")
    val sys: SysForecastDto,
    
    @SerializedName("dt_txt")
    val dateText: String
)

data class SysForecastDto(
    @SerializedName("pod")
    val partOfDay: String // "d" for day, "n" for night
)

data class CityDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("coord")
    val coordinates: Coordinates,
    
    @SerializedName("country")
    val country: String,
    
    @SerializedName("population")
    val population: Int,
    
    @SerializedName("timezone")
    val timezone: Int,
    
    @SerializedName("sunrise")
    val sunrise: Long,
    
    @SerializedName("sunset")
    val sunset: Long
)

data class TemperatureForecastDto(
    @SerializedName("day")
    val day: Double,
    
    @SerializedName("min")
    val min: Double,
    
    @SerializedName("max")
    val max: Double,
    
    @SerializedName("night")
    val night: Double,
    
    @SerializedName("eve")
    val evening: Double,
    
    @SerializedName("morn")
    val morning: Double
)