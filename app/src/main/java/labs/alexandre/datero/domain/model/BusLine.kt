package labs.alexandre.datero.domain.model

data class BusLine(
    val id: Long,
    val name: String,
    val colors: List<String>,
    val timestamps: List<Timestamp>
)
