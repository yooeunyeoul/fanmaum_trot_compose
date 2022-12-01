package com.trotfan.trot.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Primary800

@Composable
fun UnderlineTextButton(text: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = text,
            style = FanwooriTypography.button1,
            color = Primary800,
            modifier = Modifier.padding(start = 2.dp)
        )

        Box(
            modifier = Modifier
                .background(Primary800)
                .width(62.dp)
                .height(1.dp)
        )
    }
}

@Composable
@Preview
fun UnderlineTextButtonPreview() {
    UnderlineTextButton(text = "언더라인")
}