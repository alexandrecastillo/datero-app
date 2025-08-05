package labs.alexandre.datero.presentation.features.dashboard.components.dialog

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.features.dashboard.state.MarkTimestampUiState
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.presentation.model.BusOccupancyUiLevel
import labs.alexandre.datero.presentation.theme.Black100
import labs.alexandre.datero.presentation.theme.DateroTheme
import labs.alexandre.datero.presentation.theme.Gray50
import labs.alexandre.datero.presentation.ui.component.BusLineFlag
import labs.alexandre.datero.presentation.ui.component.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkBusLineTimestampBottomSheet(
    state: MarkTimestampUiState,
    onSubmit: () -> Unit,
    onDismiss: () -> Unit,
    onSelected: (BusOccupancyUiLevel) -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { value -> true }
    )

    ModalBottomSheet(
        sheetState = modalBottomSheetState,
        onDismissRequest = onDismiss
    ) {
        FormMarkTimestamp(
            state = state,
            onSubmit = onSubmit,
            onSelected = onSelected
        )
    }
}

@Composable
fun FormMarkTimestamp(
    state: MarkTimestampUiState,
    onSubmit: () -> Unit,
    onSelected: (BusOccupancyUiLevel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeaderBusLine(busLine = state.busLine)
        Spacer(
            modifier = Modifier
                .height(36.dp)
        )
        Text(
            text = stringResource(R.string.dashboard_dialog_mark_timestamp_button_title_question),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 32.dp)
        )
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        Answers(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            selected = state.occupancy,
            onSelected = onSelected
        )
        Spacer(
            modifier = Modifier
                .height(56.dp)
        )
        PrimaryButton(
            text = stringResource(R.string.dashboard_dialog_mark_timestamp_button_text_confirm_mark),
            enabled = state.occupancy != null,
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            onClick = onSubmit
        )
        Spacer(
            modifier = Modifier
                .height(52.dp)
        )
    }
}

@Composable
fun HeaderBusLine(busLine: BusLineUiModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 32.dp)
    ) {
        BusLineFlag(
            colors = busLine.colors
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = busLine.name,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun Answers(
    modifier: Modifier = Modifier,
    selected: BusOccupancyUiLevel?,
    onSelected: (state: BusOccupancyUiLevel) -> Unit
) {
    Column(
        modifier = Modifier
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BusOccupancyUiLevel.entries.forEach { state ->
            Answer(
                state = state,
                isSelected = selected == state,
                onClick = onSelected,
            )
        }
    }
}

@Composable
fun Answer(
    state: BusOccupancyUiLevel,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (BusOccupancyUiLevel) -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Black100 else Gray50,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable { onClick.invoke(state) }
            .padding(horizontal = 32.dp, vertical = 20.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(state.labelRes),
            modifier = Modifier.weight(1F),
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            painter = painterResource(state.iconRes),
            contentDescription = stringResource(R.string.dashboard_dialog_mark_timestamp_content_description_icon_bus_occupancy),
            tint = state.color,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Preview
@Composable
fun PreviewAnswer() {
    DateroTheme {
        Answer(
            state = BusOccupancyUiLevel.LOW,
            isSelected = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewDialogMarkBusLineTimestamp() {
    DateroTheme {
        FormMarkTimestamp(
            state = MarkTimestampUiState(
                busLine = BusLineUiModel(
                    id = 1,
                    "La Chama",
                    colors = listOf(
                        "#FFD73939",
                        "#FF196320",
                        "#FF20882B",
                        "#FFF8F8F8",
                        "#FFD73939"
                    ),
                    position = 0,
                    marks = emptyList()
                ),
                occupancy = null,
                showDialog = true
            ),
            onSelected = {},
            onSubmit = {}
        )
    }
}