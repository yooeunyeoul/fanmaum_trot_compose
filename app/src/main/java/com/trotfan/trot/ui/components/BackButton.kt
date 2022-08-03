package com.trotfan.trot.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.Gray300
import com.trotfan.trot.ui.theme.Gray700


@Composable
fun BackIcon(onCLick: () -> Unit) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by interactionSource.collectIsPressedAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(48.dp).clickable(
            indication = null,
            interactionSource = interactionSource
        ) {
            onCLick.invoke()
        },
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.iconback_default
            ), contentDescription = null,
            tint = if (isPressed) Gray300 else Gray700
        )
    }
}

@Preview
@Composable
fun PreviewBackIcon() {
    BackIcon(onCLick = {})
}
