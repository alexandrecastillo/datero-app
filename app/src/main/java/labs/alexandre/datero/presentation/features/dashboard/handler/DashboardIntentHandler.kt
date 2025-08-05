package labs.alexandre.datero.presentation.features.dashboard.handler

import labs.alexandre.datero.presentation.base.intent.BaseIntentHandler
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState

interface DashboardIntentHandler<I : DashboardIntent> :
    BaseIntentHandler<I, DashboardUiState, DashboardEffect>