package labs.alexandre.datero.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class BusLineRepositoryImpl: BusLineRepository {

    override fun getBusLines(): Flow<String> {
        return emptyFlow()
    }

}