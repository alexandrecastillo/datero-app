package labs.alexandre.datero.presentation.features.busLineDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import labs.alexandre.datero.R
import labs.alexandre.datero.domain.constants.BusinessRules
import labs.alexandre.datero.presentation.features.busLineDetail.components.dialog.BottomSheetColorPicker
import labs.alexandre.datero.presentation.features.busLineDetail.components.editor.AddColorButton
import labs.alexandre.datero.presentation.features.busLineDetail.components.editor.PreviewHint
import labs.alexandre.datero.presentation.features.busLineDetail.components.editor.StripBusLine
import labs.alexandre.datero.presentation.features.busLineDetail.effect.BusLineDetailEffect
import labs.alexandre.datero.presentation.features.busLineDetail.intent.BusLineDetailIntent
import labs.alexandre.datero.presentation.features.busLineDetail.state.BusLineDetailUiState
import labs.alexandre.datero.presentation.features.busLineDetail.state.DialogOpened
import labs.alexandre.datero.presentation.features.busLineDetail.state.NameState
import labs.alexandre.datero.presentation.features.busLineDetail.viewmodel.BusLineDetailViewModel
import labs.alexandre.datero.presentation.root.providers.LocalDateroSnackbarController
import labs.alexandre.datero.presentation.theme.DateroTheme
import labs.alexandre.datero.presentation.ui.component.PrimaryButton
import labs.alexandre.datero.presentation.ui.modifier.clearFocusOnKeyboardDismiss
import labs.alexandre.datero.presentation.ui.util.CustomArrangement
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun BusLineDetailScreen(
    busLineDetailViewModel: BusLineDetailViewModel = hiltViewModel(),
    navToBack: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val snackbarController = LocalDateroSnackbarController.current

    val coroutineScope = rememberCoroutineScope()

    val uiState by busLineDetailViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        busLineDetailViewModel.effect.collect { effect ->
            when (effect) {
                is BusLineDetailEffect.NavigateToBack -> {
                    navToBack()
                }

                is BusLineDetailEffect.ShowMessage -> {
                    coroutineScope.launch {
                        snackbarController.show(message = effect.message)
                    }
                }

                BusLineDetailEffect.PerformHapticLongPress -> {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }

                BusLineDetailEffect.PerformHapticGestureEnd -> {
                    haptic.performHapticFeedback(HapticFeedbackType.GestureEnd)
                }
            }
        }
    }

    BackHandler {
        busLineDetailViewModel.onIntent(BusLineDetailIntent.OnBackScreen)
    }

    BusLineDetailScreenSkeleton(
        uiState = uiState,
        onIntent = { intent -> busLineDetailViewModel.onIntent(intent) }
    )

    DialogHost(
        uiState = uiState,
        onIntent = { intent -> busLineDetailViewModel.onIntent(intent) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusLineDetailScreenSkeleton(
    uiState: BusLineDetailUiState,
    onIntent: (intent: BusLineDetailIntent) -> Unit
) {
    val onIntentUpdated = rememberUpdatedState(onIntent)

    val lazyListState = rememberLazyListState()

    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        onIntent(BusLineDetailIntent.OnMoveColor(from.key.toString(), to.key.toString()))
    }

    val canAddColor by remember(uiState.colorsState.colors) {
        derivedStateOf { uiState.colorsState.colors.size < BusinessRules.MAX_COLORS_BUS_LINE_STRIPS }
    }

    val isDeleteEnabled by remember(uiState.colorsState.colors) {
        derivedStateOf { uiState.colorsState.colors.size > BusinessRules.MIN_COLORS_BUS_LINE_STRIPS }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(uiState.title) }, navigationIcon = {
                IconButton(onClick = { onIntentUpdated.value(BusLineDetailIntent.OnCloseClick) }) {
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = stringResource(R.string.bus_line_detail_content_description_icon_close)
                    )
                }
            })
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            state = lazyListState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = CustomArrangement.LastItemToBottom
        ) {
            item(key = BusLineDetailKeys.NAME) {
                NameTextField(
                    state = uiState.nameState,
                    onChangeName = { onIntentUpdated.value(BusLineDetailIntent.OnChangeName(it)) }
                )
            }

            item(key = BusLineDetailKeys.SPACER_1) {
                Spacer(
                    modifier = Modifier
                        .height(24.dp)
                        .animateItem()
                )
            }

            item(key = BusLineDetailKeys.DIVIDER) {
                HorizontalDivider()
            }

            item(key = BusLineDetailKeys.SPACER_2) {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item(key = BusLineDetailKeys.STRIPS_TITLE) {
                Text(
                    text = stringResource(R.string.bus_line_detail_strip_section_title),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            item(key = BusLineDetailKeys.SPACER_3) {
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .animateItem()
                )
            }

            itemsIndexed(
                items = uiState.colorsState.colors,
                key = { _, item -> item.first }
            ) { index, item ->
                ReorderableItem(reorderableLazyListState, key = item.first) { isDragging ->
                    Column {
                        StripBusLine(
                            isDragging = isDragging,
                            isDeleteEnabled = isDeleteEnabled,
                            isDuplicatedVisible = canAddColor,
                            strip = item,
                            onItemClick = {
                                onIntentUpdated.value(BusLineDetailIntent.OnClickColor(item.first))
                            },
                            onClickDelete = {
                                onIntentUpdated.value(BusLineDetailIntent.OnDeleteStrip(item.first))
                            },
                            onDragStarted = {
                                onIntentUpdated.value(BusLineDetailIntent.OnColorDragStarted)
                            },
                            onDragStopped = {
                                onIntentUpdated.value(BusLineDetailIntent.OnColorDragStopped)
                            },
                            onClickDuplicate = {
                                onIntentUpdated.value(BusLineDetailIntent.OnDuplicateColor(item.first))
                            }
                        )

                        if (index != uiState.colorsState.colors.lastIndex) {
                            Spacer(
                                modifier = Modifier
                                    .height(4.dp)
                                    .fillMaxWidth()
                                    .animateItem()
                            )
                        }
                    }
                }
            }

            if (uiState.colorsState.isError) {
                item(key = BusLineDetailKeys.STRIPS_ERROR) {
                    Text(
                        text = stringResource(R.string.bus_line_detail_field_strips_supporting_text),
                        modifier = Modifier
                            .animateItem()
                            .padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            if (canAddColor) {
                item(key = BusLineDetailKeys.SPACER_4) {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .animateItem()
                    )
                }

                item(key = BusLineDetailKeys.ADD_COLOR) {
                    AddColorButton(
                        modifier = Modifier.animateItem(),
                        onClick = { onIntentUpdated.value(BusLineDetailIntent.OnClickAddColor) }
                    )
                }
            }

            item(key = BusLineDetailKeys.SPACER_5) {
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .animateItem()
                )
            }

            item(key = BusLineDetailKeys.PREVIEW_HINT) {
                PreviewHint(
                    modifier = Modifier.animateItem(),
                    colors = uiState.colorsState.colors.map { it.second }
                )
            }

            item(key = BusLineDetailKeys.SPACER_6) {
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .animateItem()
                )
            }

            item(key = BusLineDetailKeys.SAVE_BUTTON) {
                PrimaryButton(
                    text = stringResource(R.string.bus_line_detail_text_button_save),
                    onClick = { onIntentUpdated.value(BusLineDetailIntent.OnSubmitSave) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem()
                )
            }
        }
    }
}

@Composable
fun NameTextField(
    state: NameState,
    onChangeName: (name: String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val onChangeNameUpdated = rememberUpdatedState(onChangeName)

    OutlinedTextField(
        value = state.text,
        onValueChange = { value -> onChangeNameUpdated.value(value) },
        label = {
            Text(text = stringResource(R.string.bus_line_detail_field_name_label))
        },
        placeholder = {
            Text(text = stringResource(R.string.bus_line_detail_field_name_placeholder))
        },
        supportingText = {
            if (state.isError) {
                Text(text = stringResource(R.string.bus_line_detail_field_name_supporting_text))
            }
        },
        isError = state.isError,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss(),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Ascii,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Sentences
        )
    )
}

@Composable
fun DialogHost(
    uiState: BusLineDetailUiState,
    onIntent: (BusLineDetailIntent) -> Unit
) {
    when (uiState.dialogOpened) {
        DialogOpened.COLOR_PICKER_DIALOG -> {
            BottomSheetColorPicker(
                colorPickerUiState = uiState.colorPickerUiState,
                onClickSave = { colorId, colorHex ->
                    onIntent(BusLineDetailIntent.OnSaveColor(colorId, colorHex))
                },
                onDismiss = {
                    onIntent(BusLineDetailIntent.OnDismissColorPicker)
                }
            )
        }

        DialogOpened.DISCARD_CHANGES -> {
            DiscardChangesDialog(
                onIntent = onIntent
            )
        }

        DialogOpened.NONE -> Unit
    }
}

@Composable
fun DiscardChangesDialog(onIntent: (intent: BusLineDetailIntent) -> Unit) {
    AlertDialog(
        onDismissRequest = { onIntent(BusLineDetailIntent.OnDismissDiscardChangesDialog) },
        title = { Text(stringResource(R.string.bus_line_detail_dialog_discard_changes_title)) },
        text = { Text(stringResource(R.string.bus_line_detail_dialog_discard_changes_description)) },
        confirmButton = {
            TextButton(
                shape = RoundedCornerShape(8.dp),
                onClick = { onIntent(BusLineDetailIntent.OnSubmitDiscardChangesDialog) }) {
                Text(
                    text = stringResource(R.string.bus_line_detail_dialog_discard_changes_confirm_button),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(
                shape = RoundedCornerShape(8.dp),
                onClick = { onIntent(BusLineDetailIntent.OnDismissDiscardChangesDialog) }) {
                Text(text = stringResource(R.string.bus_line_detail_dialog_discard_changes_cancel_button))
            }
        },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
@Preview(showSystemUi = true)
fun PreviewBusLineScreen() {
    DateroTheme {
        BusLineDetailScreenSkeleton(
            uiState = BusLineDetailUiState.DEFAULT, onIntent = {})
    }
}

private object BusLineDetailKeys {
    const val NAME = "name"
    const val SPACER_1 = "spacer_1"
    const val DIVIDER = "divider"
    const val SPACER_2 = "spacer_2"
    const val STRIPS_TITLE = "strips_title"
    const val SPACER_3 = "spacer_3"
    const val STRIPS_ERROR = "strips_error"
    const val SPACER_4 = "spacer_4"
    const val ADD_COLOR = "add_color"
    const val SPACER_5 = "spacer_5"
    const val PREVIEW_HINT = "preview_hint"
    const val SPACER_6 = "spacer_6"
    const val SAVE_BUTTON = "save_button"
}
