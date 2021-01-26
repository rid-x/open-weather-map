package io.github.rid.hrant.weather.data.repository_impl

import io.github.rid.hrant.weather.BuildConfig
import io.github.rid.hrant.weather.data.db.dao.DayDao
import io.github.rid.hrant.weather.data.db.entity.DayEntityMapper
import io.github.rid.hrant.weather.data.network.*
import io.github.rid.hrant.weather.domain.repository.OpenWeatherMapRepository
import io.github.rid.hrant.weather.presentation.main.weather.DayViewModelMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.koin.dsl.module
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