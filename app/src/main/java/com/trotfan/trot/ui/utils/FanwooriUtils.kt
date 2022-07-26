package com.trotfan.trot.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed


inline fun Modifier.clickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = rememberRipple(bounded = true),
        interactionSource = MutableInteractionSource()
    ) {
        onClick()
    }
}