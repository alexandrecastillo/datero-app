package labs.alexandre.datero.presentation.features.dashboard.components.busmark

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.theme.BorderDashEmptyTimestamp
import labs.alexandre.datero.presentation.theme.DateroTheme
import labs.alexandre.datero.presentation.ui.modifier.DashedBorderDefaults
import labs.alexandre.datero.presentation.ui.modifier.dashedBorder

@Composable
fun EmptyBusMarks(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .dashedBorder(
                style = DashedBorderDefaults.style().copy(
                    color = BorderDashEmptyTimestamp,
                    shape = RoundedCornerShape(8.dp)
                )
            )
            .padding(vertical = 24.dp, horizontal = 16.dp),
        textAlign = TextAlign.Center,
        text = stringResource(R.string.dashboard_text_empty_marks),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewEmptyBusMarks() {
    DateroTheme {
        EmptyBusMarks(
            modifier = Modifier.padding(16.dp)
        )
    }
}