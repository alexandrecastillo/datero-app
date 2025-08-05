package labs.alexandre.datero.presentation.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import labs.alexandre.datero.presentation.base.intent.BaseEffect
import labs.alexandre.datero.presentation.base.intent.BaseIntent
import labs.alexandre.datero.presentation.base.intent.BaseState
import labs.alexandre.datero.presentation.base.intent.IntentHandlerRegistry

abstract class BaseViewModel<I : BaseIntent, S : BaseState, E : BaseEffect>(
    open val intentHandler: IntentHandlerRegistry<I, S, E>
) : ViewModel() {

    abstract val initialState: S

    private val _uiState = MutableStateFlow<S>(initialState)
    val uiState: StateFlow<S> = _uiState

    private val _effect = MutableSharedFlow<E>()
    val effect: SharedFlow<E> = _effect

    fun onIntent(
        intent: I,
    ) {
        viewModelScope.launch {
            intentHandler.get(intent)?.handle(
                intent = intent,
                state = _uiState,
                effect = _effect,
                scope = this
            )
        }
    }

}