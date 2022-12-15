package com.trotfan.trot.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*

@Composable
fun BtnOutlineSecondaryLeftIcon(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    icon: Int,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Row(
        modifier = modifier
            .height(48.dp)
            .defaultMinSize(minWidth = 170.dp, minHeight = 48.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(
                1.dp,
                if (enabled) {
                    Secondary300
                } else Gray200, RoundedCornerShape(14.dp)
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                if (enabled) {
                    onClick()
                }
            }
            .background(
                if (enabled) {
                    if (isPressed) {
                        Secondary100
                    } else {
                        Secondary50
                    }
                } else Gray100
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.border(
                width = 2.dp,
                color = Secondary400, shape = CircleShape
            )
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Secondary500
            )
        }

        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = if (enabled) {
                if (isPressed) Secondary800 else Secondary900
            } else Gray400,
            style = FanwooriTypography.button1,
            maxLines = 1
        )
    }
}

@Preview(name = "IconOutline1Button")
@Composable
fun PreviewIconOutline1Button() {
    BtnOutlineSecondaryLeftIcon(text = "Enabled",
        icon = R.drawable.icon_add,
        onClick = {})


}