package com.renderforest.weather.domain.view_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DayViewModel(
    val date: String,
    val dayTemperature: Float,
    val nightTemperature: Float,
    val description: String,
    val iconUrl: String
) : Parcelable