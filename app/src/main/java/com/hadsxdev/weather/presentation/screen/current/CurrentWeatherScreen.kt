package com.hadsxdev.weather.presentation.screen.current

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hadsxdev.weather.presentation.component.*
import com.hadsxdev.weather.presentation.viewmodel.ForecastState
import com.hadsxdev.weather.presentation.viewmodel.WeatherState
import com.hadsxdev.weather.presentation.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    onNavigateToSearch: () -> Unit = {},
    onNavigateToForecast: () -> Unit = {}
) {
    val currentWeatherState by viewModel.currentWeatherState.collectAsState()
    val forecastState by viewModel.forecastState.collectAsState()
    val currentCity by viewModel.currentCity.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = "Weather App",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.refreshWeather() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showSearchBar = !showSearchBar },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Search Bar (shown when FAB is clicked)
            if (showSearchBar) {
                SearchBar(
                    onSearch = { query ->
                        viewModel.getCurrentWeatherByCity(query)
                        viewModel.getForecastByCity(query)
                        searchQuery = query
                        showSearchBar = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Current Weather Section
            when (currentWeatherState) {
                is WeatherState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is WeatherState.Success -> {
                    val weather = (currentWeatherState as WeatherState.Success).weather
                    CurrentWeatherCard(
                        weather = weather,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                is WeatherState.Error -> {
                    val errorMessage = (currentWeatherState as WeatherState.Error).message
                    ErrorCard(
                        message = errorMessage,
                        onRetry = { viewModel.refreshWeather() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
                is WeatherState.Idle -> {
                    // Show welcome message or default city
                    WelcomeCard(
                        onSearchClick = { showSearchBar = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Forecast Section
            Text(
                text = "5-Day Forecast",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (forecastState) {
                is ForecastState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ForecastState.Success -> {
                    val forecast = (forecastState as ForecastState.Success).forecast
                    ForecastCard(
                        forecast = forecast,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                is ForecastState.Error -> {
                    val errorMessage = (forecastState as ForecastState.Error).message
                    ErrorCard(
                        message = errorMessage,
                        onRetry = { viewModel.refreshWeather() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
                is ForecastState.Idle -> {
                    // Show empty state or loading indicator
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No forecast data available",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ErrorCard(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.size(48.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Error",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun WelcomeCard(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(64.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Welcome to Weather App",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Search for a city to get current weather and forecast",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onSearchClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Search City")
            }
        }
    }
}