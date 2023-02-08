package com.trotfan.trot.ui.components.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.utils.clickableSingle

@Composable
fun AppbarMLeftIcon(
    modifier: Modifier = Modifier,
    title: String,
    onIconClick: () -> Unit = {},
    icon: Int? = null,
    textColor: Color? = null,
    iconColor: Color? = null
) {
    Row(
        modifier = modifier
            .height(titleBarHeight)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = iconColor ?: Gray800,
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .size(32.dp)
                    .clickableSingle { onIconClick() }
            )
        }
        Text(
            text = title,
            color = textColor ?: Gray800,
            style = FanwooriTypography.body3,
            modifier = Modifier.weight(1f)
        )
    }
}