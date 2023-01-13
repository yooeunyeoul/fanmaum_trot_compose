package com.trotfan.trot.ui.home.mypage.modify.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
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
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.Gray100
import com.trotfan.trot.ui.theme.Gray200
import com.trotfan.trot.ui.theme.gradient03

@Composable
fun ProfileImgModify(modifier: Modifier) {
    Box(modifier = modifier.size(136.dp)) {
        Box(
            modifier = Modifier
                .size(136.dp)
                .border(2.dp, Gray200, CircleShape)
                .clip(CircleShape)
                .background(Gray100)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mypage_profile),
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.Center)
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
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    brush = gradient03,
                    shape = CircleShape,
                )
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_camera),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}

@Preview
@Composable
fun ProfileImgModifyPreview() {
    FanwooriTheme {
        ProfileImgModify(Modifier)
    }
}