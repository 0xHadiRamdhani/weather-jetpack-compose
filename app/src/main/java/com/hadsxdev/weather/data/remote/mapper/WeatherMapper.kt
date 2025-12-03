package com.hadsxdev.weather.data.remote.mapper

import com.hadsxdev.weather.data.remote.dto.CurrentWeatherResponse
import com.hadsxdev.weather.data.remote.dto.ForecastResponse
import com.hadsxdev.weather.domain.model.City
import com.hadsxdev.weather.domain.model.CurrentWeather
import com.hadsxdev.weather.domain.model.DailyForecast
import com.hadsxdev.weather.domain.model.ForecastWeather
import com.hadsxdev.weather.domain.model.Temperature
import com.hadsxdev.weather.domain.model.Weather

object WeatherMapper {

    fun CurrentWeatherResponse.toDomainModel(): CurrentWeather {
        return CurrentWeather(
            cityName = this.cityName,
            temperature = this.main.temperature,
            feelsLike = this.main.feelsLike,
            humidity = this.main.humidity,
            pressure = this.main.pressure,
            windSpeed = this.wind.speed,
            windDirection = this.wind.degree,
            visibility = this.visibility,
            uvIndex = 0.0, // UV Index tidak tersedia di current weather endpoint gratis
            weather = this.weather.map { it.toDomainModel() },
            sunrise = this.sys.sunrise,
            sunset = this.sys.sunset,
            timestamp = this.timestamp
        )
    }

    fun ForecastResponse.toDomainModel(): ForecastWeather {
        // Group forecast by day and take the first forecast of each day
        val dailyForecasts = this.forecastList
            .groupBy { forecastItem ->
                // Group by date (convert timestamp to date string)
                java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                    .format(java.util.Date(forecastItem.timestamp * 1000))
            }
            .map { (_, forecastItems) ->
                // Take the first forecast of the day (usually around noon)
                val dailyForecast = forecastItems.firstOrNull() ?: return@map null
                dailyForecast.toDomainModel()
            }
            .filterNotNull()
            .take(5) // Take only 5 days

        return ForecastWeather(
            cityName = this.city.name,
            list = dailyForecasts
        )
    }

    private fun com.hadsxdev.weather.data.remote.dto.WeatherDto.toDomainModel(): Weather {
        return Weather(
            id = this.id,
            main = this.main,
            description = this.description,
            icon = this.icon
        )
    }

    private fun com.hadsxdev.weather.data.remote.dto.ForecastItemDto.toDomainModel(): DailyForecast {
        return DailyForecast(
            date = this.timestamp,
            temperature = Temperature(
                day = this.main.temperature,
                min = this.main.tempMin,
                max = this.main.tempMax,
                night = this.main.temperature - 3.0, // Approximation
                eve = this.main.temperature - 1.0,
                morn = this.main.temperature - 2.0
            ),
            weather = this.weather.map { it.toDomainModel() },
            humidity = this.main.humidity,
            windSpeed = this.wind.speed,
            pop = this.probabilityOfPrecipitation
        )
    }

    fun com.hadsxdev.weather.data.remote.dto.CityDto.toDomainModel(): City {
        return City(
            id = this.id,
            name = this.name,
            country = this.country,
            latitude = this.coordinates.latitude,
            longitude = this.coordinates.longitude
        )
    }
}