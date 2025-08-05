package labs.alexandre.datero.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import labs.alexandre.datero.presentation.base.intent.IntentHandlerRegistry
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.handler.*
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardIntentModule {

    @Provides
    @Singleton
    fun provideDashboardIntentHandlers(
        handler1: OnClickMarkTimestampBusLineHandler,
        handler2: OnSubmitBusMarkHandler,
        handler3: OnDismissMarkTimestampBusLineHandler,
        handler4: OnSelectAnswerHandler,
        handler5: StartObservingBusLinesHandler,
        handler6: OnClickSettingsHandler,
        handler7: OnBusMarkProgressFinishHandler,
        handler8: OnClickAddBusLineHandler
    ): IntentHandlerRegistry<DashboardIntent, DashboardUiState, DashboardEffect> {
        return IntentHandlerRegistry<DashboardIntent, DashboardUiState, DashboardEffect>().apply {
            register(handler1)
            register(handler2)
            register(handler3)
            register(handler4)
            register(handler5)
            register(handler6)
            register(handler7)
            register(handler8)
        }
    }
}
