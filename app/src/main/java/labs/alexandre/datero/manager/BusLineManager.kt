package labs.alexandre.datero.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import labs.alexandre.datero.data.event.BusLineDataEvent
import labs.alexandre.datero.data.repository.BusLineRepository
import labs.alexandre.datero.domain.model.BusLine
import labs.alexandre.datero.tracker.BusTimestampTracker
import labs.alexandre.datero.tracker.TimestampBusLineParam

sealed class Event {

    data class InitialLoad(
        val busLines: List<BusLine>
    ) : Event()

    data class BusLineAdded(
        val busLine: BusLine
    ): Event()

    data class TimestampUpdate(
        val busLineId: Long,
        val timestampId: Long,
        val elapsedTime: Long
    ) : Event()

    data class TimestampAdded(
        val busLineId: Long,
        val timestampId: Long,
        val elapsedTime: Long
    ) : Event()

}

class BusLinesManager(
    private val busLineRepository: BusLineRepository,
    private val tracker: BusTimestampTracker
) {

    private val coroutineScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _events = Channel<Event>(capacity = Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun start() {
        coroutineScope.launch {
            watchEvents()
        }

        coroutineScope.launch {
            val busLines = busLineRepository.getBusLines()
            val timestamps = busLines.toTimestampsParam()
            tracker.subscribe(params = timestamps)

            _events.trySend(Event.InitialLoad(busLines))
            tracker.startTracking()
        }
    }

    fun watchEvents() {
        watchBusLines()
        watchTracker()
    }

    private fun watchBusLines() {
        coroutineScope.launch {
            busLineRepository.getBusLinesEvents()
                .collect { event ->
                    when (event) {
                        is BusLineDataEvent.BusLineAdded -> {
                            _events.trySend(Event.BusLineAdded(event.busLine))
                        }
                        is BusLineDataEvent.TimestampAdded -> {
                            _events.trySend(Event.TimestampAdded(event.busLineId, event.timestampId, event.timestamp))
                            
                            tracker.subscribe(
                                TimestampBusLineParam(
                                    event.busLineId,
                                    event.timestampId,
                                    event.timestamp
                                )
                            )
                            tracker.startTracking()
                        }
                    }
                }
        }
    }

    private fun watchTracker() {
        coroutineScope.launch {
            tracker.events.collect {
                _events.trySend(Event.TimestampUpdate(it.busLineId, it.timestampId, it.elapsedTime))
            }
        }
    }

    private fun List<BusLine>.toTimestampsParam(): List<TimestampBusLineParam> {
        return this.flatMap { busLine ->
            busLine.timestamps.map { timestamp ->
                TimestampBusLineParam(
                    busLineId = busLine.id,
                    timestampId = timestamp.id,
                    timestamp = timestamp.timestamp
                )
            }
        }
    }

}