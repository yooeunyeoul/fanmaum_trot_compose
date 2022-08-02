package com.trotfan.trot.ui.signup.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable

@Composable
fun ListItemButton(
    text: String,
    subText: String?,
    imageUrl: String?,
    textColor: Color = Gray700,
    subTextColor: Color = Gray600,
    checkedTrailingIcon: Int,
    unCheckedTrailingIcon: Int,
    checked: Boolean = false,
    checkedIconTint: Color = Primary500,
    unCheckedIconTint: Color = Gray600,
    index: Int,
    onClick: (Int) -> Unit,
) {
    val animatedColor by animateColorAsState(
        targetValue = if (checked) Primary100 else Gray100,
        animationSpec = tween(120, easing = LinearEasing)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(index)
            },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = animatedColor,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(74.dp)
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    top = 9.dp,
                    bottom = 9.dp,
                    end = 12.dp
                )
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.apple_symbol),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(56.dp)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = text,
                    color = textColor,
                    style = FanwooriTypography.button1
                )
                if (subText != null) {
                    Text(
                        text = subText ?: "",
                        color = subTextColor,
                        style = FanwooriTypography.caption2
                    )
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
            Icon(
                tint = if (checked) checkedIconTint else unCheckedIconTint,
                painter = painterResource(
                    id = if (checked) checkedTrailingIcon
                    else unCheckedTrailingIcon
                ),
                contentDescription = null
            )


        }
    }
}


@Composable
@Preview
fun PreviewListItemButtonNotSub() {
    ListItemButton(
        text = "파쿵야파쿵야파쿵야파쿵야파쿵야파쿵야파쿵야파쿵야파쿵야",
        subText = null,
        imageUrl = "",
        checkedTrailingIcon = R.drawable.icon_heartfilled,
        unCheckedTrailingIcon = R.drawable.icon_heart,
        onClick = {},
        index = 0
    )
}

