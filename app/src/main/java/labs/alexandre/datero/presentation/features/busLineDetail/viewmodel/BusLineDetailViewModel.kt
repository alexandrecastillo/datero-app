package labs.alexandre.datero.presentation.features.busLineDetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import labs.alexandre.datero.domain.repository.BusLineRepository
import labs.alexandre.datero.domain.model.BusLine
import labs.alexandre.datero.presentation.features.busLineDetail.effect.BusLineDetailEffect
import labs.alexandre.datero.presentation.features.busLineDetail.intent.BusLineDetailIntent
import labs.alexandre.datero.presentation.features.busLineDetail.state.BusLineDetailUiState
import labs.alexandre.datero.presentation.features.busLineDetail.state.DialogColorPickerUiState
import labs.alexandre.datero.presentation.features.busLineDetail.state.DialogOpened
import labs.alexandre.datero.core.resources.StringProvider
import java.util.UUID
import javax.inject.Inject
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.navigation.RootRoutes
import labs.alexandre.datero.core.extension.indexOfFirstOrNull
import labs.alexandre.datero.domain.constants.BusinessRules

@HiltViewModel
class BusLineDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val busLineRepository: BusLineRepository,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<BusLineDetailUiState>(BusLineDetailUiState.DEFAULT)
    val uiState: StateFlow<BusLineDetailUiState> = _uiState

    private val _effect = MutableSharedFlow<BusLineDetailEffect>()
    val effect: SharedFlow<BusLineDetailEffect> = _effect

    private val params = savedStateHandle.toRoute<RootRoutes.BusLineDetail>()

    init {
        viewModelScope.launch {
            loadScreen()
            loadBusLine()
        }
    }

    private fun loadScreen() {
        val titleRes = when (params.busLineId == null) {
            true -> R.string.bus_line_detail_title_create
            false -> R.string.bus_line_detail_title_edit
        }

        _uiState.update { it.copy(title = stringProvider.getString(titleRes)) }
    }

    private suspend fun loadBusLine() {
        val busLine: BusLine = busLineRepository.getBusLine(
            busLineId = params.busLineId ?: return
        ) ?: return

        _uiState.update { uiState ->
            uiState.copy(
                currentBusLine = busLine,
                nameState = uiState.nameState.copy(text = busLine.name),
                colorsState = uiState.colorsState.copy(
                    colors = busLine.colors.map { UUID.randomUUID().toString() to it }
                ),
            )
        }
    }

    fun onIntent(intent: BusLineDetailIntent) {
        viewModelScope.launch {
            when (intent) {
                is BusLineDetailIntent.OnChangeName -> {
                    val (newName) = intent

                    _uiState.update { uiState ->
                        uiState.copy(
                            nameState = uiState.nameState.copy(
                                text = newName, isError = false
                            )
                        )
                    }
                }

                is BusLineDetailIntent.OnClickColor -> {
                    val (colorId) = intent

                    _uiState.update { uiState ->
                        val currentColor =
                            uiState.colorsState.colors.firstOrNull { it.first == colorId }
                                ?: return@launch

                        uiState.copy(
                            dialogOpened = DialogOpened.COLOR_PICKER_DIALOG,
                            colorPickerUiState = uiState.colorPickerUiState.copy(
                                currentIdColor = colorId,
                                currentPickColor = currentColor.second,
                            )
                        )
                    }
                }

                is BusLineDetailIntent.OnDuplicateColor -> {
                    val colors = uiState.value.colorsState.colors

                    if (colors.size >= BusinessRules.MAX_COLORS_BUS_LINE_STRIPS) return@launch

                    val index =
                        colors.indexOfFirstOrNull { it.first == intent.colorId } ?: return@launch

                    val newColor = UUID.randomUUID().toString() to colors[index].second

                    _uiState.update { current ->
                        current.copy(
                            colorsState = current.colorsState.copy(
                                colors = colors.toMutableList().apply { add(index + 1, newColor) })
                        )
                    }
                }


                is BusLineDetailIntent.OnSaveColor -> {
                    val (colorId, colorHex) = intent

                    _uiState.update { uiState ->
                        val updatedColors = uiState.colorsState.colors.toMutableList().apply {
                            when (colorId) {
                                null -> add(UUID.randomUUID().toString() to colorHex)
                                else -> indexOfFirst { it.first == colorId }.takeIf { it != -1 }
                                    ?.also { index -> this[index] = colorId to colorHex }
                            }
                        }

                        uiState.copy(
                            dialogOpened = DialogOpened.NONE,
                            colorPickerUiState = DialogColorPickerUiState.DEFAULT,
                            colorsState = uiState.colorsState.copy(
                                isError = false, colors = updatedColors
                            )
                        )
                    }
                }

                is BusLineDetailIntent.OnMoveColor -> {
                    val (fromId, toId) = intent
                    val currentList = uiState.value.colorsState.colors

                    val fromIndex =
                        currentList.indexOfFirstOrNull { it.first == fromId } ?: return@launch
                    val toIndex =
                        currentList.indexOfFirstOrNull { it.first == toId } ?: return@launch

                    val reordered = currentList.toMutableList().apply {
                        add(toIndex, removeAt(fromIndex))
                    }

                    _uiState.update { uiState ->
                        uiState.copy(colorsState = uiState.colorsState.copy(colors = reordered))
                    }
                }

                is BusLineDetailIntent.OnDeleteStrip -> {
                    val (colorId) = intent

                    val currentColors = uiState.value.colorsState.colors

                    if (currentColors.size == BusinessRules.MIN_COLORS_BUS_LINE_STRIPS) {
                        return@launch
                    }

                    _uiState.update { uiState ->
                        val colors = currentColors.toMutableList().apply {
                            removeIf { it.first == colorId }
                        }

                        val isError = colors.isEmpty()

                        uiState.copy(
                            colorsState = uiState.colorsState.copy(
                                isError = isError, colors = colors
                            )
                        )
                    }
                }

                is BusLineDetailIntent.OnSubmitSave -> {
                    val nameState = uiState.value.nameState
                    val colorsState = uiState.value.colorsState

                    val name = nameState.text.trim()
                    if (name.isBlank()) {
                        _uiState.update { uiState ->
                            uiState.copy(nameState = nameState.copy(isError = true))
                        }
                        return@launch
                    }

                    val colors = colorsState.colors
                    if (colors.isEmpty()) {
                        _uiState.update { uiState ->
                            uiState.copy(colorsState = colorsState.copy(isError = true))
                        }
                        return@launch
                    }

                    val busLine = uiState.value.currentBusLine

                    val updatedBusLine = busLine?.copy(
                        name = name, colors = colors.map { it.second }) ?: BusLine(
                        name = name, colors = colors.map { it.second })

                    busLineRepository.upsertBusLine(updatedBusLine)

                    _effect.emit(BusLineDetailEffect.NavigateToBack)
                    _effect.emit(BusLineDetailEffect.ShowMessage(stringProvider.getString(R.string.bus_line_detail_message_on_save)))
                }

                BusLineDetailIntent.OnClickAddColor -> {
                    _uiState.update { it.copy(dialogOpened = DialogOpened.COLOR_PICKER_DIALOG) }
                }

                BusLineDetailIntent.OnDismissColorPicker -> {
                    _uiState.update { it.copy(dialogOpened = DialogOpened.NONE) }
                }

                BusLineDetailIntent.OnCloseClick -> {
                    _uiState.update { it.copy(dialogOpened = DialogOpened.DISCARD_CHANGES) }
                }

                BusLineDetailIntent.OnBackScreen -> {
                    _uiState.update { it.copy(dialogOpened = DialogOpened.DISCARD_CHANGES) }
                }

                BusLineDetailIntent.OnSubmitDiscardChangesDialog -> {
                    _uiState.update { it.copy(dialogOpened = DialogOpened.NONE) }
                    _effect.emit(BusLineDetailEffect.NavigateToBack)
                }

                BusLineDetailIntent.OnDismissDiscardChangesDialog -> {
                    _uiState.update { it.copy(dialogOpened = DialogOpened.NONE) }
                }

                BusLineDetailIntent.OnColorDragStarted -> {
                    _effect.emit(BusLineDetailEffect.PerformHapticLongPress)
                }

                BusLineDetailIntent.OnColorDragStopped -> {
                    _effect.emit(BusLineDetailEffect.PerformHapticGestureEnd)
                }
            }
        }
    }
}