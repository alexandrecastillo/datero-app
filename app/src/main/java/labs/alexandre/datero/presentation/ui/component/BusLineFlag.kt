package labs.alexandre.datero.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.presentation.ui.util.toColorIntOrNull

@Composable
fun BusLineFlag(
    modifier: Modifier = Modifier,
    colors: List<String>,
    scale: BusLineFlagScale = BusLineFlagScale.Medium,
) {
    Column(
        modifier = Modifier
            .height(scale.height)
            .aspectRatio(3/2F)
            .then(modifier)
            .border(1.dp, Color.DarkGray)
            .padding(1.dp)
    ) {
        colors.forEach { colorHex ->
            Box(
                modifier = Modifier.fillMaxWidth()
                    .weight(1F)
                    .background(colorHex.toColorIntOrNull() ?: Color.Unspecified)
            )
        }
    }
}

enum class BusLineFlagScale(val height: Dp) {
    Small(24.dp),
    Medium(32.dp),
    Large(42.dp)
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