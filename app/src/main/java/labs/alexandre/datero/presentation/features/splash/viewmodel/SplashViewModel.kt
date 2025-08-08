package labs.alexandre.datero.presentation.features.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import labs.alexandre.datero.presentation.features.splash.effect.SplashEffect
import labs.alexandre.datero.presentation.features.splash.intent.SplashIntent
import labs.alexandre.datero.presentation.features.splash.state.SplashUiState

class SplashViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Idle)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    private val _splashEffect = MutableSharedFlow<SplashEffect>()
    val splashEffect = _splashEffect.asSharedFlow()

    fun onIntent(intent: SplashIntent) {
        viewModelScope.launch {
            when (intent) {
                SplashIntent.OnStartAnimation -> {
                    _uiState.update { SplashUiState.Animating }
                }
                SplashIntent.OnFinishAnimation -> {
                    _splashEffect.emit(SplashEffect.NavigateToDashboard)
                }
            }
        }
    }

}