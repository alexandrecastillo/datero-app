package labs.alexandre.datero.ui.dashboard.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.ui.theme.DateroTheme
import labs.alexandre.datero.ui.theme.Gray50

@Composable
fun DialogMarkBusLineTimestamp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            BusLineFlag(colors = listOf())
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Linea 73", style = MaterialTheme.typography.headlineLarge)
        }
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Text(
            text = "¿Cómo va de pasajeros?",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Answers(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
    }
}

@Composable
fun Answers(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (1..5).forEach {
            Answer()
        }
    }
}

@Composable
fun Answer(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, Gray50, RoundedCornerShape(8.dp))
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Muchos pasajeros")
        Icon(
            painter = painterResource(R.drawable.ic_bus_low),
            contentDescription = "Icon state bus",
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
fun PreviewDialogMarkBusLineTimestamp() {
    DateroTheme {
        DialogMarkBusLineTimestamp()
    }
}