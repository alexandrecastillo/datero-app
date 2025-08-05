package labs.alexandre.datero.core.resources

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StringProvider {
    fun getString(@StringRes resId: Int): String
    fun getString(@StringRes resId: Int, vararg args: Any): String
    fun getQuantityString(@PluralsRes resId: Int, quantity: Int): String
}

class DefaultStringProvider @Inject constructor(
    @ApplicationContext
    private val context: Context
) : StringProvider {
    override fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(@StringRes resId: Int, vararg args: Any): String {
        return context.getString(resId, *args)
    }

    override fun getQuantityString(@PluralsRes id: Int, quantity: Int): String {
        return context.resources.getQuantityString(id, quantity)
    }
}