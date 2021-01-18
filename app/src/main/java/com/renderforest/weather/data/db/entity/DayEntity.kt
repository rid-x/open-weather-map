package com.renderforest.weather.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.renderforest.weather.data.network.DayDto
import com.renderforest.weather.presentation.main.weather.DayViewModelMapper
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class DayEntity(
    @PrimaryKey(autoGenerate = true) var uid: Long?,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "day_temperature") val dayTemperature: Float,
    @ColumnInfo(name = "night_temperature") val nightTemperature: Float,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "iconUrl") val iconUrl: String
)

object DayEntityMapper {

    fun mapDtoToEntity(daysDto: List<DayDto>): List<DayEntity> {
        val entities = mutableListOf<DayEntity>()
        for (dayDto in daysDto) {
            entities.add(
                DayEntity(
                    null,
                    SimpleDateFormat(
                        DayViewModelMapper.DATE_PATTERN,
                        Locale.US
                    ).format(dayDto.date * 1000),
                    dayDto.temperature.day,
                    dayDto.temperature.night,
                    dayDto.weatherDetails[0].description,
                    String.format(
                        DayViewModelMapper.IMAGE_URL_PATTERN,
                        dayDto.weatherDetails[0].iconId
                    )
                )
            )
        }
        return entities
    }
}