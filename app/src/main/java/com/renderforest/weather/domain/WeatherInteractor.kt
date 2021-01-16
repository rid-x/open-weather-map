package com.renderforest.weather.domain

import com.renderforest.weather.BuildConfig
import com.renderforest.weather.data.db.dao.DayDao
import com.renderforest.weather.data.db.mapper.EntityMapper
import com.renderforest.weather.domain.view_model.DayViewModel
import com.renderforest.weather.domain.mapper.ViewModelMapper
import com.renderforest.weather.data.network.DataPart
import com.renderforest.weather.data.network.DayDto
import com.renderforest.weather.data.repository.OpenWeatherMapRepository
import com.renderforest.weather.data.network.WUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import java.io.IOException

val interactorModule = module {
    factory {
        @Suppress("USELESS_CAST")
        WeatherInteractor(get()) as WeatherUseCases
    }
}

class WeatherInteractor(private val repository: OpenWeatherMapRepository) : WeatherUseCases {

    private val dayDao: DayDao by inject(DayDao::class.java)

    override suspend fun getDailyWeatherByGeoData(
        lat: Double,
        lon: Double
    ): Flow<List<DayViewModel>> {
        return flow {
            repository.getWeather(
                lat, lon, EXCLUDE, WUnit.METRIC, BuildConfig.API_KEY
            )
                .map { weatherDto ->
                    val days = weatherDto.days
                    saveOnDb(days)
                    ViewModelMapper.mapDtoToViewModel(days)
                }
                .catch { t ->
                    if (isNoConnection(t)) {
                        emit(ViewModelMapper.mapEntityToViewModel(dayDao.getAll()))
                    }
                }
                .collect {
                    emit(it)
                }
        }.flowOn(Dispatchers.IO)
    }

    private fun isNoConnection(throwable: Throwable): Boolean {
        return throwable is IOException
    }

    private fun saveOnDb(daysDto: List<DayDto>) {
        dayDao.dropTable()
        dayDao.insertAll(*EntityMapper.mapDtoToEntity(daysDto).toTypedArray())
    }

    companion object {
        private var EXCLUDE: String =
            DataPart.CURRENT.name + "," + DataPart.MINUTELY.name + "," + DataPart.HOURLY.name + "," + DataPart.ALERTS.name
    }
}