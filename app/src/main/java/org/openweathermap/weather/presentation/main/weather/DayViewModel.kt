package org.openweathermap.weather.presentation.main.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.openweathermap.weather.data.db.entity.DayEntity
import org.openweathermap.weather.data.network.DayDto
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class DayViewModel(
    val date: String,
    val dayTemperature: Float,
    val nightTemperature: Float,
    val description: String,
    val iconUrl: String
) : Parcelable

object DayViewModelMapper {

    const val IMAGE_URL_PATTERN = "https://openweathermap.org/img/wn/%s.png"
    const val DATE_PATTERN = "EE, MMM dd"

    fun mapDtoToViewModel(daysDto: List<DayDto>): List<DayViewModel> {
        val days = mutableListOf<DayViewModel>()

        for (dayDto in daysDto) {
            val dayViewModel = DayViewModel(
                SimpleDateFormat(DATE_PATTERN, Locale.US).format(dayDto.date * 1000),
                dayDto.temperature.day,
                dayDto.temperature.night,
                dayDto.weatherDetails[0].description,
                String.format(IMAGE_URL_PATTERN, dayDto.weatherDetails[0].iconId)
            )
            days.add(dayViewModel)
        }
        return days
    }

    fun mapEntityToViewModel(entities: List<DayEntity>): List<DayViewModel> {
        val viewModels = mutableListOf<DayViewModel>()
        for (entity in entities) {
            viewModels.add(
                DayViewModel(
                    entity.date,
                    entity.dayTemperature,
                    entity.nightTemperature,
                    entity.description,
                    entity.iconUrl
                )
            )
        }
        return viewModels
    }
}