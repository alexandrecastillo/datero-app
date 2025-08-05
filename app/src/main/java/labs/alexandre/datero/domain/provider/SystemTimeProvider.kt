package labs.alexandre.datero.domain.provider

interface SystemTimeProvider {

    fun getCurrentTime(): Long

}