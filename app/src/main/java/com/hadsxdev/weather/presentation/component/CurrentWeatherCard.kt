package com.hadsxdev.weather.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hadsxdev.weather.domain.model.CurrentWeather
import com.hadsxdev.weather.domain.model.Weather
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CurrentWeatherCard(
    weather: CurrentWeather,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // City Name
            Text(
                text = weather.cityName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Current Date
            Text(
                text = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
                    .format(Date(weather.timestamp * 1000)),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Weather Icon and Temperature
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (weather.weather.isNotEmpty()) {
                    val weatherItem = weather.weather.first()
                    WeatherIcon(
                        iconCode = weatherItem.icon,
                        size = 80.dp
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = "${weather.temperature.toInt()}°C",
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        Text(
                            text = weatherItem.description.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Weather Details Grid
            WeatherDetailsGrid(weather = weather)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sunrise and Sunset
            SunriseSunsetRow(
                sunrise = weather.sunrise,
                sunset = weather.sunset
            )
        }
    }
}

@Composable
fun WeatherIcon(
    iconCode: String,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
    
    AsyncImage(
        model = iconUrl,
        contentDescription = "Weather icon",
        modifier = modifier.size(size)
    )
}

@Composable
fun WeatherDetailsGrid(
    weather: CurrentWeather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherDetailItem(
                label = "Feels Like",
                value = "${weather.feelsLike.toInt()}°C",
                icon = "🌡️"
            )
            
            WeatherDetailItem(
                label = "Humidity",
                value = "${weather.humidity}%",
                icon = "💧"
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherDetailItem(
                label = "Wind Speed",
                value = "${weather.windSpeed} m/s",
                icon = "💨"
            )
            
            WeatherDetailItem(
                label = "Pressure",
                value = "${weather.pressure} hPa",
                icon = "🌪️"
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherDetailItem(
                label = "Visibility",
                value = "${weather.visibility / 1000} km",
                icon = "👁️"
            )
            
            WeatherDetailItem(
                label = "Wind Direction",
                value = "${weather.windDirection}°",
                icon = "🧭"
            )
        }
    }
}

@Composable
fun WeatherDetailItem(
    label: String,
    value: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SunriseSunsetRow(
    sunrise: Long,
    sunset: Long,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SunriseSunsetItem(
            time = sunrise,
            label = "Sunrise",
            icon = "🌅"
        )
        
        SunriseSunsetItem(
            time = sunset,
            label = "Sunset",
            icon = "🌇"
        )
    }
}

@Composable
fun SunriseSunsetItem(
    time: Long,
    label: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 24.sp
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(Date(time * 1000)),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}