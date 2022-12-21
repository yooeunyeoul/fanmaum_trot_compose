package com.trotfan.trot.ui.components.chip

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.FanwooriTypography

@Composable
fun ChipFilledMSecondary(
    modifier: Modifier, text: String, textColor: Color
) {
    Box(
        modifier = modifier
            .height(32.dp)
    ) {
        Text(
            text = text,
            style = FanwooriTypography.button1,
            color = textColor,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp)
        )
    }
}