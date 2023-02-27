package com.trotfan.trot.ui.components.roulette

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun SpinWheelContent(
    modifier: Modifier = Modifier,
    spinSize: Dp,
    @IntRange(from = 2, to = 8) pieCount: Int,
    rotationDegree: Float,
    content: @Composable BoxScope.(pieIndex: Int) -> Unit
) {
    val pieAngle = 360f / pieCount
    val startOffset = 180
    val radius = (spinSize.value / 2)
    val pieRadius = getPieRadius(pieCount, radius)
    Box(
        modifier = modifier
            .size(spinSize),
        contentAlignment = Alignment.Center
    ) {
        for (pieIndex in 0 until pieCount) {
            val startAngle = pieAngle * pieIndex + startOffset + rotationDegree + pieAngle / 2
            val offsetX = -(pieRadius * sin(Math.toRadians(startAngle.toDouble()))).toFloat()
            val offsetY = (pieRadius * cos(Math.toRadians(startAngle.toDouble()))).toFloat()
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .offset(x = Dp(offsetX), y = Dp(offsetY))
                    .rotate(startAngle + 180),
                contentAlignment = Alignment.Center
            ) {
                content(pieIndex)
            }
        }
    }
}

fun getPieRadius(pieCount: Int, radius: Float): Float {
    return when (pieCount) {
        2 -> radius / 2f
        3 -> radius / 1.8f
        4 -> radius / 1.8f
        5 -> radius / 1.6f
        6 -> radius / 1.6f
        7 -> radius / 1.4f
        8 -> radius / 1.4f
        else -> radius / 2f
    }
}