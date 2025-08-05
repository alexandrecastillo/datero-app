package labs.alexandre.datero.presentation.ui.util

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun String.toColorIntOrNull(): Color? {
    return try {
        Color(toColorInt())
    } catch (_: Exception) {
        null
    }
}

fun isHexColorDark(hexColor: String): Boolean {
    return try {
        val cleanHex = hexColor.removePrefix("#")
        val colorInt = cleanHex.toLong(16).toInt()
        isColorDark(colorInt)
    } catch (_: Exception) {
        false
    }
}

fun isColorDark(color: Int): Boolean {
    return try {
        val r = (color shr 16 and 0xFF) / 255.0
        val g = (color shr 8 and 0xFF) / 255.0
        val b = (color and 0xFF) / 255.0

        val luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b
        luminance < 0.5
    } catch (_: Exception) {
        false
    }
}
