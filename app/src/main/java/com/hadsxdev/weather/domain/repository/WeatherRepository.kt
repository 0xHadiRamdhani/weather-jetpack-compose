package com.hadsxdev.weather.domain.repository

import com.hadsxdev.weather.domain.model.CurrentWeather
import com.hadsxdev.weather.domain.model.ForecastWeather
import com.hadsxdev.weather.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    
    /**
     * Get current weather by city name
     */
    suspend fun getCurrentWeatherByCity(cityName: String): Flow<Resource<CurrentWeather>>
    
    /**
     * Get current weather by coordinates
     */
    suspend fun getCurrentWeatherByCoordinates(latitude: Double, longitude: Double): Flow<Resource<CurrentWeather>>
    
    /**
     * Get 5-day weather forecast by city name
     */
    suspend fun getForecastByCity(cityName: String): Flow<Resource<ForecastWeather>>
    
    /**
     * Get 5-day weather forecast by coordinates
     */
    suspend fun getForecastByCoordinates(latitude: Double, longitude: Double): Flow<Resource<ForecastWeather>>
    
    /**
     * Search cities by name
     */
    suspend fun searchCity(cityName: String): Flow<Resource<CurrentWeather>>
    
    /**
     * Get weather by city ID
     */
    suspend fun getWeatherByCityId(cityId: Int): Flow<Resource<CurrentWeather>>
    
    /**
     * Get cached current weather
     */
    suspend fun getCachedCurrentWeather(): Flow<Resource<CurrentWeather?>>
    
    /**
     * Get cached forecast
     */
    suspend fun getCachedForecast(): Flow<Resource<ForecastWeather?>>
    
    /**
     * Save current weather to cache
     */
    suspend fun saveCurrentWeather(weather: CurrentWeather)
    
    /**
     * Save forecast to cache
     */
    suspend fun saveForecast(forecast: ForecastWeather)
    
    /**
     * Clear all cached data
     */
    suspend fun clearCache()
}