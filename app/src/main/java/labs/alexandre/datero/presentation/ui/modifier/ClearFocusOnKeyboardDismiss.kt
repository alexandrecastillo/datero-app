package labs.alexandre.datero.presentation.ui.modifier

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import labs.alexandre.datero.presentation.ui.util.isImeVisible

fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    val imeVisible = isImeVisible()

    var wasFocused by remember { mutableStateOf(false) }
    var keyboardWasVisible by remember { mutableStateOf(false) }

    LaunchedEffect(imeVisible, wasFocused) {
        if (wasFocused && !imeVisible && keyboardWasVisible) {
            focusManager.clearFocus()
        }

        if (wasFocused) {
            keyboardWasVisible = imeVisible
        }
    }

    onFocusChanged { focusState ->
        wasFocused = focusState.isFocused
        if (!focusState.isFocused) {
            keyboardWasVisible = false
        }
    }
}
