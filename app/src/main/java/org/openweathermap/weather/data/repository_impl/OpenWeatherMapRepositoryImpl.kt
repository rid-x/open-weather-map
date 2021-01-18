package org.openweathermap.weather.data.repository_impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.koin.dsl.module
import org.openweathermap.domain.repository.OpenWeatherMapRepository
import org.openweathermap.weather.BuildConfig
import org.openweathermap.weather.data.db.dao.DayDao
import org.openweathermap.weather.data.db.entity.DayEntityMapper
import org.openweathermap.weather.data.network.*
import org.openweathermap.weather.presentation.main.weather.DayViewModelMapper
import java.io.IOException

val repositoryModule = module {
    factory {
        @Suppress("USELESS_CAST")
        OpenWeatherMapFetcher(get(), get()) as OpenWeatherMapRepository
    }
}

class OpenWeatherMapFetcher(
    private val openWeatherMapAPIService: OpenWeatherMapAPIService,
    private val dayDao: DayDao
) : OpenWeatherMapRepository {

    override suspend fun <FlowType> getWeather(
        lat: Double,
        lon: Double
    ): Flow<FlowType> = flow {
        return@flow flow {
            val weatherDto: WeatherDto =
                openWeatherMapAPIService.getWeather(
                    lat, lon, EXCLUDE, WUnit.METRIC, BuildConfig.API_KEY
                )
            emit(weatherDto)
        }.flowOn(Dispatchers.IO)
            .map { weatherDto ->
                val days = weatherDto.days
                saveOnDb(days)
                DayViewModelMapper.mapDtoToViewModel(days)
            }
            .catch { t ->
                if (isNoConnection(t)) {
                    emit(DayViewModelMapper.mapEntityToViewModel(dayDao.getAll()))
                }
            }
            .collect {
                @Suppress("UNCHECKED_CAST")
                emit(it as FlowType)
            }
    }.flowOn(Dispatchers.IO)

    private fun isNoConnection(throwable: Throwable): Boolean {
        return throwable is IOException
    }

    private fun saveOnDb(daysDto: List<DayDto>) {
        dayDao.dropTable()
        dayDao.insertAll(*DayEntityMapper.mapDtoToEntity(daysDto).toTypedArray())
    }

    companion object {
        private var EXCLUDE: String =
            DataPart.CURRENT.name + "," + DataPart.MINUTELY.name + "," + DataPart.HOURLY.name + "," + DataPart.ALERTS.name
    }
}