package com.trotfan.trot.ui.components.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray800

@Composable
fun CustomTopAppBarWithIcon(
    modifier: Modifier = Modifier,
    title: String,
    @StringRes startIcon: Int = R.drawable.icon_info,
    @StringRes endIcon: Int = R.drawable.icon_share_aos,
    onClickStartIcon: () -> Unit = {},
    onClickEndIcon: () -> Unit = {}
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
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            text = title,
            textAlign = TextAlign.Start,
            color = Gray800,
            fontWeight = FontWeight.SemiBold,
            style = FanwooriTypography.subtitle1,
        )

        Icon(
            painter = painterResource(id = startIcon),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 28.3.dp)
                .clickable {
                    onClickStartIcon.invoke()
                }
        )

        Icon(
            painter = painterResource(id = endIcon),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable {
                    onClickEndIcon.invoke()
                }
        )
    }
}