package org.openweathermap.domain.interactor

import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module
import org.openweathermap.domain.repository.OpenWeatherMapRepository
import org.openweathermap.domain.usecase.WeatherUseCases

val interactorModule = module {
    factory {
        @Suppress("USELESS_CAST")
        WeatherInteractor(get()) as WeatherUseCases
    }
}

class WeatherInteractor(private val repository: OpenWeatherMapRepository) : WeatherUseCases {

    override suspend fun <FlowType> getDailyWeatherByGeoData(
        lat: Double,
        lon: Double
    ): Flow<FlowType> {
        return repository.getWeather(
            lat, lon
        )
    }
}