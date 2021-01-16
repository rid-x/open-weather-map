package com.renderforest.weather.data.repository

import com.renderforest.weather.data.network.OpenWeatherMapAPIService
import com.renderforest.weather.data.network.WUnit
import com.renderforest.weather.data.network.WeatherDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        @Suppress("USELESS_CAST")
        OpenWeatherMapFetcher(get()) as OpenWeatherMapRepository
    }
}

class OpenWeatherMapFetcher(
    private val openWeatherMapAPIService: OpenWeatherMapAPIService
) : OpenWeatherMapRepository {

    override suspend fun getWeather(
        lat: Double,
        lon: Double,
        exclude: String,
        units: WUnit,
        appId: String
    ): Flow<WeatherDto> {
        return flow {
            val weatherDto: WeatherDto =
                openWeatherMapAPIService.getWeather(lat, lon, exclude, units, appId)
            emit(weatherDto)
        }.flowOn(Dispatchers.IO)
    }
}