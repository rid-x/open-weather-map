package com.renderforest.weather.presentation.main.common

import com.renderforest.weather.domain.view_model.DayViewModel

sealed class AbstractState

class DefaultState(val days: List<DayViewModel>) : AbstractState()

object LoadingState : AbstractState()
class ErrorState(val message: String) : AbstractState()