package labs.alexandre.datero.presentation.features.busLineDetail.components.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.ui.util.isHexColorDark
import labs.alexandre.datero.presentation.ui.util.toColorIntOrNull
import sh.calvin.reorderable.ReorderableCollectionItemScope

@Composable
fun ReorderableCollectionItemScope.StripBusLine(
    isDragging: Boolean,
    isDeleteEnabled: Boolean,
    isDuplicatedVisible: Boolean,
    strip: Pair<String, String>,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    onClickDelete: () -> Unit,
    onDragStarted: () -> Unit,
    onDragStopped: () -> Unit,
    onClickDuplicate: () -> Unit
) {
    val elevation by animateDpAsState(if (isDragging) 8.dp else 0.dp)
    val tintIcon = remember(strip) {  if (isHexColorDark(strip.second)) Color.White else Color.Black }

    Row(
        modifier = Modifier
            .shadow(elevation)
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 4.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onClickDelete,
            enabled = isDeleteEnabled
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.bus_line_detail_strip_content_description_icon_duplicate_strip),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = Modifier
                .weight(1F)
                .clickable { onItemClick() }
                .border(1.dp, Color.Black)
                .background(strip.second.toColorIntOrNull() ?: Color.Unspecified),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            AnimatedVisibility(isDuplicatedVisible) {
                IconButton(
                    onClick = onClickDuplicate
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_duplicate),
                        contentDescription = stringResource(R.string.bus_line_detail_strip_content_description_icon_duplicate_strip),
                        tint = tintIcon
                    )
                }
            }

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
                    tint = tintIcon
                )
            }
        }
    }
}