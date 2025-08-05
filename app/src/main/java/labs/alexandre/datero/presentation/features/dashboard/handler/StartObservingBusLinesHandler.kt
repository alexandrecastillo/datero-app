package labs.alexandre.datero.presentation.features.dashboard.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import labs.alexandre.datero.domain.usecase.ObserveBusLineWithTimestampsUseCase
import labs.alexandre.datero.presentation.diff.BusLineUiDiffCalculator
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardContentState
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class StartObservingBusLinesHandler @Inject constructor(
    private val observeBusLineWithTimestampsUseCase: ObserveBusLineWithTimestampsUseCase,
    private val diffCalculator: BusLineUiDiffCalculator
) : DashboardIntentHandler<DashboardIntent.StartObservingBusLines> {

    override suspend fun handle(
        intent: DashboardIntent.StartObservingBusLines,
        state: MutableStateFlow<DashboardUiState>,
        effect: MutableSharedFlow<DashboardEffect>,
        scope: CoroutineScope
    ) {
        observeBusLineWithTimestampsUseCase.invoke()
            .onStart {
                state.update { it.copy(contentState = DashboardContentState.LOADING) }
            }
            .mapLatest { busLines ->
                diffCalculator.calculate(state.value.busLines, busLines)
            }
            .collectLatest { busLines ->
                state.update {
                    it.copy(
                        contentState = DashboardContentState.LOADED,
                        busLines = busLines
                    )
                }
            }
    }

}