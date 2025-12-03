package com.hadsxdev.weather.domain.usecase

import com.hadsxdev.weather.domain.model.CurrentWeather
import com.hadsxdev.weather.domain.repository.WeatherRepository
import com.hadsxdev.weather.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    
    suspend operator fun invoke(cityName: String): Flow<Resource<CurrentWeather>> {
        return repository.searchCity(cityName)
    }
}