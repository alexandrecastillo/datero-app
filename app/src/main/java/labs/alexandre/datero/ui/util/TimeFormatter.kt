package labs.alexandre.datero.ui.util

object TimeFormatter {

    fun transform(timeInMillis: Long): TimeFormat {
        return when {
            timeInMillis < Time.ONE_MINUTE_IN_MS -> {
                TimeFormat.Seconds(
                    seconds = getSecondsOfTimeInMs(timeInMillis)
                )
            }

            timeInMillis < Time.TEN_MINUTE_IN_MS -> {
                TimeFormat.MinutesAndSeconds(
                    minutes = getMinutesOfTimeInMs(timeInMillis),
                    seconds = getSecondsOfTimeInMs(timeInMillis)
                )
            }

            timeInMillis < Time.ONE_HOUR_IN_MS -> {
                TimeFormat.Minutes(
                    minutes = getMinutesOfTimeInMs(timeInMillis)
                )
            }

            else -> {
                TimeFormat.HoursAndMinutes(
                    hours = getHoursOfTimeInMs(timeInMillis),
                    minutes = getMinutesOfTimeInMs(timeInMillis)
                )
            }
        }
    }

    private fun getHoursOfTimeInMs(timeInMs: Long): Int {
        return (timeInMs / (3_600_000)).toInt()
    }

    private fun getMinutesOfTimeInMs(timeInMs: Long): Int {
        return ((timeInMs / 60_000) % 60).toInt()
    }

    private fun getSecondsOfTimeInMs(timeInMs: Long): Int {
        return ((timeInMs / 1000) % 60).toInt()
    }

    sealed class TimeFormat {
        data class Seconds(val seconds: Int) : TimeFormat()
        data class MinutesAndSeconds(val minutes: Int, val seconds: Int) : TimeFormat()
        data class Minutes(val minutes: Int) : TimeFormat()
        data class HoursAndMinutes(val hours: Int, val minutes: Int) : TimeFormat()
    }

    private object Time {
        const val ONE_MINUTE_IN_MS: Long = 60_000L
        const val TEN_MINUTE_IN_MS: Long = 600_000L
        const val ONE_HOUR_IN_MS: Long = 3_600_000L
    }

}