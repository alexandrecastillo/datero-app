package labs.alexandre.datero.presentation.features.busLineDetail.state

import androidx.compose.runtime.Immutable
import labs.alexandre.datero.domain.model.BusLine
import java.util.UUID

@Immutable
data class BusLineDetailUiState(
    val title: String,
    val dialogOpened: DialogOpened,
    val colorPickerUiState: DialogColorPickerUiState,
    val nameState: NameState,
    val colorsState: ColorsState,
    val currentBusLine: BusLine? = null,
) {
    companion object {
        val DEFAULT = BusLineDetailUiState(
            title = "",
            dialogOpened = DialogOpened.NONE,
            colorPickerUiState = DialogColorPickerUiState.DEFAULT,
            nameState = NameState.DEFAULT,
            colorsState = ColorsState.DEFAULT
        )
    }
}

@Immutable
data class DialogColorPickerUiState(
    val currentIdColor: String?,
    val currentPickColor: String?
) {
    companion object {
        val DEFAULT = DialogColorPickerUiState(
            currentIdColor = null,
            currentPickColor = null
        )
    }
}

enum class DialogOpened {
    COLOR_PICKER_DIALOG,
    DISCARD_CHANGES,
    NONE
}

@Immutable
data class NameState(
    val text: String,
    val isError: Boolean
) {
    companion object {
        val DEFAULT = NameState(
            text = "",
            isError = false
        )
    }
}

@Immutable
data class ColorsState(
    val colors: List<Pair<String, String>>,
    val isError: Boolean
) {
    companion object {
        val DEFAULT = ColorsState(
            colors = DEFAULT_COLORS,
            isError = false
        )
    }
}

val DEFAULT_COLORS = listOf(UUID.randomUUID().toString() to "#FFFFFF")