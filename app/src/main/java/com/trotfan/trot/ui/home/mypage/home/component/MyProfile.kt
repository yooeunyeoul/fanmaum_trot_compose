package com.trotfan.trot.ui.home.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*

@Composable
fun MyProfile() {
    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(104.dp)
                .border(2.dp, Gray300, CircleShape)
                .clip(CircleShape)
                .background(Gray200)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mypage_profile),
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .align(Center)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(null)
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .align(CenterVertically)
                .weight(1f)
        ) {
            Row(
                Modifier
                    .background(
                        brush = gradient01,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .size(width = 64.dp, height = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_star),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(width = 10.dp, height = 9.54.dp)
                )

                Text(
                    text = "임영웅",
                    color = Color.White,
                    style = FanwooriTypography.button2,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "닉네임입니다", style = FanwooriTypography.subtitle1, color = Gray900)

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(text = "프로필수정", style = FanwooriTypography.body2, color = Gray700)
                Icon(
                    painter = painterResource(id = R.drawable.icon_pen),
                    contentDescription = null,
                    tint = Gray850,
                    modifier = Modifier.align(CenterVertically)
                )
            }
        }
    }
}

@Preview
@Composable
fun MyProfilePreview() {
    FanwooriTheme {
        MyProfile()
    }
}