package io.github.rid.hrant.weather.domain.interactor

import io.github.rid.hrant.weather.domain.repository.OpenWeatherMapRepository
import io.github.rid.hrant.weather.domain.usecase.WeatherUseCases
import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module

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