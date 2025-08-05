package labs.alexandre.datero.presentation.features.dashboard.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnClickMarkTimestampBusLineHandler @Inject constructor() :
    DashboardIntentHandler<DashboardIntent.OnClickMarkTimestampBusLine> {

    override suspend fun handle(
        intent: DashboardIntent.OnClickMarkTimestampBusLine,
        state: MutableStateFlow<DashboardUiState>,
        effect: MutableSharedFlow<DashboardEffect>,
        scope: CoroutineScope
    ) {
        state.update {
            it.copy(
                markTimestampState = state.value.markTimestampState.copy(
                    busLine = intent.busLine,
                    showDialog = true
                )
            )
        }
    }
}