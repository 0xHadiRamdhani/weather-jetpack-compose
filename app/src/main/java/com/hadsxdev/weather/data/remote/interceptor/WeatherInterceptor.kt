package com.hadsxdev.weather.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class WeatherInterceptor(
    private val apiKey: String
) : Interceptor {
    
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Add API key and other default parameters to all requests
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("appid", apiKey)
            .build()
        
        val request = originalRequest.newBuilder()
            .url(url)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()
        
        return chain.proceed(request)
    }
}