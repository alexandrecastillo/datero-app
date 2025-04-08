package labs.alexandre.datero.ui.dashboard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.ui.theme.BorderDashEmptyTimestamp
import labs.alexandre.datero.ui.theme.DateroTheme
import labs.alexandre.datero.ui.util.dashedBorder

@Composable
fun EmptyTimestamps(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .dashedBorder(
                color = BorderDashEmptyTimestamp,
                shape = RoundedCornerShape(8.dp),
                strokeWidth = 2.dp,
                dashWidth = 8.dp,
                gapWidth = 8.dp,
                cap = StrokeCap.Round
            )
            .padding(vertical = 24.dp),
        textAlign = TextAlign.Center,
        text = "Aún no se han marcado buses",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Preview
@Composable
fun PreviewEmptyTimestamps() {
    DateroTheme {
        EmptyTimestamps()
    }
}