package com.fanmaum.roullete.state

import android.util.Log
import androidx.annotation.IntRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

data class SpinWheelState(
    internal val pieCount: Int,
    private val durationMillis: Int,
    private val delayMillis: Int,
    private val rotationPerSecond: Float,
    private val easing: Easing,
    private val startDegree: Float,
    internal val autoSpinDelay: Long? = null,
) {
    internal var rotation by mutableStateOf(Animatable(startDegree))
    var resultDegree: Float = 0f

    private val _spinAnimationState = MutableStateFlow(SpinAnimationState.STOPPED)
    val spinAnimationState = _spinAnimationState.asStateFlow()

    suspend fun animate(onFinish: (pieIndex: Int) -> Unit = {}) {
        when (spinAnimationState.value) {
            SpinAnimationState.STOPPED -> {
                spin(onFinish = onFinish)
            }
            SpinAnimationState.SPINNING -> {
//                reset()
            }
        }
    }

    suspend fun spin(onFinish: (pieIndex: Int) -> Unit = {}) {
        if (spinAnimationState.value == SpinAnimationState.STOPPED) {

            _spinAnimationState.value = SpinAnimationState.SPINNING

            val randomRotationDegree = generateRandomRotationDegree()

            rotation.animateTo(
                targetValue = (360f * rotationPerSecond * (durationMillis / 1000)) + (resultDegree
                    ?: randomRotationDegree),
                animationSpec = tween(
                    durationMillis = durationMillis,
                    delayMillis = delayMillis,
                    easing = easing
                )
            )
            Log.e("resultDegree2", resultDegree.toString())

            val pieDegree = 360f / pieCount
            val quotient = (resultDegree ?: randomRotationDegree).toInt() / pieDegree.toInt()
            val resultIndex = pieCount - quotient - 1


            onFinish(resultIndex)

            rotation.snapTo(resultDegree ?: randomRotationDegree)

            _spinAnimationState.value = SpinAnimationState.STOPPED

            autoSpinDelay?.let {
                delay(it)
                spin()
            }
        }
    }

    suspend fun reset() {
//        if(spinAnimationState == SpinAnimationState.SPINNING) {

        rotation.snapTo(startDegree)

        _spinAnimationState.value = SpinAnimationState.STOPPED
//        }
    }

    private fun generateRandomRotationDegree(): Float {
        return Random().nextInt(360).toFloat()
    }
}

enum class SpinAnimationState {
    STOPPED, SPINNING
}

@Composable
fun rememberSpinWheelState(
    @IntRange(from = 2, to = 8) pieCount: Int = 8,
    durationMillis: Int = 12000,
    delayMillis: Int = 0,
    rotationPerSecond: Float = 1f,
    easing: Easing = CubicBezierEasing(0.16f, 1f, 0.3f, 1f),
    startDegree: Float = 0f,
    autoSpinDelay: Long? = null
): SpinWheelState {
    return remember {
        SpinWheelState(
            pieCount,
            durationMillis,
            delayMillis,
            rotationPerSecond,
            easing,
            startDegree,
            autoSpinDelay
        )
    }
}
