package com.renderforest.weather.presentation

import android.app.Application
import com.renderforest.weather.data.db.dbModule
import com.renderforest.weather.data.network.di.networkModule
import com.renderforest.weather.domain.interactorModule
import com.renderforest.weather.data.repository.repositoryModule
import com.renderforest.weather.presentation.main.weather.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

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