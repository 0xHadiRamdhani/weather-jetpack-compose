package com.hadsxdev.weather.domain.usecase

import com.hadsxdev.weather.domain.model.CurrentWeather
import com.hadsxdev.weather.domain.repository.WeatherRepository
import com.hadsxdev.weather.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    
    suspend operator fun invoke(cityName: String): Flow<Resource<CurrentWeather>> {
        return repository.getCurrentWeatherByCity(cityName)
    }
    
    suspend fun byCoordinates(latitude: Double, longitude: Double): Flow<Resource<CurrentWeather>> {
        return repository.getCurrentWeatherByCoordinates(latitude, longitude)
    }
    
    suspend fun byCityId(cityId: Int): Flow<Resource<CurrentWeather>> {
        return repository.getWeatherByCityId(cityId)
    }
}