package labs.alexandre.datero.presentation.base.intent

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

interface BaseIntentHandler<I : BaseIntent, S : BaseState, E : BaseEffect> {

    suspend fun handle(
        intent: I,
        state: MutableStateFlow<S>,
        effect: MutableSharedFlow<E>,
        scope: CoroutineScope
    )

}
