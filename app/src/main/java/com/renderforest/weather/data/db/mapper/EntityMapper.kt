package com.renderforest.weather.data.db.mapper

import com.renderforest.weather.data.db.entity.DayEntity
import com.renderforest.weather.data.network.DayDto
import com.renderforest.weather.domain.mapper.ViewModelMapper
import com.renderforest.weather.domain.mapper.ViewModelMapper.DATE_PATTERN
import java.text.SimpleDateFormat
import java.util.*

object EntityMapper {

    fun mapDtoToEntity(daysDto: List<DayDto>): List<DayEntity> {
        val entities = mutableListOf<DayEntity>()
        for (dayDto in daysDto) {
            entities.add(
                DayEntity(
                    null,
                    SimpleDateFormat(DATE_PATTERN, Locale.US).format(dayDto.date * 1000),
                    dayDto.temperature.day,
                    dayDto.temperature.night,
                    dayDto.weatherDetails[0].description,
                    String.format(
                        ViewModelMapper.IMAGE_URL_PATTERN,
                        dayDto.weatherDetails[0].iconId
                    )
                )
            )
        }
        return entities
    }
}
