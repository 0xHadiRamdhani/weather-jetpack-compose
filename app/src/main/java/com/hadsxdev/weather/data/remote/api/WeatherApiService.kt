package com.hadsxdev.weather.data.remote.api

import com.hadsxdev.weather.data.remote.dto.CurrentWeatherResponse
import com.hadsxdev.weather.data.remote.dto.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "YOUR_API_KEY_HERE" // Replace with your actual API key
        const val UNITS = "metric" // metric, imperial, or standard
        const val LANG = "id" // Indonesian language
    }
    
    /**
     * Get current weather by city name
     */
    @GET("weather")
    suspend fun getCurrentWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") language: String = LANG
    ): Response<CurrentWeatherResponse>
    
    /**
     * Get current weather by coordinates
     */
    @GET("weather")
    suspend fun getCurrentWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") language: String = LANG
    ): Response<CurrentWeatherResponse>
    
    /**
     * Get 5-day weather forecast by city name
     */
    @GET("forecast")
    suspend fun getForecastByCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") language: String = LANG
    ): Response<ForecastResponse>
    
    /**
     * Get 5-day weather forecast by coordinates
     */
    @GET("forecast")
    suspend fun getForecastByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") language: String = LANG
    ): Response<ForecastResponse>
    
    /**
     * Search cities by name (using the same weather endpoint but with partial names)
     */
    @GET("weather")
    suspend fun searchCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") language: String = LANG
    ): Response<CurrentWeatherResponse>
    
    /**
     * Get weather data by city ID (useful for saved cities)
     */
    @GET("weather")
    suspend fun getWeatherByCityId(
        @Query("id") cityId: Int,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = UNITS,
        @Query("lang") language: String = LANG
    ): Response<CurrentWeatherResponse>
}