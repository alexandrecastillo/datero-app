package labs.alexandre.datero.tracker

import android.os.SystemClock
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import labs.alexandre.datero.manager.TimestampSnapshotManager
import java.util.TreeSet
import kotlin.math.max

data class TimestampBusLineParam(
    val busLineId: Long,
    val timestampId: Long,
    val timestamp: Long
)

data class TimestampTrack(
    val busLineId: Long,
    val timestampId: Long,
    val timestamp: Long,
    val triggerAtElapsedSinceBoot: Long
)

data class UpdateTimestamp(
    val busLineId: Long,
    val timestampId: Long,
    val timestamp: Long,
    val elapsedTime: Long
)

class BusTimestampTracker(
    private val timestampSnapshotManager: TimestampSnapshotManager
) {
    private val busesQueue: TreeSet<TimestampTrack> =
        TreeSet<TimestampTrack>(compareBy { it.triggerAtElapsedSinceBoot })

    private val _events = Channel<UpdateTimestamp>(capacity = Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private var trackingJob: Job? = null

    private val mutex: Mutex = Mutex()

    suspend fun subscribe(param: TimestampBusLineParam) {
        subscribe(listOf(param))
    }

    suspend fun subscribe(params: List<TimestampBusLineParam>) {
        mutex.withLock {
            params.forEach { param ->
                val track = TimestampTrack(
                    busLineId = param.busLineId,
                    timestampId = param.timestampId,
                    timestamp = param.timestamp,
                    triggerAtElapsedSinceBoot = getTriggerAtElapsedSinceBoot(param.timestamp)
                )
                busesQueue.add(track)
            }
        }
    }

    fun startTracking() {
        if (trackingJob?.isActive == true) return

        trackingJob = scope.launch {
            while (true) {
                val currentBus = mutex.withLock {
                    if (busesQueue.isEmpty()) return@launch
                    busesQueue.first()
                }

                val timeLeftToTrigger = max(
                    ZERO_MS,
                    currentBus.triggerAtElapsedSinceBoot - SystemClock.elapsedRealtime()
                )

                delay(timeLeftToTrigger)

                val elapsedTime = System.currentTimeMillis() - currentBus.timestamp

                _events.trySend(
                    UpdateTimestamp(
                        busLineId = currentBus.busLineId,
                        timestampId = currentBus.timestampId,
                        timestamp = currentBus.timestamp,
                        elapsedTime = elapsedTime
                    )
                )

                mutex.withLock {
                    busesQueue.remove(currentBus)

                    val newTrack = currentBus.copy(
                        triggerAtElapsedSinceBoot = getTriggerAtElapsedSinceBoot(currentBus.timestamp)
                    )
                    busesQueue.add(newTrack)
                }
            }
        }
    }

    private suspend fun getTriggerAtElapsedSinceBoot(timestampBus: Long): Long {
        // tiempo transcurrido desde que se encendio el dispositivo
        val currentElapsedSinceBoot = SystemClock.elapsedRealtime() // 3h, 15seg, 47min

        // hora que da actualmente el sistema
        val currentSystemTimestamp = System.currentTimeMillis() // 12:30:23.540

        // tiempo transcurrido desde que se grabo el snapshot elapsed boot y el system timestamp
        val elapsedTimeSinceBootSnapshot =
            currentElapsedSinceBoot.minus(timestampSnapshotManager.getElapsedSinceBoot()) // 1 hora o 30 segundos o 10 minutos o etc

        // hora que dio en el sistema cuando se tomo el snapshot
        val systemTimestampSnapshot =
            timestampSnapshotManager.getSystemTimestampBoot() // 10:20:45.100

        // hora que deberia dar el sistema actualmente
        val correctCurrentTimestamp =
            systemTimestampSnapshot.plus(elapsedTimeSinceBootSnapshot) // 11:15:36.678

        // rango de 5 segundos mas o menos hora correcta actual
        val rangeCorrectCurrentTimestamp =
            correctCurrentTimestamp.minus(
                FIVE_SECONDS_MS
            )..correctCurrentTimestamp.plus(
                FIVE_SECONDS_MS
            )

        // validar que este en el rango
        if (currentSystemTimestamp !in rangeCorrectCurrentTimestamp) {
            trackingJob?.cancel()

            Log.e("BRIANA", "SE DETECTO CAMBIO DE HORA")
        }

        // tiempo en ms que debe pasar hasta su siguiente update tick en vista
        val msToNextTick =
            getMsToNextTick(currentSystemTimestamp, timestampBus)

        // tiempo transcurrido desde que se prendio el celular
        // mas el tiempo que debe pasar para que se ejecute el update tick
        val triggerAtElapsedSinceBoot = currentElapsedSinceBoot + msToNextTick

        println(triggerAtElapsedSinceBoot)

        return triggerAtElapsedSinceBoot
    }

    fun getMsToNextTick(
        currentSystemTime: Long,
        timestampBus: Long
    ): Long {
        // tiempo que ha transcurrido desde que paso el bus
        val elapsedTimeFromBusTimestamp = currentSystemTime.minus(timestampBus)

        return when {
            // si el tiempo desde que paso el bus es menor a una hora entonces el update es cada
            // segundo por lo que se obtiene las milesimas de cuanto falta para el siguiente segundo
            elapsedTimeFromBusTimestamp < TEN_MINUTE_IN_MS -> {
                getMillisecondsNextTick(currentSystemTime, timestampBus)
            }
            // si el tiempo desde que paso el bus es mayor o igual a un minuto entonces el update es cada
            // minuto por lo que se obtiene los segundos y milesimas de cuanto falta para el siguiente minuto
            else -> {
                getSecondsInMillisNextTick(currentSystemTime, timestampBus)
                    .plus(getMillisecondsNextTick(currentSystemTime, timestampBus))
            }
        }
    }

    // obtener los segundos que faltan para coincidir con los mismos segundos
    // de la hora que paso el bus
    fun getSecondsInMillisNextTick(
        currentSystemTime: Long,
        busTimestamp: Long
    ): Long {
        val currentSystemTimeSeconds = currentSystemTime.getSeconds() // 10:20:30.200
        val busTimestampSeconds = busTimestamp.getSeconds() // 10:14:20.800

        val seconds = when {
            currentSystemTimeSeconds < busTimestampSeconds -> {
                busTimestampSeconds.minus(currentSystemTimeSeconds).toLong()
            }

            else -> {
                SECONDS_59.minus(currentSystemTimeSeconds)
                    .plus(busTimestampSeconds)
            }
        }

        return seconds * 1000L
    }

    // obtener los milisegundos que faltan para coincidir con los mismos milisegundos
    // de la hora que paso el bus
    fun getMillisecondsNextTick(
        currentSystemTime: Long,
        busTimestamp: Long
    ): Long {
        val busTimestampMilliseconds = busTimestamp.getMilliseconds()
        val currentSystemTimeMilliseconds = currentSystemTime.getMilliseconds()

        val milliseconds = when {
            // si los millisegundos del tiempo actual son menores a los del bus
            // restar los millisegundos del bus con los de la hora actual
            currentSystemTimeMilliseconds < busTimestampMilliseconds -> {
                busTimestampMilliseconds.minus(currentSystemTimeMilliseconds).toLong()
            }
            // si son iguales se quedan en 0
            currentSystemTimeMilliseconds == busTimestampMilliseconds -> 0L
            // sino
            else -> {
                ONE_SECOND_MS.minus(currentSystemTimeMilliseconds)
                    .plus(busTimestampMilliseconds)
            }
        }

        println("next tick milliseconds: $milliseconds")

        return milliseconds
    }

    private fun Long.getSeconds(): Int {
        return ((this / 1000) % 60).toInt()
    }

    private fun Long.getMilliseconds(): Int {
        return (this % 1000).toInt()
    }

}

const val ZERO_MS = 0L
const val FIVE_SECONDS_MS = 5000L
const val SECONDS_59 = 59000L
const val ONE_SECOND_MS = 1000L
const val TEN_MINUTE_IN_MS: Long = 600_000L