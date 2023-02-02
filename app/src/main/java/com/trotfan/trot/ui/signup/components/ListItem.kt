package com.trotfan.trot.ui.signup.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.R
import com.trotfan.trot.ui.signup.Sample
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable

@Composable
fun ListItemButton(
    text: String,
    subText: String?,
    imageUrl: String?,
    textColor: Color = Gray900,
    subTextColor: Color = Gray600,
    selectedTextColor: Color = Primary900,
    selectedSubTextColor: Color = Primary700,
    checkedTrailingIcon: Int,
    unCheckedTrailingIcon: Int,
    checked: Boolean = false,
    checkedIconTint: Color = Primary500,
    unCheckedIconTint: Color = Gray700,
    item: Any?,
    onClick: (Any?) -> Unit,
) {
    val animatedColor by animateColorAsState(
        targetValue = if (checked) Primary100 else Gray100,
        animationSpec = tween(120, easing = LinearEasing)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(item)
            }
            .border(
                if (checked) 1.dp
                else 0.dp,
                color = if (checked) Primary100 else Gray100,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = animatedColor,
        elevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(74.dp)
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 12.dp
                )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl).crossfade(true).build(),
                contentDescription = null,
                error = painterResource(id = com.google.android.material.R.drawable.mtrl_ic_error),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(56.dp)
                    .border(width = 1.dp, color = Gray300, CircleShape)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = text,
                    color = if (checked) selectedTextColor else textColor,
                    style = FanwooriTypography.button1
                )
                if (subText != null) {
                    Text(
                        text = subText ?: "",
                        color = if (checked) selectedSubTextColor else subTextColor,
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
                contentDescription = null,
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
        item = Sample()
    )
}

