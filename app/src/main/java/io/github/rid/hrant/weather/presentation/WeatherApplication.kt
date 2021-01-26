package io.github.rid.hrant.weather.presentation

import android.app.Application
import io.github.rid.hrant.weather.data.db.dbModule
import io.github.rid.hrant.weather.data.network.di.networkModule
import io.github.rid.hrant.weather.data.repository_impl.repositoryModule
import io.github.rid.hrant.weather.domain.interactor.interactorModule
import io.github.rid.hrant.weather.presentation.main.weather.viewModelModule
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