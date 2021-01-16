package com.renderforest.weather.domain

import com.renderforest.weather.domain.view_model.DayViewModel
import kotlinx.coroutines.flow.Flow

interface WeatherUseCases {
    suspend fun getDailyWeatherByGeoData(lat: Double, lon: Double): Flow<List<DayViewModel>>
}