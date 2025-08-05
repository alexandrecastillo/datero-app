package labs.alexandre.datero.presentation.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import labs.alexandre.datero.presentation.root.providers.DateroRootProviders
import labs.alexandre.datero.presentation.root.snackbar.DateroSnackbarController
import labs.alexandre.datero.presentation.root.snackbar.DateroSnackbarHost
import labs.alexandre.datero.presentation.root.snackbar.DateroSnackbarListener
import labs.alexandre.datero.presentation.root.viewmodel.DateroRootViewModel

@Composable
fun DateroRootScreen(
    dateroRootViewModel: DateroRootViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarController = remember { DateroSnackbarController() }

    DateroSnackbarListener(snackbarController, snackbarHostState)

    DateroRootProviders(snackbarController) {
        DateroRootSkeleton(snackbarHostState = snackbarHostState) {
            content()
        }
    }
}

@Composable
fun DateroRootSkeleton(
    snackbarHostState: SnackbarHostState,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()
        DateroSnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState
        )
    }
}
