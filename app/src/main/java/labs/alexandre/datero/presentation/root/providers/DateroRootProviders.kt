package labs.alexandre.datero.presentation.root.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import labs.alexandre.datero.di.provideSystemClockProvider
import labs.alexandre.datero.domain.provider.SystemClockProvider
import labs.alexandre.datero.presentation.root.snackbar.DateroSnackbarController

@Composable
fun DateroRootProviders(
    snackbarController: DateroSnackbarController,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val systemClockProvider = remember { provideSystemClockProvider(context) }

    CompositionLocalProvider(
        LocalDateroSnackbarController provides snackbarController,
        LocalSystemClock provides systemClockProvider
    ) {
        content()
    }
}

val LocalDateroSnackbarController = staticCompositionLocalOf<DateroSnackbarController> {
    error("DateroSnackbarController not provided")
}

val LocalSystemClock = staticCompositionLocalOf<SystemClockProvider> {
    error("No SystemClockProvider provided")
}
