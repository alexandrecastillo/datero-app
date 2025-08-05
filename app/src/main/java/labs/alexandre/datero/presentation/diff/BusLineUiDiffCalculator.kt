package labs.alexandre.datero.presentation.diff

import labs.alexandre.datero.domain.model.BusLine
import labs.alexandre.datero.domain.model.BusMark
import labs.alexandre.datero.presentation.mapper.BusLineUiMapper
import labs.alexandre.datero.presentation.mapper.BusOccupancyUiMapper
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.presentation.model.BusMarkUiModel
import javax.inject.Inject

class BusLineUiDiffCalculator @Inject constructor(
    private val busLineUiMapper: BusLineUiMapper,
    private val occupancyUiMapper: BusOccupancyUiMapper
) {

    fun calculate(
        current: List<BusLineUiModel>,
        domainList: List<BusLine>
    ): List<BusLineUiModel> {
        val currentMap = current.associateBy { it.id }

        return domainList.map { domain ->
            currentMap[domain.id]
                ?.takeUnless { hasChanged(it, domain) }
                ?: busLineUiMapper.mapToUiModel(domain)
        }
    }

    private fun hasChanged(ui: BusLineUiModel, domain: BusLine): Boolean {
        return ui.name != domain.name ||
                ui.colors != domain.colors ||
                ui.position != domain.position ||
                marksChanged(ui.marks, domain.marks)
    }

    private fun marksChanged(
        uiMarks: List<BusMarkUiModel>,
        domainMarks: List<BusMark>
    ): Boolean {
        if (uiMarks.size != domainMarks.size) return true

        return uiMarks
            .asSequence()
            .zip(domainMarks.asSequence())
            .any { (ui, dm) ->
                ui.id != dm.id ||
                ui.busLineId != dm.busLineId ||
                ui.timestamp != dm.timestamp ||
                ui.occupancy != occupancyUiMapper.mapToUiModel(dm.occupancy)
            }
    }

}

