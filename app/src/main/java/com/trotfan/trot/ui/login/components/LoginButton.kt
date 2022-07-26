package com.trotfan.trot.ui.login.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.TrotTheme

@Composable
fun LoginButton(
    text: String,
    icon: Painter
) {
    Button(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(24.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        onClick = {

        }) {
        Image(painter = icon, null)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun AppleLoginButtonPreview() {
    TrotTheme {
        Surface {
            LoginButton(
                "Apple 계정으로 로그인",
                painterResource(id = R.drawable.apple_symbol)
            )
        }
    }
}