package com.trotfan.trot.ui.components.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray600
import com.trotfan.trot.ui.theme.Gray800

@Composable
fun CustomTopAppBarWithIcon(
    modifier: Modifier = Modifier,
    title: String,
    @StringRes icon: Int = R.drawable.icon_share,
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(Color.White)
            .alpha(0.9f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            fontSize = 20.sp,
            modifier = Modifier.weight(1f),
            text = title,
            textAlign = TextAlign.Start,
            color = Gray800,
            style = FanwooriTypography.body2,
        )
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null
        )
    }
}