package com.trotfan.trot.ui.components.navigation

import androidx.compose.foundation.background
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
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.theme.Gray900
import com.trotfan.trot.ui.utils.clickable
val titleBarHeight = 56.dp
@Composable
fun AppbarL(
    title: String,
    onDismiss: () -> Unit = {},
    icon: Int? = null,
    modifier: Modifier = Modifier

) {
    Row(
        modifier = modifier
            .height(titleBarHeight)
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            color = Gray900,
            style = FanwooriTypography.h2,
            modifier = Modifier.weight(1f)
        )

        icon?.let {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Gray700,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp)
            )
        }
    }
}