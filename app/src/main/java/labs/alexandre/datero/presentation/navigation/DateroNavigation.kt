package labs.alexandre.datero.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import labs.alexandre.datero.presentation.features.busLineDetail.BusLineDetailScreen
import labs.alexandre.datero.presentation.features.busLines.BusLinesScreen
import labs.alexandre.datero.presentation.features.dashboard.DashboardScreen
import labs.alexandre.datero.presentation.features.splash.SplashScreen

@Composable
fun DateroNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = RootRoutes.Splash) {
        composable<RootRoutes.Splash> {
            SplashScreen(
                navToDashboard = {
                    navController.navigate(
                        route = RootRoutes.Dashboard,
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(RootRoutes.Splash, inclusive = true)
                            .build()
                    )
                }
            )
        }
        composable<RootRoutes.Dashboard> {
            DashboardScreen(
                navToCreateBusLine = {
                    navController.navigate(RootRoutes.BusLineDetail())
                },
                navToBusLines = {
                    navController.navigate(RootRoutes.BusLines)
                }
            )
        }
        composable<RootRoutes.BusLines> {
            BusLinesScreen(
                navToBack = {
                    navController.navigateUp()
                },
                navToBusLineDetail = { busLineId ->
                    navController.navigate(RootRoutes.BusLineDetail(busLineId = busLineId))
                }
            )
        }
        composable<RootRoutes.BusLineDetail>(
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 500)
                )
            },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 500)
                )
            }
        ) {
            BusLineDetailScreen {
                navController.navigateUp()
            }
        }
    }
}