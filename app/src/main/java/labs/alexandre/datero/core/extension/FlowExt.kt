@file:OptIn(kotlinx.coroutines.FlowPreview::class)
package labs.alexandre.datero.core.extension


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce

fun <T> Flow<T>.defaultDebounce() = debounce(300)