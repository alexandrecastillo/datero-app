package labs.alexandre.datero.presentation.features.dashboard.components.busline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.presentation.theme.DateroTheme
import labs.alexandre.datero.presentation.ui.preview.Data

@Composable
fun ListBusLines(
    busLines: List<BusLineUiModel>,
    modifier: Modifier = Modifier,
    onIntent: (DashboardIntent) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.then(modifier),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 152.dp)
    ) {
        items(busLines, key = { it.id }) { busLine ->
            ItemBusLine(
                busLine = busLine,
                modifier = Modifier.animateItem(),
                onIntent = { intent -> onIntent(intent) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewListBusLines() {
    DateroTheme {
        ListBusLines(
            busLines = Data.busLines, onIntent = {})
    }
}

@Composable
fun ListBusLinesPlaceholder() {
    LazyColumn(
        modifier = Modifier.shimmer(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        items(8) {
            CardBusLinePlaceholder()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewListBusLinesPlaceholder() {
    DateroTheme {
        ListBusLinesPlaceholder()
    }
}