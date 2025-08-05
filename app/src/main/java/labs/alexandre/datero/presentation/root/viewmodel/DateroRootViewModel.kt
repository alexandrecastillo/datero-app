package labs.alexandre.datero.presentation.root.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import labs.alexandre.datero.presentation.root.effect.DateroRootEffect
import labs.alexandre.datero.presentation.root.state.DateroRootState
import javax.inject.Inject

@HiltViewModel
class DateroRootViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow<DateroRootState>(DateroRootState.DEFAULT)
    val uiState: StateFlow<DateroRootState> = _uiState

    private val _effect = MutableSharedFlow<DateroRootEffect>()
    val effect: SharedFlow<DateroRootEffect> = _effect

}