package labs.alexandre.datero.presentation.features.dashboard.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import labs.alexandre.datero.R
import labs.alexandre.datero.core.resources.StringProvider
import labs.alexandre.datero.domain.model.BusMark
import labs.alexandre.datero.domain.provider.SystemTimeProvider
import labs.alexandre.datero.domain.repository.BusLineRepository
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState
import labs.alexandre.datero.presentation.features.dashboard.state.MarkTimestampUiState
import labs.alexandre.datero.presentation.mapper.BusOccupancyUiMapper
import javax.inject.Inject

class OnSubmitBusMarkHandler @Inject constructor(
    private val busLineRepository: BusLineRepository,
    private val busOccupancyUiMapper: BusOccupancyUiMapper,
    private val stringProvider: StringProvider,
    private val systemTimeProvider: SystemTimeProvider
) : DashboardIntentHandler<DashboardIntent.OnSubmitBusMark> {

    override suspend fun handle(
        intent: DashboardIntent.OnSubmitBusMark,
        state: MutableStateFlow<DashboardUiState>,
        effect: MutableSharedFlow<DashboardEffect>,
        scope: CoroutineScope
    ) {
        val markTimestampState = state.value.markTimestampState
        val busLineId = markTimestampState.busLine.id
        val occupancy = markTimestampState.occupancy ?: return

        val timestamp = systemTimeProvider.getCurrentTime()

        busLineRepository.addBusMark(
            BusMark.Undefined(
                id = 0,
                busLineId = busLineId,
                timestamp = timestamp,
                occupancy = busOccupancyUiMapper.mapToDomain(occupancy)
            )
        )

        state.update { it.copy(markTimestampState = MarkTimestampUiState.DEFAULT) }
        effect.emit(DashboardEffect.ShowSnackbar(stringProvider.getString(R.string.dashboard_message_success_on_record)))
    }

}