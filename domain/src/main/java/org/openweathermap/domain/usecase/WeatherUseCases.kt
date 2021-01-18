package org.openweathermap.domain.usecase

import kotlinx.coroutines.flow.Flow

interface WeatherUseCases {
    suspend fun <FlowType> getDailyWeatherByGeoData(lat: Double, lon: Double): Flow<FlowType>
}