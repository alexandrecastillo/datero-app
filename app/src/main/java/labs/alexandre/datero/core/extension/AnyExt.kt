package labs.alexandre.datero.core.extension

fun Any.toLongOrZero(): Long {
    return toString().toLongOrNull() ?: 0L
}