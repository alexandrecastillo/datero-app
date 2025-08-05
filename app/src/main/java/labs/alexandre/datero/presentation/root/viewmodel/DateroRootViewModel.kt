package labs.alexandre.datero.presentation.root.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import labs.alexandre.datero.presentation.root.effect.DateroRootEffect
import labs.alexandre.datero.presentation.root.intent.DateroRootIntent
import labs.alexandre.datero.presentation.root.state.DateroRootDialogState
import labs.alexandre.datero.presentation.root.state.DateroRootState
import javax.inject.Inject

@HiltViewModel
class DateroRootViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow<DateroRootState>(DateroRootState.DEFAULT)
    val uiState: StateFlow<DateroRootState> = _uiState

    private val _effect = MutableSharedFlow<DateroRootEffect>()
    val effect: SharedFlow<DateroRootEffect> = _effect

    init {
    }

    fun onIntent(intent: DateroRootIntent) {
        when (intent) {
            DateroRootIntent.RecalculateTimes -> {
                recalculateTime()
            }

            DateroRootIntent.DismissRecalculateTimesDialog -> {
                onDismissDialog()
            }
        }
    }

    private fun validTime() {
        _uiState.update { uiState ->
            uiState.copy()
        }
    }

    private fun onDismissDialog() {
        _uiState.update { uiState ->
            uiState.copy(dialogState = DateroRootDialogState.None)
        }
    }

    private fun recalculateTime() {
        viewModelScope.launch {

        }
    }

}