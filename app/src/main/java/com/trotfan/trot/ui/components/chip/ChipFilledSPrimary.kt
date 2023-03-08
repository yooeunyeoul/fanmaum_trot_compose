package com.trotfan.trot.ui.components.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Primary100
import com.trotfan.trot.ui.theme.Primary800

@Composable
fun ChipFilledSPrimary(
    modifier: Modifier, text: String
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Primary100),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = FanwooriTypography.button2,
            color = Primary800,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 6.dp, end = 6.dp, top = 1.dp, bottom = 1.dp)
        )
    }
}