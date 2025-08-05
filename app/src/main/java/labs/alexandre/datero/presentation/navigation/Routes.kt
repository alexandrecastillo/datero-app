package labs.alexandre.datero.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface RootRoutes {
    @Serializable
    data object Splash: RootRoutes

    @Serializable
    data object Dashboard: RootRoutes

    @Serializable
    data class BusLineDetail(val busLineId: Long? = null): RootRoutes

    @Serializable
    data object BusLines: RootRoutes
}
