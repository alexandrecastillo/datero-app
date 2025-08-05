package labs.alexandre.datero.presentation.features.busLineDetail.components.dialog

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import labs.alexandre.datero.R
import labs.alexandre.datero.presentation.features.busLineDetail.state.DialogColorPickerUiState
import labs.alexandre.datero.presentation.theme.DateroTheme
import labs.alexandre.datero.presentation.ui.util.Constant
import labs.alexandre.datero.presentation.ui.util.toColorIntOrNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetColorPicker(
    colorPickerUiState: DialogColorPickerUiState,
    onClickSave: (colorId: String?, colorHex: String) -> Unit,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { value -> true }
    )

    ModalBottomSheet(
        sheetState = modalBottomSheetState,
        onDismissRequest = onDismiss,
        contentWindowInsets = { WindowInsets(top = 16.dp, bottom = 16.dp) }
    ) {
        val controller = rememberColorPickerController()
        val hexCode = remember { mutableStateOf("") }

        Text(
            text = when (colorPickerUiState.currentIdColor == null) {
                true -> stringResource(R.string.bus_line_detail_dialog_pick_color_title_new_strip)
                false -> stringResource(R.string.bus_line_detail_dialog_pick_color_title_edit_strip)
            },
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                hexCode.value = "${Constant.HASHTAG}${colorEnvelope.hexCode}"
            },
            initialColor = colorPickerUiState.currentPickColor?.toColorIntOrNull()
        )
        Spacer(modifier = Modifier.height(16.dp))
        BrightnessSlider(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(36.dp),
            controller = controller,
            initialColor = colorPickerUiState.currentPickColor?.toColorIntOrNull()
        )
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.bus_line_detail_dialog_pick_color_chosen_color),
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlphaTile(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(6.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(6.dp)),
            controller = controller
        )
        Spacer(modifier = Modifier.height(32.dp))
        ButtonSaveColor(
            modifier = Modifier.padding(horizontal = 24.dp),
            enabled = hexCode.value.isNotBlank(),
            onClick = { onClickSave(colorPickerUiState.currentIdColor, hexCode.value) }
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ButtonSaveColor(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        Text(
            text = stringResource(R.string.bus_line_detail_dialog_pick_color_button_save)
        )
    }
}

@Composable
@Preview
fun PreviewBottomSheetColorPicker() {
    DateroTheme {
        BottomSheetColorPicker(
            colorPickerUiState = DialogColorPickerUiState.DEFAULT,
            { a, b -> },
            {})
    }
}