package labs.alexandre.datero.presentation.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density

@Immutable
object CustomArrangement {

    @Stable
    val LastItemToBottom = object : Arrangement.Vertical {
        override fun Density.arrange(
            totalSize: Int,
            sizes: IntArray,
            outPositions: IntArray
        ) {
            var currentY = 0

            sizes.forEachIndexed { index, height ->
                outPositions[index] = if (index == sizes.lastIndex) {
                    totalSize - height
                } else {
                    currentY.also { currentY += height }
                }
            }
        }

        override fun toString(): String = "CustomArrangement#LastItemToBottom"
    }
}