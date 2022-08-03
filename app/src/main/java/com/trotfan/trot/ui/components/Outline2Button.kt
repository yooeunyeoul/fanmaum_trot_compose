package com.trotfan.trot.ui.components

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Outline2Button(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    var borderColor by remember {
        mutableStateOf(Gray300)
    }

    var textColor by remember {
        mutableStateOf(Gray600)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        borderColor = Gray500
                        textColor = Gray800
                        if (enabled) {
                            onClick()
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        borderColor = Gray300
                        textColor = Gray600
                    }
                }
                true
            }
            .border(1.dp, if (enabled) borderColor else Gray300, RoundedCornerShape(24.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = text,
            color = if (enabled) textColor else Gray400,
            style = FanwooriTypography.button1
        )
    }
}