package labs.alexandre.datero.ui.dashboard

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import labs.alexandre.datero.R
import labs.alexandre.datero.ui.dashboard.components.BusLineListPlaceholder
import labs.alexandre.datero.ui.dashboard.components.BusLinesContent
import labs.alexandre.datero.ui.dashboard.model.BusLineUiModel
import labs.alexandre.datero.ui.dashboard.model.BusTimestampUiModel
import labs.alexandre.datero.ui.dashboard.model.BusUiState
import labs.alexandre.datero.ui.dashboard.model.DashboardUiState
import labs.alexandre.datero.ui.dashboard.viewmodel.DashboardViewModel
import labs.alexandre.datero.ui.theme.DateroTheme
import kotlin.random.Random

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by dashboardViewModel.uiState.collectAsStateWithLifecycle()
    val busLinesList = dashboardViewModel.busLines

    DashboardScreenSkeleton(
        uiState = uiState,
        busLines = busLinesList,
        onBusLineClick = {
            // redireccionar a detalle bus line
            Log.d("TAG", "DashboardScreen: OnBusLineClick")
        },
        onMarkBusLineClick = {
            dashboardViewModel.onMarkTimestampBusLineClick(it.id)
            // abrir popup marcar tiempo
        },
        onBusTimestampClick = {
            // abrir popup detalle timestamp
            Log.d("TAG", "DashboardScreen: onBusTimestampClick")
        },
        onAddBusLineClick = {
            // redireccionar a creacion de bus
            Log.d("TAG", "DashboardScreen: onAddBusLineClick")
        }
    )
}

@Composable
fun DashboardScreenSkeleton(
    uiState: DashboardUiState,
    busLines: SnapshotStateMap<String, BusLineUiModel>,
    onBusLineClick: (BusLineUiModel) -> Unit,
    onMarkBusLineClick: (BusLineUiModel) -> Unit,
    onBusTimestampClick: (BusTimestampUiModel) -> Unit,
    onAddBusLineClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.dashboard_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge
        )

        Crossfade(uiState) { uiState ->
            when (uiState) {
                DashboardUiState.Idle -> Unit
                DashboardUiState.Loading -> {
                    BusLineListPlaceholder(
                        modifier = Modifier
                            .weight(1F)
                    )
                }
                DashboardUiState.Loaded -> {
                    BusLinesContent(
                        modifier = Modifier
                            .weight(1F),
                        busLines = busLines,
                        onBusLineClick = onBusLineClick,
                        onMarkBusLineClick = onMarkBusLineClick,
                        onBusTimestampClick = onBusTimestampClick,
                        onAddBusLineClick = onAddBusLineClick
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewDashboardScreen() {
    val busLinesList = SnapshotStateMap<String, BusLineUiModel>().apply {
        (1..8).forEach { lineId ->
            this[lineId.toString()] = BusLineUiModel(
                id = lineId.toString(),
                name = "Línea $lineId",
                colors = listOf(
                    "#FFD73939",
                    "#FF196320",
                    "#FF20882B",
                    "#FFF8F8F8",
                    "#FFD73939"
                ),
                position = 0,
                timestamps = SnapshotStateMap<String, BusTimestampUiModel>().apply {
                    (1..8).forEach { busId ->
                        this[busId.toString()] = BusTimestampUiModel(
                            id = busId.toString(),
                            timestamp = 0L,
                            elapsedTime = Random.nextLong(1000, 1800000),
                            state = BusUiState.entries.random()
                        )
                    }
                }
            )
        }
    }

    DateroTheme {
        DashboardScreenSkeleton(
            uiState = DashboardUiState.Loaded,
            busLines = busLinesList,
            onBusLineClick = {},
            onMarkBusLineClick = {},
            onBusTimestampClick = {},
            onAddBusLineClick = {}
        )
    }
}