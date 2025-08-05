package labs.alexandre.datero.presentation.model

data class BusLineUiModel(
    val id: Long,
    val name: String,
    val colors: List<String>,
    val position: Int,
    val marks: List<BusMarkUiModel>
) {
    companion object {
        val EMPTY = BusLineUiModel(
            id = 0,
            name = "",
            colors = emptyList(),
            position = 0,
            marks = emptyList()
        )
    }
}
