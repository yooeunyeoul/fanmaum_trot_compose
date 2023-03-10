package com.trotfan.trot.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.*

@Composable
fun BtnFilledMBlack(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Row(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(21.dp))
            .background(
                if (enabled) {
                    if (isPressed) Gray600 else Gray800
                } else Gray200,
            )
            .padding(start = 18.dp, end = 18.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                if (enabled) {
                    onClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = text,
            color = Color.White,
            style = FanwooriTypography.subtitle3
        )
    }
}