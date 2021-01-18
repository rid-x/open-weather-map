package org.openweathermap.domain.repository

import kotlinx.coroutines.flow.Flow

interface OpenWeatherMapRepository {
    suspend fun <FlowType> getWeather(
        lat: Double,
        lon: Double,
    ): Flow<FlowType>
}