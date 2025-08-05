package labs.alexandre.datero.presentation.ui.preview

import labs.alexandre.datero.domain.model.Cycle
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.presentation.model.BusMarkUiModel
import labs.alexandre.datero.presentation.model.BusOccupancyUiLevel
import labs.alexandre.datero.presentation.model.ElapsedTimeUiModel
import kotlin.random.Random

object Data {

    val busLines = listOf<BusLineUiModel>(
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "Chama",
            colors = listOf("#D73939", "#196320", "#19AE28", "#FFFFFF", "#D73939"),
            position = 0,
            marks = listOf(
                BusMarkUiModel.Current(
                    id = Random.Default.nextLong(),
                    busLineId = Random.Default.nextLong(),
                    timestamp = System.currentTimeMillis() - 120_000L,
                    occupancy = BusOccupancyUiLevel.MEDIUM,
                    elapsedTime = ElapsedTimeUiModel("3", "min"),
                    cycle = Cycle(0, 0)
                )
            ),
        ),
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "Chosicano",
            colors = listOf("#FFFFFF", "#2DAF3A", "#196320"),
            position = 0,
            marks = emptyList()
        ),
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "La E",
            colors = listOf("#FFFFFF", "#F9A825"),
            position = 0,
            marks = emptyList(),
        ),
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "La T",
            colors = listOf("#992850"),
            position = 0,
            marks = emptyList(),
        ),
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "La 73",
            colors = listOf("#B5DB14"),
            position = 0,
            marks = emptyList(),
        ),
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "La X",
            colors = listOf("#B5DB14"),
            position = 0,
            marks = emptyList(),
        ),
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "La Z",
            colors = listOf("#B5DB14"),
            position = 0,
            marks = emptyList(),
        ),
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "La T",
            colors = listOf("#992850"),
            position = 0,
            marks = emptyList(),
        ),
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "La 73",
            colors = listOf("#B5DB14"),
            position = 0,
            marks = emptyList(),
        ),
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "La X",
            colors = listOf("#B5DB14"),
            position = 0,
            marks = emptyList(),
        ),
        BusLineUiModel(
            id = Random.Default.nextLong(),
            name = "La Z",
            colors = listOf("#B5DB14"),
            position = 0,
            marks = emptyList(),
        )
    )

}