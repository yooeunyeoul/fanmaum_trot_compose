package com.fanmaum.roullete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fanmaum.roullete.state.SpinWheelState
import kotlinx.coroutines.delay

@Composable
internal fun AnimatedSpinWheel(
    modifier: Modifier,
    state: SpinWheelState,
    size: Dp,
    frameWidth: Dp,
    pieColors: List<Color>,
    onClick: () -> Unit,
    isEnabled: Boolean,
    selectorIcon: Int,
    frameRes: Int,
    spinWheelVisibleState: SpinWheelVisibleState,
    content: @Composable BoxScope.(pieIndex: Int) -> Unit,
    spinWheelCenterContent: @Composable BoxScope.() -> Unit
) {
    LaunchedEffect(key1 = state.autoSpinDelay) {
        state.autoSpinDelay?.let {
            delay(it)
            state.spin()
        }
    }
    SpinWheelSelector(
        modifier = modifier,
        frameSize = size,
        selectorIcon = selectorIcon
    ) {
        SpinWheelFrame(
            modifier = modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp),
            frameSize = size,
            onClick = onClick,
            frameRes = frameRes,
            spinWheelVisibleState = spinWheelVisibleState,
        ) {
            SpinWheelPies(
                modifier = modifier,
                spinSize = size - frameWidth,
                pieCount = state.pieCount,
                pieColors = pieColors,
                rotationDegree = state.rotation.value,
                spinWheelVisibleState = spinWheelVisibleState,
                onClick = onClick,
                isEnabled = isEnabled,
                spinWheelCenterContent = spinWheelCenterContent,
                spinWheelContent = {
                    SpinWheelContent(
                        modifier = modifier,
                        spinSize = size - frameWidth,
                        pieCount = state.pieCount,
                        rotationDegree = state.rotation.value
                    ) {
                        content(it)
                    }
                }
            )
        }
    }
}
