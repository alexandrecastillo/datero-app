package labs.alexandre.datero.ui.dashboard.viewmodel

import android.os.SystemClock
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import labs.alexandre.datero.ui.dashboard.model.BusLineUiModel
import labs.alexandre.datero.ui.dashboard.model.BusTimestampUiModel
import labs.alexandre.datero.ui.dashboard.model.BusUiState
import kotlin.random.Random

class DashboardViewModel() : ViewModel() {

    private val _busLines = mutableStateMapOf<String, BusLineUiModel>()
    val busLines: SnapshotStateMap<String, BusLineUiModel> = _busLines

    init {
        (1..10).forEach { lineId ->
            _busLines[lineId.toString()] = BusLineUiModel(
                id = lineId.toString(),
                name = "Línea $lineId",
                colors = listOf(),
                timestamps = SnapshotStateMap<String, BusTimestampUiModel>().apply {
                    (1..8).forEach { busId ->
                        this[busId.toString()] = BusTimestampUiModel(
                            id = busId.toString(),
                            elapsedTime = System.currentTimeMillis(),
                            BusUiState.LOW
                        )
                    }
                }
            )
        }
    }


    fun onMarkBusLineClick(busLineUiModel: BusLineUiModel) {
        _busLines[busLineUiModel.id]?.let { busLine ->
            busLine.timestamps[busLine.id] = BusTimestampUiModel("1", 1000L, BusUiState.FULL)// Agrega al inicio

            if (busLine.timestamps.size > 8) {
                val oldestKey = busLine.timestamps.keys.firstOrNull() // Obtener el más antiguo
                if (oldestKey != null) busLine.timestamps.remove(oldestKey)
            }
        }
    }

    private fun updateBusTimestamp(lineId: String, busId: String, newTimestamp: Long) {
        _busLines[lineId]?.timestamps?.set(busId, BusTimestampUiModel(busId, newTimestamp, BusUiState.LOW))
    }

    private fun removeBusLine(busLineId: String) {
        _busLines[busLineId]?.timestamps?.remove(_busLines[busLineId]?.timestamps?.minBy { it.value.elapsedTime }?.key)

        _busLines.remove(busLineId)
    }

    fun start() {
        viewModelScope.launch {
            while (true) {
                val lineId = Random.nextInt(1, 11).toString()
                val busId = Random.nextInt(1, 9).toString()

                updateBusTimestamp(lineId, busId, SystemClock.elapsedRealtime())

                delay(1000L)
            }
        }
    }

}