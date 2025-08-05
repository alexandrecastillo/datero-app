package labs.alexandre.datero.presentation.features.busLines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import labs.alexandre.datero.core.extension.indexOfFirstOrNull
import labs.alexandre.datero.domain.repository.BusLineRepository
import labs.alexandre.datero.presentation.features.busLines.effect.BusLinesEffect
import labs.alexandre.datero.presentation.features.busLines.intent.BusLinesIntent
import labs.alexandre.datero.presentation.features.busLines.state.BusLinesUiState
import labs.alexandre.datero.presentation.features.busLines.state.ContentState
import labs.alexandre.datero.presentation.mapper.BusLineUiMapper
import javax.inject.Inject

@HiltViewModel
class BusLinesViewModel @Inject constructor(
    private val busLineRepository: BusLineRepository, private val busLineUiMapper: BusLineUiMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow<BusLinesUiState>(BusLinesUiState.Companion.DEFAULT)
    val uiState: StateFlow<BusLinesUiState> = _uiState

    private val _effect = MutableSharedFlow<BusLinesEffect>()
    val effect: SharedFlow<BusLinesEffect> = _effect

    init {
        onIntent(BusLinesIntent.StartObservingBusLines)
    }

    fun onIntent(intent: BusLinesIntent) {
        viewModelScope.launch {
            when (intent) {
                is BusLinesIntent.StartObservingBusLines -> {
                    busLineRepository.observeBusLines().map { busLines ->
                        busLines.map { busLineUiMapper.mapToUiModel(it) }
                    }.collect { busLines ->
                        _uiState.update { uiState ->
                            uiState.copy(contentState = ContentState.LOADED, busLines = busLines)
                        }
                    }
                }

                is BusLinesIntent.OnMoveBusLineItem -> {
                    val (fromId, toId) = intent
                    val currentList = uiState.value.busLines

                    val fromIndex =
                        currentList.indexOfFirstOrNull { it.id == fromId } ?: return@launch
                    val toIndex = currentList.indexOfFirstOrNull { it.id == toId } ?: return@launch

                    val reordered = currentList.toMutableList().apply {
                        add(toIndex, removeAt(fromIndex))
                    }.mapIndexed { index, busLine ->
                        busLine.copy(position = index)
                    }

                    _uiState.update { uiState -> uiState.copy(busLines = reordered) }
                }

                is BusLinesIntent.OnDragEndBusLines -> {
                    busLineRepository.reorder(uiState.value.busLines.map {
                        busLineUiMapper.mapToDomain(it)
                    })
                }

                is BusLinesIntent.OnClickDeleteBusLine -> {
                    _uiState.update { uiState ->
                        uiState.copy(
                            showConfirmDelete = true, busLineToDelete = intent.busLine
                        )
                    }
                }

                BusLinesIntent.OnDismissDialogConfirmDelete -> {
                    _uiState.update { uiState ->
                        uiState.copy(
                            showConfirmDelete = false, busLineToDelete = null
                        )
                    }
                }

                is BusLinesIntent.OnSubmitConfirmDelete -> {
                    val busLineId = uiState.value.busLineToDelete?.id ?: return@launch
                    busLineRepository.deleteBusLine(busLineId)
                    _uiState.update { uiState -> uiState.copy(showConfirmDelete = false) }
                }

                BusLinesIntent.OnClickAddBusLine -> {
                    _effect.emit(BusLinesEffect.NavigateToBusLineDetail())
                }

                is BusLinesIntent.OnClickBusLine -> {
                    _effect.emit(BusLinesEffect.NavigateToBusLineDetail(intent.busLineId))
                }

                BusLinesIntent.OnClickClose -> {
                    _effect.emit(BusLinesEffect.NavigateToBack)
                }
            }
        }
    }

}
