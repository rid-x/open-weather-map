package com.renderforest.weather.data.repository

import com.renderforest.weather.data.network.WUnit
import com.renderforest.weather.data.network.WeatherDto
import kotlinx.coroutines.flow.Flow

interface OpenWeatherMapRepository {
    suspend fun getWeather(
        lat: Double,
        lon: Double,
        exclude: String,
        units: WUnit,
        appId: String
    ): Flow<WeatherDto>
}