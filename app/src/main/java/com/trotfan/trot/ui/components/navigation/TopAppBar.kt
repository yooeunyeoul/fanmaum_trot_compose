package com.trotfan.trot.ui.components.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray600
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.utils.clickable

@Composable
fun CustomTopAppBar(
    title: String,
    icon: Int? = null,
    onDismiss: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Gray700,
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .size(32.dp)
                    .clickable { onDismiss() }
            )
        }

        Text(
            text = title,
            color = Gray800,
            style = FanwooriTypography.body3
        )
    }
}