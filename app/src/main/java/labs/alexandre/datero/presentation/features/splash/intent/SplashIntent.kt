package labs.alexandre.datero.presentation.features.splash.intent

sealed interface SplashIntent {
    object OnStartAnimation: SplashIntent
    object OnFinishAnimation : SplashIntent
}