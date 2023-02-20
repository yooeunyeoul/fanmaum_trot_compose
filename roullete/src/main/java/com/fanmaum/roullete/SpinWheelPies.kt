package com.fanmaum.roullete

import androidx.annotation.IntRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
internal fun SpinWheelPies(
    modifier: Modifier = Modifier,
    spinSize: Dp,
    @IntRange(from = 2, to = 8) pieCount: Int,
    pieColors: List<Color>,
    rotationDegree: Float,
    onClick: () -> Unit,
    isEnabled: Boolean,
    spinWheelVisibleState: SpinWheelVisibleState,
    spinWheelContent: @Composable BoxScope.() -> Unit,
    spinWheelCenterContent: @Composable BoxScope.() -> Unit
) {
    val pieAngle = 360f / pieCount
    val startAngleOffset = 270
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        spinWheelCenterContent()
        Canvas(
            modifier = Modifier
                .size(spinSize)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                    enabled = isEnabled
                )
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            for (i in 0 until pieCount) {
                val startAngle = pieAngle * i + rotationDegree + startAngleOffset
                val nextColor = pieColors.getOrElse(i) { Color.LightGray }
                drawArc(
                    color = nextColor,
                    startAngle = startAngle,
                    sweepAngle = pieAngle,
                    useCenter = true,
                    size = Size(canvasWidth, canvasHeight)
                )
            }
        }
        spinWheelContent()
    }
}