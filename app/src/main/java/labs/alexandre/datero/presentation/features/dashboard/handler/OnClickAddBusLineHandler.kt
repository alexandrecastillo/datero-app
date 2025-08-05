package labs.alexandre.datero.presentation.features.dashboard.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState
import javax.inject.Inject

class OnClickAddBusLineHandler @Inject constructor() :
    DashboardIntentHandler<DashboardIntent.OnClickAddBusLine> {

    override suspend fun handle(
        intent: DashboardIntent.OnClickAddBusLine,
        state: MutableStateFlow<DashboardUiState>,
        effect: MutableSharedFlow<DashboardEffect>,
        scope: CoroutineScope
    ) {
        effect.emit(DashboardEffect.NavigateToCreateBusLine)
    }

}