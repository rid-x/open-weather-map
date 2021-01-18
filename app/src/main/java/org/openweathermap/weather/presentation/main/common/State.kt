package org.openweathermap.weather.presentation.main.common

import org.openweathermap.weather.presentation.main.weather.DayViewModel

sealed class AbstractState

class DefaultState(val days: List<DayViewModel>) : AbstractState()

object LoadingState : AbstractState()
class ErrorState(val message: String) : AbstractState()