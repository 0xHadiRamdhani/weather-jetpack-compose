package com.hadsxdev.weather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("coord")
    val coordinates: Coordinates,
    
    @SerializedName("weather")
    val weather: List<WeatherDto>,
    
    @SerializedName("main")
    val main: MainDto,
    
    @SerializedName("visibility")
    val visibility: Int,
    
    @SerializedName("wind")
    val wind: WindDto,
    
    @SerializedName("clouds")
    val clouds: CloudsDto,
    
    @SerializedName("dt")
    val timestamp: Long,
    
    @SerializedName("sys")
    val sys: SysDto,
    
    @SerializedName("timezone")
    val timezone: Int,
    
    @SerializedName("id")
    val cityId: Int,
    
    @SerializedName("name")
    val cityName: String,
    
    @SerializedName("cod")
    val responseCode: Int
)

data class Coordinates(
    @SerializedName("lon")
    val longitude: Double,
    
    @SerializedName("lat")
    val latitude: Double
)

data class WeatherDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("main")
    val main: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("icon")
    val icon: String
)

data class MainDto(
    @SerializedName("temp")
    val temperature: Double,
    
    @SerializedName("feels_like")
    val feelsLike: Double,
    
    @SerializedName("temp_min")
    val tempMin: Double,
    
    @SerializedName("temp_max")
    val tempMax: Double,
    
    @SerializedName("pressure")
    val pressure: Int,
    
    @SerializedName("humidity")
    val humidity: Int,
    
    @SerializedName("sea_level")
    val seaLevel: Int?,
    
    @SerializedName("grnd_level")
    val groundLevel: Int?
)

data class WindDto(
    @SerializedName("speed")
    val speed: Double,
    
    @SerializedName("deg")
    val degree: Int,
    
    @SerializedName("gust")
    val gust: Double?
)

data class CloudsDto(
    @SerializedName("all")
    val cloudiness: Int
)

data class SysDto(
    @SerializedName("type")
    val type: Int?,
    
    @SerializedName("id")
    val id: Int?,
    
    @SerializedName("country")
    val country: String,
    
    @SerializedName("sunrise")
    val sunrise: Long,
    
    @SerializedName("sunset")
    val sunset: Long
)