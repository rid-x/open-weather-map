@file:Suppress("UNUSED_PARAMETER", "unused")

package com.renderforest.weather.data.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapAPIService {

    @GET("data/2.5/onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("units") units: WUnit,
        @Query("appid") appId: String
    ): WeatherDto
}

enum class DataPart(part: String) {
    CURRENT("current"),
    MINUTELY("minutely"),
    HOURLY("hourly"),
    DAILY("daily"),
    ALERTS("alerts"),
}

enum class WUnit(unit: String) {
    STANDARD("standard"),
    METRIC("metric"),
    IMPERIAL("imperial")
}

data class WeatherDto(
    @SerializedName("daily") val days: List<DayDto>
)

data class DayDto(
    @SerializedName("dt") val date: Long,
    @SerializedName("temp") val temperature: TemperatureDto,
    @SerializedName("weather") val weatherDetails: List<WeatherDetailsDto>,
)

data class TemperatureDto(
    @SerializedName("day") val day: Float,
    @SerializedName("night") val night: Float,
)

data class WeatherDetailsDto(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val iconId: String
)