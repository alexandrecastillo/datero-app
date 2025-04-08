package labs.alexandre.datero.ui.util

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dashedBorder(
    color: Color,
    shape: Shape,
    strokeWidth: Dp = 1.dp,
    dashWidth: Dp = 4.dp,
    gapWidth: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "dashedBorder"
        properties["color"] = color
        properties["shape"] = shape
        properties["strokeWidth"] = strokeWidth
        properties["dashWidth"] = dashWidth
        properties["gapWidth"] = gapWidth
        properties["cap"] = cap
    },
    factory = {
        val density = LocalDensity.current

        val strokeWidthPx = with(density) { strokeWidth.toPx() }
        val dashWidthPx = with(density) { dashWidth.toPx() }
        val gapWidthPx = with(density) { gapWidth.toPx() }

        val stroke = remember(cap, strokeWidthPx, dashWidthPx, gapWidthPx) {
            Stroke(
                cap = cap,
                width = strokeWidthPx,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(dashWidthPx, gapWidthPx),
                    phase = 0f
                )
            )
        }

        drawWithContent {
            val outline = shape.createOutline(size, layoutDirection, this)

            val path = Path().apply {
                addOutline(outline)
            }

            drawContent()

            drawPath(
                path = path,
                color = color,
                style = stroke
            )
        }
    }
)