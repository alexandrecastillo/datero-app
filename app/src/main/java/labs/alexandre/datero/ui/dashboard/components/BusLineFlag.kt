package labs.alexandre.datero.ui.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BusLineFlag(
    modifier: Modifier = Modifier,
    colors: List<Color>,
) {
    Canvas(
        modifier = Modifier
            .border(1.dp, Color.DarkGray)
            .padding(1.dp)
            .defaultMinSize(36.dp, 28.dp)
            .then(modifier)
    ) {
        val stripeCount = colors.size.takeIf { it > 0 } ?: 1
        val stripeHeight = size.height / stripeCount

        colors.forEachIndexed { index, color ->
            drawRect(
                color = color,
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
        colors = listOf(
            Color(0xFFEB1319), Color(0xFFFF5722), Color(0xFFFFC107), Color(0xFFFFFFFF)
        )
    )
}