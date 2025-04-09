package labs.alexandre.datero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import labs.alexandre.datero.ui.dashboard.DashboardScreen
import labs.alexandre.datero.ui.theme.DateroTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DateroTheme {
                DashboardScreen()
                /*
                DashboardScreenSkeleton(
                    uiState = DashboardUiState.Idle,
                    busLines = SnapshotStateMap<String, BusLineUiModel>(),
                    onBusLineClick = {},
                    onMarkBusLineClick = {},
                    onBusTimestampClick = {},
                    onAddBusLineClick = {}
                )
                 */
            }
        }
    }

}