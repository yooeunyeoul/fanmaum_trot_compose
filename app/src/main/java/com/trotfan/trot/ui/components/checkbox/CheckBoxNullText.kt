package com.trotfan.trot.ui.components.checkbox

import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.Gray300
import com.trotfan.trot.ui.theme.Primary500

@Composable
fun CheckBoxNullText(
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Checkbox(
        checked = isChecked,
        onCheckedChange = {
            onCheckedChange()
        },
        colors = CheckboxDefaults.colors(
            checkedColor = Primary500,
            uncheckedColor = Gray300
        ),
        modifier = modifier
            .size(22.dp)
    )
}