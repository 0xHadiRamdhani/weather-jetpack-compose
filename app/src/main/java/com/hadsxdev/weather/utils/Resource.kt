package com.hadsxdev.weather.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}

sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val message: String, val code: Int? = null) : NetworkResult<T>()
    data class Loading<T>(val isLoading: Boolean = true) : NetworkResult<T>()
}

sealed class UIState<out T> {
    data object Idle : UIState<Nothing>()
    data object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
}

fun <T> Resource<T>.toUIState(): UIState<T> {
    return when (this) {
        is Resource.Success -> UIState.Success(this.data!!)
        is Resource.Error -> UIState.Error(this.message ?: "Unknown error")
        is Resource.Loading -> UIState.Loading
    }
}