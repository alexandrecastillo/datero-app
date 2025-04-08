package labs.alexandre.datero.ui.util

object TimeFormatter {

    fun transform(elapsedTimeInMillis: Long): TimeFormat {
        return when {
            elapsedTimeInMillis < Time.ONE_MINUTE_IN_MS -> {
                TimeFormat.Seconds((elapsedTimeInMillis / 1000) % 60)
            }
            elapsedTimeInMillis < Time.ONE_HOUR_IN_MS -> {
                TimeFormat.Minutes((elapsedTimeInMillis / 60_000) % 60)
            }
            else -> {
                TimeFormat.HoursAndMinutes(
                    hours = elapsedTimeInMillis / (1000 * 60 * 60),
                    minutes = (elapsedTimeInMillis / 60_000) % 60
                )
            }
        }
    }

    sealed class TimeFormat {
        data class Seconds(val seconds: Long) : TimeFormat()
        data class Minutes(val minutes: Long) : TimeFormat()
        data class HoursAndMinutes(val hours: Long, val minutes: Long) : TimeFormat()
    }

    private object Time {
        const val ONE_MINUTE_IN_MS: Long = 60_000L
        const val ONE_HOUR_IN_MS: Long = 3_600_000L
    }

}