package com.trotfan.trot.ui.components

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.background
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
fun ContainedButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier,
    onClick: () -> Unit
) {
    var backgroundColor by remember {
        mutableStateOf(Primary500)
    }

    var textColor by remember {
        mutableStateOf(Color.White)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        backgroundColor = Primary700
                        textColor = Gray400
                        onClick()
                    }
                    MotionEvent.ACTION_UP -> {
                        backgroundColor = Primary500
                        textColor = Color.White
                    }
                }
                true
            }
            .background(if (enabled) backgroundColor else Gray300),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = text,
            color = if (enabled) textColor else Gray500,
            style = FanwooriTypography.button1
        )
    }
}