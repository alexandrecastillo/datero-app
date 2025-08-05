package labs.alexandre.datero.presentation.features.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import labs.alexandre.datero.presentation.features.splash.effect.SplashEffect

class SplashViewModel : ViewModel() {

    private val _splashEffect = MutableSharedFlow<SplashEffect>()
    val splashEffect = _splashEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            _splashEffect.emit(SplashEffect.NavigateToDashboard)
        }
    }

}