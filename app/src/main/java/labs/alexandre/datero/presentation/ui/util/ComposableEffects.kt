package labs.alexandre.datero.presentation.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope

@Composable
fun OnUpdateEffect(
    key1: Any, vararg keys: Any, block: suspend CoroutineScope.() -> Unit
) {
    var hasLaunched by remember { mutableStateOf(false) }
    val allKeys = listOf(key1, *keys).toTypedArray()

    LaunchedEffect(*allKeys) {
        if (hasLaunched) {
            block()
        } else {
            hasLaunched = true
        }
    }
}