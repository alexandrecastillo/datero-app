package labs.alexandre.datero.presentation.ui.modifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class DashedBorderStyle(
    val color: Color,
    val shape: Shape,
    val strokeWidth: Dp,
    val dashWidth: Dp,
    val gapWidth: Dp,
    val cap: StrokeCap
)

object DashedBorderDefaults {
    @Composable
    fun style() = DashedBorderStyle(
        color = Color.Gray,
        shape = RectangleShape,
        strokeWidth = 2.dp,
        dashWidth = 6.dp,
        gapWidth = 6.dp,
        cap = StrokeCap.Round
    )
}

@Composable
fun Modifier.dashedBorder(
    style: DashedBorderStyle = DashedBorderDefaults.style()
) = composed {
    val density = LocalDensity.current

    val strokeWidthPx = with(density) { style.strokeWidth.toPx() }
    val dashWidthPx = with(density) { style.dashWidth.toPx() }
    val gapWidthPx = with(density) { style.gapWidth.toPx() }

    val stroke = remember(style.cap, strokeWidthPx, dashWidthPx, gapWidthPx) {
        Stroke(
            cap = style.cap,
            width = strokeWidthPx,
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(dashWidthPx, gapWidthPx),
                phase = 0f
            )
        )
    }

    drawWithContent {
        val outline = style.shape.createOutline(size, layoutDirection, this)

        val path = Path().apply {
            addOutline(outline)
        }

        drawContent()

        drawPath(
            path = path,
            color = style.color,
            style = stroke
        )
    }
}