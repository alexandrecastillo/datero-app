package labs.alexandre.datero.ui.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@Composable
fun BusLineFlag(
    modifier: Modifier = Modifier,
    colors: List<String>,
) {
    Canvas(
        modifier = Modifier
            .border(1.dp, Color.DarkGray)
            .padding(1.dp)
            .size(36.dp, 24.dp)
            .then(modifier)
    ) {
        val stripeCount = colors.size.takeIf { it > 0 } ?: 1
        val stripeHeight = size.height / stripeCount

        colors.forEachIndexed { index, colorHex ->
            drawRect(
                color = Color(colorHex.toColorInt()),
                topLeft = Offset(0f, index * stripeHeight),
                size = Size(size.width, stripeHeight)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBusLineFlag() {
    BusLineFlag(
        modifier = Modifier
            .size(52.dp, 32.dp),
        colors = listOf("#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF")
    )
}