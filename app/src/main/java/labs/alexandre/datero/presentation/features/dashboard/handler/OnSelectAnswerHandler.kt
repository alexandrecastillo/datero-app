package labs.alexandre.datero.presentation.features.dashboard.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState
import javax.inject.Inject

class OnSelectAnswerHandler @Inject constructor() :
    DashboardIntentHandler<DashboardIntent.OnSelectAnswer> {

    override suspend fun handle(
        intent: DashboardIntent.OnSelectAnswer,
        state: MutableStateFlow<DashboardUiState>,
        effect: MutableSharedFlow<DashboardEffect>,
        scope: CoroutineScope
    ) {
        state.update { it.copy(markTimestampState = it.markTimestampState.copy(occupancy = intent.answer)) }
    }

}