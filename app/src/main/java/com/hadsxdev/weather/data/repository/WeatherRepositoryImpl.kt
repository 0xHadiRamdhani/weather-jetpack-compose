package com.hadsxdev.weather.data.repository

import com.hadsxdev.weather.data.remote.api.WeatherApiService
import com.hadsxdev.weather.data.remote.mapper.WeatherMapper.toDomainModel
import com.hadsxdev.weather.domain.model.CurrentWeather
import com.hadsxdev.weather.domain.model.ForecastWeather
import com.hadsxdev.weather.domain.repository.WeatherRepository
import com.hadsxdev.weather.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {
    
    override suspend fun getCurrentWeatherByCity(cityName: String): Flow<Resource<CurrentWeather>> = flow {
        emit(Resource.Loading())
        
        try {
            val response = apiService.getCurrentWeatherByCity(cityName)
            if (response.isSuccessful) {
                response.body()?.let { weatherResponse ->
                    val currentWeather = weatherResponse.toDomainModel()
                    emit(Resource.Success(currentWeather))
                } ?: emit(Resource.Error("Empty response body"))
            } else {
                val errorMessage = when (response.code()) {
                    404 -> "City not found"
                    401 -> "Invalid API key"
                    429 -> "API rate limit exceeded"
                    else -> "Error: ${response.message()}"
                }
                emit(Resource.Error(errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Connection error: Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.message}"))
        }
    }
    
    override suspend fun getCurrentWeatherByCoordinates(
        latitude: Double, 
        longitude: Double
    ): Flow<Resource<CurrentWeather>> = flow {
        emit(Resource.Loading())
        
        try {
            val response = apiService.getCurrentWeatherByCoordinates(latitude, longitude)
            if (response.isSuccessful) {
                response.body()?.let { weatherResponse ->
                    val currentWeather = weatherResponse.toDomainModel()
                    emit(Resource.Success(currentWeather))
                } ?: emit(Resource.Error("Empty response body"))
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Invalid API key"
                    429 -> "API rate limit exceeded"
                    else -> "Error: ${response.message()}"
                }
                emit(Resource.Error(errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Connection error: Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.message}"))
        }
    }
    
    override suspend fun getForecastByCity(cityName: String): Flow<Resource<ForecastWeather>> = flow {
        emit(Resource.Loading())
        
        try {
            val response = apiService.getForecastByCity(cityName)
            if (response.isSuccessful) {
                response.body()?.let { forecastResponse ->
                    val forecast = forecastResponse.toDomainModel()
                    emit(Resource.Success(forecast))
                } ?: emit(Resource.Error("Empty response body"))
            } else {
                val errorMessage = when (response.code()) {
                    404 -> "City not found"
                    401 -> "Invalid API key"
                    429 -> "API rate limit exceeded"
                    else -> "Error: ${response.message()}"
                }
                emit(Resource.Error(errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Connection error: Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.message}"))
        }
    }
    
    override suspend fun getForecastByCoordinates(
        latitude: Double, 
        longitude: Double
    ): Flow<Resource<ForecastWeather>> = flow {
        emit(Resource.Loading())
        
        try {
            val response = apiService.getForecastByCoordinates(latitude, longitude)
            if (response.isSuccessful) {
                response.body()?.let { forecastResponse ->
                    val forecast = forecastResponse.toDomainModel()
                    emit(Resource.Success(forecast))
                } ?: emit(Resource.Error("Empty response body"))
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Invalid API key"
                    429 -> "API rate limit exceeded"
                    else -> "Error: ${response.message()}"
                }
                emit(Resource.Error(errorMessage))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Connection error: Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.message}"))
        }
    }
    
    override suspend fun searchCity(cityName: String): Flow<Resource<CurrentWeather>> = flow {
        emit(Resource.Loading())
        
        try {
            val response = apiService.searchCity(cityName)
            if (response.isSuccessful) {
                response.body()?.let { weatherResponse ->
                    val currentWeather = weatherResponse.toDomainModel()
                    emit(Resource.Success(currentWeather))
                } ?: emit(Resource.Error("City not found"))
            } else {
                emit(Resource.Error("City not found"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Connection error: Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.message}"))
        }
    }
    
    override suspend fun getWeatherByCityId(cityId: Int): Flow<Resource<CurrentWeather>> = flow {
        emit(Resource.Loading())
        
        try {
            val response = apiService.getWeatherByCityId(cityId)
            if (response.isSuccessful) {
                response.body()?.let { weatherResponse ->
                    val currentWeather = weatherResponse.toDomainModel()
                    emit(Resource.Success(currentWeather))
                } ?: emit(Resource.Error("Empty response body"))
            } else {
                emit(Resource.Error("City not found"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Connection error: Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.message}"))
        }
    }
    
    // TODO: Implement cache functionality with Room database
    override suspend fun getCachedCurrentWeather(): Flow<Resource<CurrentWeather?>> = flow {
        emit(Resource.Success(null)) // Placeholder - will implement with Room
    }
    
    override suspend fun getCachedForecast(): Flow<Resource<ForecastWeather?>> = flow {
        emit(Resource.Success(null)) // Placeholder - will implement with Room
    }
    
    override suspend fun saveCurrentWeather(weather: CurrentWeather) {
        // Placeholder - will implement with Room
    }
    
    override suspend fun saveForecast(forecast: ForecastWeather) {
        // Placeholder - will implement with Room
    }
    
    override suspend fun clearCache() {
        // Placeholder - will implement with Room
    }
}