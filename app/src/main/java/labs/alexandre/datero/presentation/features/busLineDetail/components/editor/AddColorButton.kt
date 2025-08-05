package labs.alexandre.datero.presentation.features.busLineDetail.components.editor

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.theme.DateroTheme

@Composable
fun AddColorButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FilledTonalButton(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = stringResource(R.string.bus_line_detail_strip_content_description_icon_add_strip),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.bus_line_detail_strip_add_button),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
fun PreviewAddColorButton() {
    DateroTheme {
        AddColorButton { }
    }
}