package labs.alexandre.datero.ui.util

import android.content.Context
import dagger.hilt.android.EntryPointAccessors

fun <T> Context.getProvidesFromEntryPoint(entryPoint: Class<T>): T {
    return EntryPointAccessors.fromApplication(
        context = applicationContext,
        entryPoint = entryPoint
    )
}