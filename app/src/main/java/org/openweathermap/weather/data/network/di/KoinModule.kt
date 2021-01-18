package org.openweathermap.weather.data.network.di

import org.openweathermap.weather.data.network.OpenWeatherMapAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.openweathermap.weather.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    factory { provideWeatherAPIService(get()) }
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideWeatherAPIService(retrofit: Retrofit): OpenWeatherMapAPIService {
    return retrofit.create(OpenWeatherMapAPIService::class.java)
}