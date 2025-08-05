package labs.alexandre.datero.presentation.features.dashboard.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import labs.alexandre.datero.presentation.base.intent.IntentHandlerRegistry
import labs.alexandre.datero.presentation.base.viewmodel.BaseViewModel
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    intentHandler: IntentHandlerRegistry<DashboardIntent, DashboardUiState, DashboardEffect>
) : BaseViewModel<DashboardIntent, DashboardUiState, DashboardEffect>(intentHandler) {

    override val initialState: DashboardUiState
        get() = DashboardUiState.DEFAULT

    init {
        onIntent(DashboardIntent.StartObservingBusLines)
    }

}