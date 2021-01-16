package com.renderforest.weather.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DayEntity(
    @PrimaryKey(autoGenerate = true) var uid: Long?,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "day_temperature") val dayTemperature: Float,
    @ColumnInfo(name = "night_temperature") val nightTemperature: Float,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "iconUrl") val iconUrl: String
)

