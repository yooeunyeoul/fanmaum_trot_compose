package com.trotfan.trot.ui.components.roulette

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.components.roulette.state.SpinWheelState
import com.trotfan.trot.ui.components.roulette.state.rememberSpinWheelState

@Composable
fun SpinWheel(
    modifier: Modifier = Modifier,
    state: SpinWheelState = rememberSpinWheelState(),
    dimensions: SpinWheelDimensions = SpinWheelDefaults.spinWheelDimensions(),
    colors: SpinWheelColors = SpinWheelDefaults.spinWheelColors(),
    onClick: () -> Unit = {},
    isEnabled: Boolean,
    selectorIcon: Int,
    frameRes: Int,
    spinWheelVisibleState: SpinWheelVisibleState = SpinWheelVisibleState.Available,
    content: @Composable BoxScope.(pieIndex: Int) -> Unit,
    spinWheelCenterContent: @Composable BoxScope.() -> Unit
) {
    AnimatedSpinWheel(
        modifier = modifier,
        state = state,
        size = dimensions.spinWheelSize().value,
        frameWidth = dimensions.frameWidth().value,
        pieColors = colors.pieColors().value,
        onClick = onClick,
        isEnabled = isEnabled,
        selectorIcon = selectorIcon,
        frameRes = frameRes,
        spinWheelVisibleState = spinWheelVisibleState,
        content = content,
        spinWheelCenterContent = spinWheelCenterContent
    )
}

enum class SpinWheelVisibleState {
    Available, Waiting, UnAvailable
}

object SpinWheelDefaults {
    @Composable
    fun spinWheelColors(
        frameColor: Color = Color(0xFF941c2f),
        pieColors: List<Color> = listOf(
            Color(0xFFef476f),
            Color(0xFFf78c6b),
            Color(0xFFffd166),
            Color(0xFF83d483),
            Color(0xFF06d6a0),
            Color(0xFF0cb0a9),
            Color(0xFF118ab2),
            Color(0xFF073b4c)
        )
    ): SpinWheelColors = DefaultSpinWheelColors(
        frameColor = frameColor,
        pieColors = pieColors
    )

    @Composable
    fun spinWheelDimensions(
        spinWheelSize: Dp = 240.dp,
        frameWidth: Dp = 10.dp,
    ): SpinWheelDimensions = DefaultSpinWheelDimensions(
        spinWheelSize = spinWheelSize,
        frameWidth = frameWidth
    )
}

interface SpinWheelColors {
    @Composable
    fun frameColor(): State<Color>

    @Composable
    fun pieColors(): State<List<Color>>
}

@Immutable
private class DefaultSpinWheelColors(
    private val frameColor: Color,
    private val pieColors: List<Color>
) : SpinWheelColors {

    @Composable
    override fun frameColor(): State<Color> {
        return rememberUpdatedState(frameColor)
    }

    @Composable
    override fun pieColors(): State<List<Color>> {
        return rememberUpdatedState(pieColors)
    }
}

interface SpinWheelDimensions {
    @Composable
    fun spinWheelSize(): State<Dp>

    @Composable
    fun frameWidth(): State<Dp>
}

@Immutable
private class DefaultSpinWheelDimensions(
    private val spinWheelSize: Dp,
    private val frameWidth: Dp,
) : SpinWheelDimensions {

    @Composable
    override fun spinWheelSize(): State<Dp> {
        return rememberUpdatedState(spinWheelSize)
    }

    @Composable
    override fun frameWidth(): State<Dp> {
        return rememberUpdatedState(frameWidth)
    }
}