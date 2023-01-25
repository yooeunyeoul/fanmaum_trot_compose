package com.trotfan.trot.ui.components.chip

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.trotfan.trot.R
import com.trotfan.trot.ui.home.BottomNavHeight
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray900

@Composable
fun BoxScope.ChipCapsuleImg() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.ranking_arrow))
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Gray900,
        elevation = 3.dp,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = BottomNavHeight.plus(32.dp))
            .zIndex(2f)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "화면을 내려보세요",
                color = Color.White,
                fontSize = 15.sp,
                style = FanwooriTypography.body2,
                fontWeight = FontWeight.Medium
            )

            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(40.dp)
            )
        }
    }

}