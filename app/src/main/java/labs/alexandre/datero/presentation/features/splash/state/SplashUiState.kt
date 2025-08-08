package labs.alexandre.datero.presentation.features.splash.state

sealed class SplashUiState {
    object Idle : SplashUiState()
    object Animating : SplashUiState()
}
