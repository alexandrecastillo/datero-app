package labs.alexandre.datero.util

import java.time.LocalDateTime
import java.time.ZoneOffset

fun fixedTimestamp(
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int,
    second: Int
): Long {
    return LocalDateTime.of(year, month, day, hour, minute, second)
        .atZone(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()
}
