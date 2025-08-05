package labs.alexandre.datero.presentation.features.dashboard.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState
import labs.alexandre.datero.presentation.features.dashboard.state.MarkTimestampUiState
import javax.inject.Inject

class OnDismissMarkTimestampBusLineHandler @Inject constructor() :
    DashboardIntentHandler<DashboardIntent.OnDismissMarkTimestampBusLine> {

    override suspend fun handle(
        intent: DashboardIntent.OnDismissMarkTimestampBusLine,
        state: MutableStateFlow<DashboardUiState>,
        effect: MutableSharedFlow<DashboardEffect>,
        scope: CoroutineScope
    ) {
        state.update { it.copy(markTimestampState = MarkTimestampUiState.DEFAULT) }
    }

}