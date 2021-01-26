package io.github.rid.hrant.weather.domain.repository

import kotlinx.coroutines.flow.Flow

interface OpenWeatherMapRepository {
    suspend fun <FlowType> getWeather(
        lat: Double,
        lon: Double,
    ): Flow<FlowType>
}