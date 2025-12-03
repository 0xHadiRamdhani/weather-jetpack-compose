package com.hadsxdev.weather.domain.usecase

import com.hadsxdev.weather.domain.model.ForecastWeather
import com.hadsxdev.weather.domain.repository.WeatherRepository
import com.hadsxdev.weather.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    
    suspend operator fun invoke(cityName: String): Flow<Resource<ForecastWeather>> {
        return repository.getForecastByCity(cityName)
    }
    
    suspend fun byCoordinates(latitude: Double, longitude: Double): Flow<Resource<ForecastWeather>> {
        return repository.getForecastByCoordinates(latitude, longitude)
    }
}