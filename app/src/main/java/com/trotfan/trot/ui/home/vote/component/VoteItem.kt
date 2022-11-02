package com.trotfan.trot.ui.home.vote.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
fun VoteItem(isAfterMakingRanking: Boolean = false, isMyStar: Boolean = false) {

    val strokeWidth = LocalDensity.current.run { 1.dp.toPx() }
    val margin = LocalDensity.current.run { 24.dp.toPx() }
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                color = if (isMyStar) Primary50 else Color.White,
                shape = RoundedCornerShape(24.dp)
            )
            .drawBehind {

                val x = size.width - strokeWidth
                val y = size.height
                drawLine(
                    color = if (isMyStar) Primary100 else Gray200,
                    start = Offset(margin, y),
                    end = Offset(x - margin, y),
                    strokeWidth = strokeWidth
                )
            }
            .padding(top = 16.dp, bottom = 16.dp, start = 25.dp, end = 24.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(
                Modifier.wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "현재",
                    color = Primary900,
                    style = FanwooriTypography.subtitle3,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )

                Text(
                    text = "2",
                    color = Primary900,
                    style = FanwooriTypography.subtitle3,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )

            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.shutterstock.com/image-vector/sample-red-square-grunge-stamp-260nw-338250266.jpg")
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(start = 23.5.dp, end = 12.dp)
                    .border(
                        width = 4.dp,
                        brush = Brush.linearGradient(
                            0.13f to Color(0xFF7366D9),
                            0.36f to Color(0xFFAB9FFB),
                            0.8f to Color(0xFFF7ACAE),
                            1.0f to Color(0xFFFDEAEB)

                        ),
                        shape = CircleShape
                    )
                    .size(88.dp)
                    .border(width = 8.dp, color = Color.Black, CircleShape)
            )

            Column(Modifier.wrapContentWidth()) {
                if (isMyStar) {
                    Row(
                        Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    0.0f to Color(0xFF4E43B3),
                                    0.37f to Color(0xFF6E61D7),
                                    0.7f to Color(0xFFB1A0F6),
                                    1.0f to Color(0xFFF8ADAF)

                                ),
                                shape = RoundedCornerShape(20.dp),
                            )
                            .size(width = 64.dp, height = 24.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "내 스타",
                            color = Color.White,
                            style = FanwooriTypography.button2,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                        Icon(
                            painter = painterResource(R.drawable.icon_star),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(width = 10.dp, height = 9.54.dp)
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "임영웅",
                    color = Gray800,
                    style = FanwooriTypography.subtitle1,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Text(
                    text = "10,876,287,000 표",
                    color = Gray800,
                    style = FanwooriTypography.body5,
                    fontSize = 17.sp
                )

            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .background(color = Gray100, shape = RoundedCornerShape(20.dp))
                    .size(width = 56.dp, height = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_share_aos),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                Modifier
                    .background(color = Primary500, shape = RoundedCornerShape(20.dp))
                    .size(width = 72.dp, height = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "투표",
                    color = Color.White,
                    style = FanwooriTypography.button1,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                )
            }


        }
    }
}

@Preview
@Composable
fun PreviewVoteItem() {
    VoteItem()
}