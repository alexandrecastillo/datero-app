package labs.alexandre.datero.presentation.base.intent

import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class IntentHandlerRegistry<I : BaseIntent, S : BaseState, E : BaseEffect> {
    val handlers = mutableMapOf<KClass<out I>, BaseIntentHandler<out I, out S, out E>>()

    inline fun <reified T : I> register(handler: BaseIntentHandler<T, S, E>) {
        handlers[T::class] = handler
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : I> get(intent: T): BaseIntentHandler<T, S, E>? {
        return handlers[intent::class] as? BaseIntentHandler<T, S, E>
    }

}
