package org.openweathermap.weather.presentation

import android.app.Application
import org.openweathermap.weather.data.db.dbModule
import org.openweathermap.weather.data.network.di.networkModule
import org.openweathermap.weather.data.repository_impl.repositoryModule
import org.openweathermap.weather.presentation.main.weather.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.openweathermap.domain.interactor.interactorModule

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    interactorModule,
                    viewModelModule,
                    dbModule
                )
            )
        }
    }
}