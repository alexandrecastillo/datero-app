package labs.alexandre.datero.presentation.root.snackbar

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DateroSnackbarController {
    private val _messages = MutableSharedFlow<String>()
    val messages = _messages.asSharedFlow()

    suspend fun show(message: String) {
        _messages.emit(message)
    }
}