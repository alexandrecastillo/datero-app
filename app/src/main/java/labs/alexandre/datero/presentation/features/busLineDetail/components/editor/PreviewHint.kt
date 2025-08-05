package labs.alexandre.datero.presentation.features.busLineDetail.components.editor

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.ui.component.BusLineFlag
import labs.alexandre.datero.presentation.ui.component.BusLineFlagScale
import labs.alexandre.datero.presentation.theme.DateroTheme

@Composable
fun PreviewHint(
    modifier: Modifier = Modifier,
    colors: List<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.bus_line_detail_strip_preview_flag),
            style = MaterialTheme.typography.bodyLarge
        )
        BusLineFlag(
            modifier = Modifier,
            colors = colors,
            scale = BusLineFlagScale.Large
        )
    }
}

@Preview
@Composable
fun PreviewHintPreview() {
    DateroTheme {
        PreviewHint(colors = listOf())
    }
}