package com.trotfan.trot.ui.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray300
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.utils.clickable

@Composable
fun LoginButton(
    text: String,
    icon: Painter?,
    textColor: Color = Gray700,
    borderColor: Color = Gray300,
    borderWidth: Dp = 1.dp,
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(borderWidth, borderColor, RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable {
                onClick()
            },
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        icon?.let {
            Image(
                modifier = Modifier
                    .align(CenterVertically),
                painter = it,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = textColor,
            style = FanwooriTypography.button1
        )
    }
}

@Preview
@Composable
fun AppleLoginButtonPreview() {
    FanwooriTheme {
        Surface {
            LoginButton(
                text = "Apple 계정으로 로그인",
                icon = painterResource(id = R.drawable.apple_symbol),
                textColor = Gray700,
                borderColor = Gray300
            ) {

            }
        }
    }
}