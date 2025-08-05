package labs.alexandre.datero.domain.provider

interface SystemClockProvider {

    fun elapsedRealtime(): Long

}