package io.github.rid.hrant.weather.presentation.main.common

import io.github.rid.hrant.weather.presentation.main.weather.DayViewModel

sealed class AbstractState

class DefaultState(val days: List<DayViewModel>) : AbstractState()

object LoadingState : AbstractState()
class ErrorState(val message: String) : AbstractState()