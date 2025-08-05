package labs.alexandre.datero.presentation.ui.modifier

import android.graphics.PathMeasure
import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class ProgressBorderStyle(
    val borderColor: Color,
    val borderWidth: Dp,
    val cornerRadius: Dp,
    val circleRadius: Dp,
    val progressColor: Color
)

object ProgressBorderDefaults {
    @Composable
    fun style() = ProgressBorderStyle(
        borderColor = MaterialTheme.colorScheme.primary,
        borderWidth = 2.dp,
        cornerRadius = 8.dp,
        circleRadius = 4.dp,
        progressColor = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun Modifier.animatedProgressBorder(
    style: ProgressBorderStyle = ProgressBorderDefaults.style(),
    @FloatRange(from = 0.0, to = 1.0)
    progress: Float,
    duration: Int,
    onFinish: () -> Unit = {},
    key: Any
): Modifier = composed {
    val currentOnFinish by rememberUpdatedState(onFinish)

    val animator = remember(key) {
        Animatable(initialValue = progress)
    }

    LaunchedEffect(key) {
        animator.animateTo(
            targetValue = 1F, animationSpec = tween(duration, easing = LinearEasing)
        )
        currentOnFinish()
    }

    then(
        Modifier.drawWithCache {
            val stroke = style.borderWidth.toPx()
            val radius = style.cornerRadius.toPx()
            val halfStroke = stroke / 2
            val width = size.width - stroke
            val height = size.height - stroke

            val composePath = Path().apply {
                moveTo(size.width / 2, halfStroke)
                lineTo(width - radius + halfStroke, halfStroke)
                quadraticTo(
                    size.width - halfStroke,
                    halfStroke,
                    size.width - halfStroke,
                    radius + halfStroke
                )
                lineTo(size.width - halfStroke, height - radius + halfStroke)
                quadraticTo(
                    size.width - halfStroke,
                    size.height - halfStroke,
                    width - radius + halfStroke,
                    size.height - halfStroke
                )
                lineTo(radius + halfStroke, size.height - halfStroke)
                quadraticTo(
                    halfStroke, size.height - halfStroke, halfStroke, height - radius + halfStroke
                )
                lineTo(halfStroke, radius + halfStroke)
                quadraticTo(halfStroke, halfStroke, radius + halfStroke, halfStroke)
                lineTo(size.width / 2, halfStroke)
            }

            val androidPath = composePath.asAndroidPath()
            val measure = PathMeasure().apply { setPath(androidPath, false) }
            val totalLength = measure.length
            val pos = FloatArray(2)

            measure.getPosTan(totalLength * animator.value, pos, null)

            val segment = android.graphics.Path().apply {
                measure.getSegment(0f, totalLength * animator.value, this, true)
            }

            val animatedPath = Path().apply { asAndroidPath().set(segment) }
            val circleCenter = Offset(pos[0], pos[1])

            onDrawWithContent {
                drawContent()

                drawPath(
                    path = composePath, color = style.borderColor, style = Stroke(width = stroke)
                )

                drawPath(
                    path = animatedPath,
                    color = style.progressColor,
                    style = Stroke(width = stroke, cap = StrokeCap.Round)
                )

                drawCircle(
                    color = style.progressColor, radius = style.circleRadius.toPx(), center = circleCenter
                )
            }
        }
    )
}