package labs.alexandre.datero.presentation.features.dashboard

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.features.dashboard.components.busline.ListBusLines
import labs.alexandre.datero.presentation.features.dashboard.components.busline.ListBusLinesPlaceholder
import labs.alexandre.datero.presentation.features.dashboard.components.busline.EmptyDashboard
import labs.alexandre.datero.presentation.features.dashboard.components.dialog.MarkBusLineTimestampBottomSheet
import labs.alexandre.datero.presentation.features.dashboard.effect.DashboardEffect
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardContentState
import labs.alexandre.datero.presentation.features.dashboard.state.DashboardUiState
import labs.alexandre.datero.presentation.features.dashboard.state.MarkTimestampUiState
import labs.alexandre.datero.presentation.features.dashboard.viewmodel.DashboardViewModel
import labs.alexandre.datero.presentation.root.providers.LocalDateroSnackbarController
import labs.alexandre.datero.presentation.theme.DateroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navToCreateBusLine: () -> Unit,
    navToBusLines: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HandleDashboardEffects(
        effectFlow = viewModel.effect,
        navToBusLines = navToBusLines,
        navToCreateBusLine = navToCreateBusLine
    )

    DashboardScreenSkeleton(
        uiState = uiState,
        onIntent = { intent -> viewModel.onIntent(intent) }
    )

    HostDialog(
        uiState = uiState,
        onIntent = { intent -> viewModel.onIntent(intent) }
    )
}

@Composable
private fun HandleDashboardEffects(
    effectFlow: Flow<DashboardEffect>,
    navToBusLines: () -> Unit,
    navToCreateBusLine: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarController = LocalDateroSnackbarController.current

    LaunchedEffect(effectFlow) {
        effectFlow.collect { effect ->
            when (effect) {
                DashboardEffect.NavigateToSettings -> navToBusLines()
                DashboardEffect.NavigateToCreateBusLine -> navToCreateBusLine()
                is DashboardEffect.ShowSnackbar -> {
                    coroutineScope.launch {
                        snackbarController.show(message = effect.message)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenSkeleton(
    uiState: DashboardUiState,
    onIntent: (DashboardIntent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.dashboard_title))
                },
                actions = {
                    IconButton(onClick = { onIntent(DashboardIntent.OnClickSettings) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings),
                            contentDescription = stringResource(R.string.dashboard_content_description_button_icon_settings)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Crossfade(
            targetState = uiState.contentState,
            modifier = Modifier.padding(innerPadding)
        ) { contentState ->
            when (contentState) {
                DashboardContentState.IDLE -> Unit
                DashboardContentState.LOADING -> {
                    ListBusLinesPlaceholder()
                }

                DashboardContentState.LOADED -> {
                    when (uiState.busLines.isEmpty()) {
                        true -> {
                            EmptyDashboard(
                                modifier = Modifier.fillMaxSize(),
                                onIntent = onIntent
                            )
                        }

                        false -> {
                            ListBusLines(
                                busLines = uiState.busLines,
                                modifier = Modifier.fillMaxSize(),
                                onIntent = onIntent
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HostDialog(
    uiState: DashboardUiState,
    onIntent: (DashboardIntent) -> Unit
) {
    if (uiState.markTimestampState.showDialog == true) {
        MarkBusLineTimestampBottomSheet(
            state = uiState.markTimestampState,
            onSelected = { answer -> onIntent(DashboardIntent.OnSelectAnswer(answer)) },
            onSubmit = { onIntent(DashboardIntent.OnSubmitBusMark) },
            onDismiss = { onIntent(DashboardIntent.OnDismissMarkTimestampBusLine) }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewDashboardScreen() {
    DateroTheme {
        DashboardScreenSkeleton(
            uiState = DashboardUiState(
                contentState = DashboardContentState.LOADED,
                busLines = emptyList(),
                markTimestampState = MarkTimestampUiState.DEFAULT
            ),
            onIntent = {}
        )
    }
}