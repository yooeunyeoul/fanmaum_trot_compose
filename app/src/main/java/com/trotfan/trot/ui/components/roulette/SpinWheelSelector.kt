package com.trotfan.trot.ui.components.roulette

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


val pointerSize = 64.dp

@Composable
internal fun SpinWheelSelector(
    modifier: Modifier = Modifier,
    frameSize: Dp,
    selectorIcon: Int,
    spinWheelFrame: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .size(width = frameSize, height = frameSize + pointerSize / 2)
            .background(color = Color.Transparent),
    ) {
        spinWheelFrame()
        Image(
            painter = painterResource(id = selectorIcon),
            contentDescription = null,
            modifier = Modifier
                .size(width = 40.dp, height = pointerSize)
                .align(Alignment.TopCenter)

        )
    }
}