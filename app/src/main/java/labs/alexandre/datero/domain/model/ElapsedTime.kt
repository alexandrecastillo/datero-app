package labs.alexandre.datero.domain.model

data class ElapsedTime(
    val time: Int,
    val unit: TimeUnit
)

enum class TimeUnit {
    MINUTES, HOURS
}
