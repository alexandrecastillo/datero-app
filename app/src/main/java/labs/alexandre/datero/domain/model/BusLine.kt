package labs.alexandre.datero.domain.model

data class BusLine(
    val id: Long? = null,
    val name: String,
    val colors: List<String>,
    val position: Int = 0,
    val marks: List<BusMark> = emptyList()
)
