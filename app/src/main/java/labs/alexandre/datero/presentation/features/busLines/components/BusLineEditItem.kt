package labs.alexandre.datero.presentation.features.busLines.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.presentation.ui.component.BusLineFlag
import labs.alexandre.datero.presentation.ui.component.BusLineFlagScale
import sh.calvin.reorderable.ReorderableCollectionItemScope

@Composable
fun ReorderableCollectionItemScope.BusLineEditItem(
    isDragging: Boolean,
    busLine: BusLineUiModel,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    onClickDelete: () -> Unit,
    onDragStarted: () -> Unit,
    onDragStopped: () -> Unit
) {
    val elevation by animateDpAsState(if (isDragging) 8.dp else 0.dp)

    Row(
        modifier = Modifier
            .clickable { onItemClick() }
            .shadow(elevation)
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClickDelete) {
            Icon(
                painter = painterResource(R.drawable.ic_remove),
                contentDescription = stringResource(R.string.bus_lines_content_description_edit_item_icon_remove)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        BusLineFlag(colors = busLine.colors, scale = BusLineFlagScale.Medium)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = busLine.name, style = MaterialTheme.typography.titleSmall)
        Spacer(
            modifier = Modifier
                .widthIn(min = 8.dp)
                .weight(1F)
        )
        Box(
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .draggableHandle(
                    onDragStarted = { onDragStarted() },
                    onDragStopped = { onDragStopped() }
                )
                .longPressDraggableHandle(
                    onDragStarted = { onDragStarted() },
                    onDragStopped = { onDragStopped() }
                )
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = {},
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_drag),
                contentDescription = stringResource(R.string.bus_lines_content_description_edit_item_icon_drag),
            )
        }
    }
}