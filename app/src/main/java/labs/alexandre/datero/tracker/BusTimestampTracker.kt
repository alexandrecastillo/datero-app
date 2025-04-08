package labs.alexandre.datero.tracker

import android.os.SystemClock
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TreeSet
import kotlin.math.max

data class BusParam(
    val id: String, val name: String, val timestamp: Long
)

data class BusTrack(
    val id: String, val name: String, val timestamp: Long, val triggerAtElapsedSinceBoot: Long
)

class BusTimestampTracker(
    private val timestampSnapshot: TimestampSnapshot
) {
    private val busesQueue: TreeSet<BusTrack> =
        TreeSet<BusTrack>(compareBy { it.triggerAtElapsedSinceBoot })

    private val eventChannel = Channel<Unit>(Channel.CONFLATED) // Notificación única
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private var trackingJob: Job? = null

    private val mutex: Mutex = Mutex()

    fun track(param: BusParam) {
        val track = BusTrack(
            param.id,
            param.name,
            param.timestamp,
            getTriggerAtElapsedSinceBoot(param.timestamp)
        )
        busesQueue.add(track)

        tracking()
    }

    fun unTrackBus(busId: String) {
        scope.launch {
            mutex.withLock {
                val iterator = busesQueue.iterator()
                while (iterator.hasNext()) {
                    if (iterator.next().id == busId) {
                        iterator.remove()
                    }
                }
            }
        }
    }

    private fun tracking() {
        if (trackingJob?.isActive == true) return

        trackingJob = scope.launch {
            while (busesQueue.isNotEmpty()) {
                // obtenemos el bus mas proximo a recibir el update
                val currentBus = busesQueue.first()

                // calculamos cuanto tiempo falta para su ejecucion
                // de evento, el cual no se puede ser menor a cero
                val timeLeftToTrigger = max(
                    ZERO_MS,
                    currentBus.triggerAtElapsedSinceBoot.minus(SystemClock.elapsedRealtime())
                )

                // hacemos el delay
                delay(timeLeftToTrigger)

                // ejecutamos el evento
                // aqui debe emitirse el evento
                val elapsedTimeBusTimestamp = System.currentTimeMillis() - currentBus.timestamp


                Log.e(
                    "BRIANA",
                    "Bus: ${currentBus.name} - " + elapsedTimeBusTimestamp
                )

                // eliminamos el bus actual
                busesQueue.remove(currentBus)

                // programamos su proximo evento
                val newTrack = currentBus.copy(
                    triggerAtElapsedSinceBoot = getTriggerAtElapsedSinceBoot(currentBus.timestamp)
                )
                busesQueue.add(newTrack)
            }
        }
    }

    private fun getTriggerAtElapsedSinceBoot(timestampBus: Long): Long {
        // tiempo transcurrido desde que se encendio el dispositivo
        val currentElapsedSinceBoot =
            SystemClock.elapsedRealtime() // 3 horas o 15 segundos o 47 minutos

        // hora que da actualmente el sistema
        val currentSystemTimestamp = System.currentTimeMillis() // 12:30:23.540

        // tiempo transcurrido desde que se grabo el snapshot elapsed boot y el system timestamp
        val elapsedTimeSinceBootSnapshot =
            currentElapsedSinceBoot.minus(timestampSnapshot.elapsedSinceBoot) // 1 hora o 30 segundos o 10 minutos o etc

        // hora que dio en el sistema cuando se tomo el snapshot
        val systemTimestampSnapshot = timestampSnapshot.systemTimestamp // 10:20:45.100

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
            elapsedTimeFromBusTimestamp < ONE_HOUR_MS -> {
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

data class TimestampSnapshot(
    val elapsedSinceBoot: Long, // Tiempo transcurrido desde el arranque en el momento de la captura
    val systemTimestamp: Long   // Marca de tiempo absoluta en el momento de la captura
)

const val ZERO_MS = 0L
const val FIVE_SECONDS_MS = 5000L
const val ONE_HOUR_MS = 3600000L
const val SECONDS_59 = 59000L
const val ONE_SECOND_MS = 1000L

val currentTime: Calendar = Calendar.getInstance().apply {
    set(Calendar.HOUR, 11)
    set(Calendar.MINUTE, 10)
    set(Calendar.SECOND, 17)
    set(Calendar.MILLISECOND, 200)
}

val busTimestamp: Calendar = Calendar.getInstance().apply {
    set(Calendar.HOUR, 9)
    set(Calendar.MINUTE, 10)
    set(Calendar.SECOND, 20)
    set(Calendar.MILLISECOND, 0)
}

fun main() {
    val busTimestampTracker = BusTimestampTracker(TimestampSnapshot(0L, 0L))

    val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS", Locale.getDefault())
    println("bus: ${formatter.format(busTimestamp.timeInMillis)}")
    println("current: ${formatter.format(currentTime.timeInMillis)}")

    val timeInMs =
        busTimestampTracker.getMsToNextTick(currentTime.timeInMillis, busTimestamp.timeInMillis)
    println("timeInMs: $timeInMs")
}