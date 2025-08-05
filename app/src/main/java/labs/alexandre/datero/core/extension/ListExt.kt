package labs.alexandre.datero.core.extension

inline fun <T> List<T>.indexOfFirstOrNull(predicate: (T) -> Boolean): Int? = indexOfFirst(predicate).takeIf { it != -1 }
