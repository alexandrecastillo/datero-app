package labs.alexandre.datero.presentation.features.dashboard.components.busmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.presentation.model.BusMarkUiModel
import labs.alexandre.datero.presentation.ui.util.OnUpdateEffect

@Composable
fun ListBusMarks(
    busLine: BusLineUiModel,
    onIntent: (DashboardIntent) -> Unit
) {
    val lazyListState = rememberLazyListState()

    OnUpdateEffect(busLine.marks.map { it.id }) {
        if (lazyListState.firstVisibleItemIndex > 0 || lazyListState.firstVisibleItemScrollOffset != 0) {
            lazyListState.animateScrollToItem(0)
        }
    }

    LazyRow(
        state = lazyListState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(14.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(busLine.marks, key = { busMark -> busMark.id }) { busMark ->
            RenderBusMark(
                busLine = busLine,
                busMark = busMark,
                onIntent = onIntent
            )
        }
    }
}

@Composable
fun LazyItemScope.RenderBusMark(
    busLine: BusLineUiModel,
    busMark: BusMarkUiModel,
    onIntent: (DashboardIntent) -> Unit
) {
    when (busMark) {
        is BusMarkUiModel.Current -> {
            BusMarkCurrentItem(
                busMark = busMark,
                modifier = Modifier.animateItem(fadeInSpec = null),
                onProgressFinish = {
                    onIntent(DashboardIntent.OnBusMarkProgressFinish(busLine.id, busMark.id))
                }
            )
        }

        is BusMarkUiModel.Historical -> {
            BusMarkHistoricalItem(
                busMark = busMark,
                modifier = Modifier.animateItem()
            )
        }
    }
}