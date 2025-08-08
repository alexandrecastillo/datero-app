package labs.alexandre.datero.presentation.features.dashboard.components.busline

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.features.dashboard.intent.DashboardIntent
import labs.alexandre.datero.presentation.theme.DateroTheme
import labs.alexandre.datero.presentation.ui.component.PrimaryButton

@Composable
fun EmptyDashboard(
    modifier: Modifier = Modifier,
    onIntent: (DashboardIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(48.dp)
            .offset(y = (-36).dp)
            .then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_clipboard),
            contentDescription = stringResource(R.string.dashboard_empty_bus_icon),
            modifier = Modifier.size(164.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.dashboard_empty_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.dashboard_empty_description),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(84.dp))
        PrimaryButton(
            text = stringResource(R.string.dashboard_empty_text_button),
            onClick = { onIntent(DashboardIntent.OnClickAddBusLine) },
            modifier = Modifier.fillMaxWidth(),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(R.string.dashboard_empty_content_description_icon_button),
                )
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewEmptyDashboard() {
    DateroTheme {
        EmptyDashboard(
            modifier = Modifier.fillMaxSize(),
            onIntent = {}
        )
    }
}