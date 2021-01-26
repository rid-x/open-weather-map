package io.github.rid.hrant.weather.domain.usecase

import kotlinx.coroutines.flow.Flow

interface WeatherUseCases {
    suspend fun <FlowType> getDailyWeatherByGeoData(lat: Double, lon: Double): Flow<FlowType>
}