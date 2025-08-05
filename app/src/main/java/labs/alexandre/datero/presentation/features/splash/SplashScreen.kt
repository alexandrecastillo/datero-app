package labs.alexandre.datero.presentation.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.features.splash.effect.SplashEffect
import labs.alexandre.datero.presentation.features.splash.viewmodel.SplashViewModel
import labs.alexandre.datero.presentation.theme.DateroTheme

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    navToDashboard: () -> Unit
) {

    LaunchedEffect(splashViewModel.splashEffect) {
        splashViewModel.splashEffect.collect { effect ->
            when (effect) {
                SplashEffect.NavigateToDashboard -> navToDashboard()
            }
        }
    }

    SplashScreenSkeleton()
}

@Composable
fun SplashScreenSkeleton() {
    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_side_bus),
                contentDescription = stringResource(R.string.splash_content_description_image),
                modifier = Modifier.align(Alignment.Center)
            )
            Text(
                text = stringResource(R.string.splash_credits),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewSplashScreen() {
    DateroTheme {
        SplashScreenSkeleton()
    }
}