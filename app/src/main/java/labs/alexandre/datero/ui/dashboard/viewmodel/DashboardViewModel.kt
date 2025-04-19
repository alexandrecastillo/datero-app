package labs.alexandre.datero.ui.dashboard.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import labs.alexandre.datero.manager.BusLinesManager
import labs.alexandre.datero.manager.Event
import labs.alexandre.datero.ui.dashboard.model.BusLineUiModel
import labs.alexandre.datero.ui.dashboard.model.BusTimestampUiModel
import labs.alexandre.datero.ui.dashboard.model.BusUiState
import labs.alexandre.datero.ui.dashboard.model.DashboardUiState
import labs.alexandre.datero.ui.dashboard.model.toUiModel
import javax.inject.Inject
import kotlin.collections.set
import kotlin.random.Random

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val busLinesManager: BusLinesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Idle)
    val uiState: StateFlow<DashboardUiState> = _uiState

    private val _busLines = mutableStateMapOf<String, BusLineUiModel>()
    val busLines: SnapshotStateMap<String, BusLineUiModel> = _busLines

    init {
        watchBusLines()
        startBusLines()
    }

    private fun startBusLines() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            busLinesManager.start()
        }
    }

    private fun watchBusLines() {
        viewModelScope.launch {
            busLinesManager.events.collect { event ->
                when (event) {
                    is Event.InitialLoad -> onInitialLoad(event)
                    is Event.BusLineAdded -> onBusLineAdded(event)
                    is Event.TimestampUpdate -> onTimestampUpdate(event)
                    is Event.TimestampAdded -> Unit
                }
            }
        }
    }

    private fun onInitialLoad(event: Event.InitialLoad) {
        viewModelScope.launch {
            _busLines.forEach {
                _busLines.remove(it.key)
            }
            event.busLines.forEach {
                _busLines[it.id.toString()] = it.toUiModel()
            }

            _uiState.value = DashboardUiState.Loaded
        }
    }

    fun onBusLineAdded(event: Event.BusLineAdded) {
        viewModelScope.launch {
            val busLine = event.busLine
            _busLines[busLine.id.toString()] = busLine.toUiModel()
        }
    }

    private fun onTimestampUpdate(update: Event.TimestampUpdate) {
        val keyBusLine = update.busLineId.toString()
        val keyTimestamp = update.timestampId.toString()

        val currentTimestamp = _busLines[keyBusLine]?.timestamps[keyTimestamp]

        currentTimestamp?.let {
            _busLines[keyBusLine]?.timestamps[keyTimestamp] =
                it.copy(elapsedTime = update.elapsedTime)
        }
    }

    fun onMarkTimestampBusLineClick(busLineId: String) {
        viewModelScope.launch {
            val randomLong = Random.nextLong()
            _busLines[busLineId]?.timestamps[randomLong.toString()] =
                BusTimestampUiModel(
                    id = randomLong.toString(),
                    timestamp = 0,
                    elapsedTime = Random.nextLong(1000, 5990000),
                    state = BusUiState.entries.random()
                )
        }
    }

}