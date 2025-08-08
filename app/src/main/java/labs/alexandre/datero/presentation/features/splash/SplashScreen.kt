package labs.alexandre.datero.presentation.features.splash

import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.features.splash.effect.SplashEffect
import labs.alexandre.datero.presentation.features.splash.intent.SplashIntent
import labs.alexandre.datero.presentation.features.splash.state.SplashUiState
import labs.alexandre.datero.presentation.features.splash.viewmodel.SplashViewModel
import labs.alexandre.datero.presentation.theme.DateroTheme

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(), navToDashboard: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.splashEffect) {
        viewModel.splashEffect.collect { effect ->
            when (effect) {
                SplashEffect.NavigateToDashboard -> navToDashboard()
            }
        }
    }

    SplashScreenSkeleton(
        uiState = uiState, onIntent = { intent -> viewModel.onIntent(intent) })
}

@Composable
fun SplashScreenSkeleton(
    uiState: SplashUiState,
    onIntent: (intent: SplashIntent) -> Unit
) {
    val transition = updateTransition(
        targetState = uiState, label = "SplashTransition"
    )

    val scale by transition.animateFloat(
        label = "scale", transitionSpec = { tween(1000, easing = EaseOutBack) }) {
        if (it is SplashUiState.Animating) 1.2f else 0.8f
    }

    val rotation by transition.animateFloat(
        label = "rotation", transitionSpec = { tween(1000, easing = FastOutSlowInEasing) }) {
        if (it is SplashUiState.Animating) 360f else 0f
    }

    val alpha by transition.animateFloat(
        label = "alpha", transitionSpec = { tween(600) }) {
        if (it is SplashUiState.Animating) 1f else 0f
    }

    LaunchedEffect(uiState) {
        if (uiState is SplashUiState.Idle) {
            onIntent(SplashIntent.OnStartAnimation)
        }
    }

    LaunchedEffect(transition.currentState, transition.targetState, transition.isRunning) {
        if (transition.currentState == SplashUiState.Animating
            && transition.targetState == SplashUiState.Animating
            && !transition.isRunning
        ) {
            delay(1000)
            onIntent(SplashIntent.OnFinishAnimation)
        }
    }

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_splash),
                contentDescription = stringResource(R.string.splash_content_description_image),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(96.dp)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        rotationZ = rotation,
                        alpha = alpha
                    )
            )
            Text(
                text = stringResource(R.string.splash_credits),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .alpha(alpha = alpha)
                    .padding(bottom = 32.dp)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewSplashScreen() {
    DateroTheme {
        SplashScreenSkeleton(SplashUiState.Animating) {}
    }
}