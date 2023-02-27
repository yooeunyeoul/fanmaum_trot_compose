package com.trotfan.trot.ui.components.roulette

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
internal fun SpinWheelFrame(
    modifier: Modifier = Modifier,
    frameSize: Dp,
    onClick: () -> Unit,
    frameRes: Int,
    spinWheelVisibleState: SpinWheelVisibleState,
    spinWheel: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = frameRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Canvas(
            modifier = Modifier
                .size(frameSize)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        ) {

        }
        spinWheel()
    }
}