package com.hadsxdev.weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadsxdev.weather.domain.model.CurrentWeather
import com.hadsxdev.weather.domain.model.ForecastWeather
import com.hadsxdev.weather.domain.usecase.GetCurrentWeatherUseCase
import com.hadsxdev.weather.domain.usecase.GetForecastUseCase
import com.hadsxdev.weather.domain.usecase.SearchCityUseCase
import com.hadsxdev.weather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel() {

    // Current Weather State
    private val _currentWeatherState = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val currentWeatherState: StateFlow<WeatherState> = _currentWeatherState.asStateFlow()

    // Forecast State
    private val _forecastState = MutableStateFlow<ForecastState>(ForecastState.Idle)
    val forecastState: StateFlow<ForecastState> = _forecastState.asStateFlow()

    // Search State
    private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    // Current Location
    private val _currentLocation = MutableStateFlow<Pair<Double, Double>?>(null)
    val currentLocation: StateFlow<Pair<Double, Double>?> = _currentLocation.asStateFlow()

    // Current City
    private val _currentCity = MutableStateFlow<String?>(null)
    val currentCity: StateFlow<String?> = _currentCity.asStateFlow()

    fun getCurrentWeatherByCity(cityName: String) {
        viewModelScope.launch {
            getCurrentWeatherUseCase(cityName).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { weather ->
                            _currentWeatherState.value = WeatherState.Success(weather)
                            _currentCity.value = cityName
                        } ?: run {
                            _currentWeatherState.value = WeatherState.Error("No weather data available")
                        }
                    }
                    is Resource.Error -> {
                        _currentWeatherState.value = WeatherState.Error(result.message ?: "Unknown error")
                    }
                    is Resource.Loading -> {
                        _currentWeatherState.value = WeatherState.Loading
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getCurrentWeatherByLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _currentLocation.value = Pair(latitude, longitude)
            getCurrentWeatherUseCase.byCoordinates(latitude, longitude).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { weather ->
                            _currentWeatherState.value = WeatherState.Success(weather)
                        } ?: run {
                            _currentWeatherState.value = WeatherState.Error("No weather data available")
                        }
                    }
                    is Resource.Error -> {
                        _currentWeatherState.value = WeatherState.Error(result.message ?: "Unknown error")
                    }
                    is Resource.Loading -> {
                        _currentWeatherState.value = WeatherState.Loading
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getForecastByCity(cityName: String) {
        viewModelScope.launch {
            getForecastUseCase(cityName).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { forecast ->
                            _forecastState.value = ForecastState.Success(forecast)
                        } ?: run {
                            _forecastState.value = ForecastState.Error("No forecast data available")
                        }
                    }
                    is Resource.Error -> {
                        _forecastState.value = ForecastState.Error(result.message ?: "Unknown error")
                    }
                    is Resource.Loading -> {
                        _forecastState.value = ForecastState.Loading
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getForecastByLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            getForecastUseCase.byCoordinates(latitude, longitude).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { forecast ->
                            _forecastState.value = ForecastState.Success(forecast)
                        } ?: run {
                            _forecastState.value = ForecastState.Error("No forecast data available")
                        }
                    }
                    is Resource.Error -> {
                        _forecastState.value = ForecastState.Error(result.message ?: "Unknown error")
                    }
                    is Resource.Loading -> {
                        _forecastState.value = ForecastState.Loading
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun searchCity(cityName: String) {
        viewModelScope.launch {
            searchCityUseCase(cityName).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { weather ->
                            _searchState.value = SearchState.Success(weather)
                        } ?: run {
                            _searchState.value = SearchState.Error("No weather data available")
                        }
                    }
                    is Resource.Error -> {
                        _searchState.value = SearchState.Error(result.message ?: "Unknown error")
                    }
                    is Resource.Loading -> {
                        _searchState.value = SearchState.Loading
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun clearSearch() {
        _searchState.value = SearchState.Idle
    }

    fun refreshWeather() {
        _currentCity.value?.let { city ->
            getCurrentWeatherByCity(city)
            getForecastByCity(city)
        } ?: _currentLocation.value?.let { (lat, lon) ->
            getCurrentWeatherByLocation(lat, lon)
            getForecastByLocation(lat, lon)
        }
    }
}

// State classes
sealed class WeatherState {
    data object Idle : WeatherState()
    data object Loading : WeatherState()
    data class Success(val weather: CurrentWeather) : WeatherState()
    data class Error(val message: String) : WeatherState()
}

sealed class ForecastState {
    data object Idle : ForecastState()
    data object Loading : ForecastState()
    data class Success(val forecast: ForecastWeather) : ForecastState()
    data class Error(val message: String) : ForecastState()
}

sealed class SearchState {
    data object Idle : SearchState()
    data object Loading : SearchState()
    data class Success(val weather: CurrentWeather) : SearchState()
    data class Error(val message: String) : SearchState()
}