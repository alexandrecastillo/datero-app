package labs.alexandre.datero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.snapshots.SnapshotStateMap
import labs.alexandre.datero.ui.dashboard.DashboardScreenSkeleton
import labs.alexandre.datero.ui.dashboard.model.BusLineUiModel
import labs.alexandre.datero.ui.theme.DateroTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DateroTheme {
                DashboardScreenSkeleton(
                    busLines = SnapshotStateMap<String, BusLineUiModel>(),
                    onBusLineClick = {},
                    onMarkBusLineClick = {},
                    onBusTimestampClick = {},
                    onAddBusLineClick = {}
                )
            }
        }
    }

}