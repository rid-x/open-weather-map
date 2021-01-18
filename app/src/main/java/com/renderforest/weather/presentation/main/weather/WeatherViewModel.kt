package com.renderforest.weather.presentation.main.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renderforest.weather.presentation.main.common.AbstractState
import com.renderforest.weather.presentation.main.common.DefaultState
import com.renderforest.weather.presentation.main.common.ErrorState
import com.renderforest.weather.presentation.main.common.LoadingState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.openweathermap.domain.usecase.WeatherUseCases

val viewModelModule = module {
    factory { WeatherViewModel(get()) }
}

class WeatherViewModel(private val weatherUseCases: WeatherUseCases) : ViewModel() {

    val weatherState = MutableLiveData<AbstractState>()

    fun getWeeklyWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            weatherUseCases.getDailyWeatherByGeoData<List<DayViewModel>>(
                latitude, longitude
            )
                .onStart {
                    weatherState.value = LoadingState
                }
                .catch { e ->
                    weatherState.value = ErrorState(e.message.toString())
                }
                .collect { days ->
                    weatherState.value = DefaultState(days)
                }
        }
    }
}