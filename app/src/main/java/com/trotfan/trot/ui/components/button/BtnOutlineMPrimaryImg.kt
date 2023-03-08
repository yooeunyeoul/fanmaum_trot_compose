package com.trotfan.trot.ui.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*

@Composable
fun BtnOutlineMPrimaryImg(
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
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                1.dp,
                if (enabled) {
                    Primary200
                } else Gray200, RoundedCornerShape(20.dp)
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
                if (isPressed) {
                    Primary100
                } else {
                    Color.White
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_vote_iconcolored),
            contentDescription = null,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = if (enabled) {
                Primary600
            } else Gray400,
            style = FanwooriTypography.button1,
            maxLines = 1,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Preview(name = "IconOutline3Button")
@Composable
fun PreviewIconOutline3Button() {
    BtnOutlineMPrimaryImg(text = "Enabled",
        icon = R.drawable.ic_vote_iconcolored,
        onClick = {})


}