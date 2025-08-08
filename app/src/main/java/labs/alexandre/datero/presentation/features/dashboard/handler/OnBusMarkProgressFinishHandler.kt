package labs.alexandre.datero.presentation.features.dashboard.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import labs.alexandre.datero.domain.usecase.CalculateCycleUseCase
import labs.alexandre.datero.domain.usecase.CalculateElapsedTimeUseCase
import labs.alexandre.datero.domain.usecase.Param
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState
import labs.alexandre.datero.presentation.mapper.ElapsedTimeUiMapper
import labs.alexandre.datero.presentation.model.BusMarkUiModel
import javax.inject.Inject

class OnBusMarkProgressFinishHandler @Inject constructor(
    private val calculateElapsedTimeUseCase: CalculateElapsedTimeUseCase,
    private val calculateCycleUseCase: CalculateCycleUseCase,
    private val elapsedTimeUiMapper: ElapsedTimeUiMapper
) : DashboardIntentHandler<DashboardIntent.OnBusMarkProgressFinish> {

    override suspend fun handle(
        intent: DashboardIntent.OnBusMarkProgressFinish,
        state: MutableStateFlow<DashboardUiState>,
        effect: MutableSharedFlow<DashboardEffect>,
        scope: CoroutineScope
    ) {
        val updatedBusLines = state.value.busLines.map { busLine ->
            if (busLine.id != intent.busLineId) return@map busLine

            val updatedMarks = busLine.marks.map { mark ->
                updateMarkIfNeeded(mark, intent)
            }

            busLine.takeIf { it.marks != updatedMarks }?.copy(marks = updatedMarks) ?: busLine
        }

        if (updatedBusLines != state.value.busLines) {
            state.update { it.copy(busLines = updatedBusLines) }
        }
    }

    private fun updateMarkIfNeeded(
        busMark: BusMarkUiModel,
        intent: DashboardIntent.OnBusMarkProgressFinish
    ): BusMarkUiModel {
        if (busMark !is BusMarkUiModel.Current || busMark.id != intent.busMarkId) {
            return busMark
        }

        val elapsedTime = calculateElapsedTimeUseCase.invoke(
            Param.Ongoing(busMark.timestamp)
        )
        val cycle = calculateCycleUseCase.invoke(busMark.timestamp)

        return busMark.copy(
            elapsedTime = elapsedTimeUiMapper.mapToUiModel(elapsedTime),
            cycle = cycle
        )
    }

}