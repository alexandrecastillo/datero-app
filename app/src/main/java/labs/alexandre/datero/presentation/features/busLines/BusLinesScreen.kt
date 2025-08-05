package labs.alexandre.datero.presentation.features.busLines

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.features.busLines.components.BusLinesList
import labs.alexandre.datero.presentation.features.busLines.components.EmptyBusLines
import labs.alexandre.datero.presentation.features.busLines.effect.BusLinesEffect
import labs.alexandre.datero.presentation.features.busLines.intent.BusLinesIntent
import labs.alexandre.datero.presentation.features.busLines.state.BusLinesUiState
import labs.alexandre.datero.presentation.features.busLines.state.ContentState
import labs.alexandre.datero.presentation.features.busLines.viewmodel.BusLinesViewModel
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.presentation.theme.DateroTheme

@Composable
fun BusLinesScreen(
    busLinesViewModel: BusLinesViewModel = hiltViewModel(),
    navToBack: () -> Unit,
    navToBusLineDetail: (busLineId: Long?) -> Unit
) {
    val uiState by busLinesViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(busLinesViewModel.effect) {
        busLinesViewModel.effect.collect { effect ->
            when (effect) {
                is BusLinesEffect.NavigateToBusLineDetail -> navToBusLineDetail(effect.busLineId)
                BusLinesEffect.NavigateToBack -> navToBack()
            }
        }
    }

    BusLinesScreenSkeleton(
        uiState = uiState,
        onIntent = { intent -> busLinesViewModel.onIntent(intent) }
    )

    if (uiState.showConfirmDelete) {
        DialogConfirmDelete(
            busLine = uiState.busLineToDelete,
            onIntent = { intent -> busLinesViewModel.onIntent(intent) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusLinesScreenSkeleton(
    uiState: BusLinesUiState, onIntent: (intent: BusLinesIntent) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.bus_lines_title)) }, navigationIcon = {
            IconButton(onClick = { onIntent(BusLinesIntent.OnClickClose) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.bus_lines_content_description_icon_close)
                )
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { onIntent(BusLinesIntent.OnClickAddBusLine) },
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(R.string.bus_lines_content_description_icon_add_bus_line)
            )
        }
    }) { contentPadding ->
        Crossfade(targetState = uiState.contentState) { state ->
            when (state) {
                ContentState.IDLE -> Unit
                ContentState.LOADED -> {
                    Crossfade(uiState.busLines.isEmpty()) { isEmpty ->
                        when (isEmpty) {
                            true -> {
                                EmptyBusLines(
                                    modifier = Modifier
                                        .padding(contentPadding)
                                        .fillMaxSize()
                                )
                            }

                            false -> {
                                BusLinesList(
                                    busLines = uiState.busLines,
                                    modifier = Modifier
                                        .padding(contentPadding)
                                        .fillMaxSize(),
                                    onMoveBusLine = { fromId, toId ->
                                        onIntent(BusLinesIntent.OnMoveBusLineItem(fromId, toId))
                                    },
                                    onClickBusLine = { busLineId ->
                                        onIntent(BusLinesIntent.OnClickBusLine(busLineId))
                                    },
                                    onClickDeleteBusLine = { busLineId ->
                                        onIntent(BusLinesIntent.OnClickDeleteBusLine(busLineId))
                                    },
                                    onDragEndBusLines = { onIntent(BusLinesIntent.OnDragEndBusLines) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogConfirmDelete(
    busLine: BusLineUiModel?,
    onIntent: (intent: BusLinesIntent) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onIntent(BusLinesIntent.OnDismissDialogConfirmDelete) },
        title = { Text(stringResource(R.string.bus_lines_detail_dialog_delete_title)) },
        text = {
            Text(
                text = stringResource(
                    id = R.string.bus_lines_detail_dialog_delete_description,
                    busLine?.name.orEmpty()
                )
            )
        },
        confirmButton = {
            TextButton(
                shape = RoundedCornerShape(8.dp),
                onClick = { onIntent(BusLinesIntent.OnSubmitConfirmDelete) }) {
                Text(
                    text = stringResource(R.string.bus_lines_detail_dialog_delete_confirm_button),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(
                shape = RoundedCornerShape(8.dp),
                onClick = { onIntent(BusLinesIntent.OnDismissDialogConfirmDelete) }) {
                Text(text = stringResource(R.string.bus_lines_detail_dialog_delete_cancel_button))
            }
        },
        shape = RoundedCornerShape(8.dp)
    )
}

@Preview
@Composable
fun PreviewBusLinesSkeleton() {
    DateroTheme {
        BusLinesScreenSkeleton(
            uiState = BusLinesUiState.Companion.DEFAULT, onIntent = {})
    }
}
