package com.trotfan.trot.ui.components.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray200
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.utils.clickable

@Composable
fun ChipTab(
    modifier: Modifier, text: String, isOn: Boolean, onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isOn) Gray800 else Color.White)
            .border(
                border = if (isOn) BorderStroke(0.dp, Gray800) else BorderStroke(
                    1.dp,
                    Gray200
                ),
                shape = RoundedCornerShape(20.dp)
            ).clickable {
                onClick()
            }
    ) {
        Text(
            text = text,
            style = FanwooriTypography.button1,
            color = if (isOn) Color.White else Gray700,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 20.dp, end = 20.dp)
        )
    }
}