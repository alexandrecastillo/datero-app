package labs.alexandre.datero.presentation.root.snackbar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun DateroSnackbarListener(
    controller: DateroSnackbarController,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(controller) {
        controller.messages.collect { message ->
            snackbarHostState.showSnackbar(
                message = message
            )
        }
    }
}
