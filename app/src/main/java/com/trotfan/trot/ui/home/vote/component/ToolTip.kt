package com.trotfan.trot.ui.home.vote.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.TooltipTriangle
import com.trotfan.trot.ui.theme.gradient01

enum class ToolTipPosition {
    Start, Finish
}

@Composable
fun ToolTip(modifier: Modifier = Modifier) {

    var tooltipState by remember { mutableStateOf(ToolTipPosition.Start) }

    val offsetAnimation: Dp by animateDpAsState(
        if (tooltipState == ToolTipPosition.Start) 5.dp else 10.dp,
        infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Reverse)
    )

    LaunchedEffect(true) {
        tooltipState = ToolTipPosition.Finish
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .absoluteOffset(y = offsetAnimation)
    ) {
        Box(
            Modifier
                .height(40.dp)
                .background(
                    brush = gradient01,
                    shape = RoundedCornerShape(20.dp),
                )
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "최종 순위는 어떻게 선정되나요?",
                style = FanwooriTypography.button1,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 17.sp,
                modifier = Modifier.align(Alignment.Center)
            )

        }
        Box(
            Modifier
                .padding(start = 20.dp)
                .width(16.dp)
                .height(8.dp)
                .rotate(180f)
                .background(color = TooltipTriangle, shape = TriangleShape)
        )

    }
}

@Preview
@Composable
fun PreviewToolTip() {
    ToolTip()
}

private val TriangleShape = GenericShape { size, _ ->
    // 1)
    moveTo(size.width / 2f, 0f)

    // 2)
    lineTo(size.width, size.height)

    // 3)
    lineTo(0f, size.height)
}