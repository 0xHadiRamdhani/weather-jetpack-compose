package com.hadsxdev.weather.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hadsxdev.weather.domain.model.DailyForecast
import com.hadsxdev.weather.domain.model.ForecastWeather
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ForecastCard(
    forecast: ForecastWeather,
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
                .padding(16.dp)
        ) {
            Text(
                text = "5-Day Forecast",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(forecast.list) { dailyForecast ->
                    DailyForecastItem(
                        forecast = dailyForecast,
                        modifier = Modifier.width(100.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DailyForecastItem(
    forecast: DailyForecast,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Day name
            Text(
                text = SimpleDateFormat("EEE", Locale("id", "ID"))
                    .format(Date(forecast.date * 1000)),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            // Date
            Text(
                text = SimpleDateFormat("dd/MM", Locale.getDefault())
                    .format(Date(forecast.date * 1000)),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Weather icon
            if (forecast.weather.isNotEmpty()) {
                WeatherIcon(
                    iconCode = forecast.weather.first().icon,
                    size = 40.dp
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Temperature
            Text(
                text = "${forecast.temperature.max.toInt()}°",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = "${forecast.temperature.min.toInt()}°",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Precipitation probability
            if (forecast.pop > 0) {
                Text(
                    text = "${(forecast.pop * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                // Rain icon
                Text(
                    text = "🌧️",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 16.sp
                )
            }
            
            // Humidity
            Text(
                text = "${forecast.humidity}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            // Wind speed
            Text(
                text = "${forecast.windSpeed}m/s",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ForecastList(
    forecasts: List<DailyForecast>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        forecasts.forEach { forecast ->
            ForecastListItem(forecast = forecast)
        }
    }
}

@Composable
fun ForecastListItem(
    forecast: DailyForecast,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date and day
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = SimpleDateFormat("EEEE", Locale("id", "ID"))
                        .format(Date(forecast.date * 1000)),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = SimpleDateFormat("dd MMM", Locale.getDefault())
                        .format(Date(forecast.date * 1000)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Weather icon
            if (forecast.weather.isNotEmpty()) {
                WeatherIcon(
                    iconCode = forecast.weather.first().icon,
                    size = 32.dp
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Temperature range
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${forecast.temperature.max.toInt()}°",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = "${forecast.temperature.min.toInt()}°",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Precipitation
            if (forecast.pop > 0) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "${(forecast.pop * 100).toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = "🌧️",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}