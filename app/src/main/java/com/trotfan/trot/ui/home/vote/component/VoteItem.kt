package com.trotfan.trot.ui.home.vote.component

import android.content.Context
import android.content.Intent
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
import com.trotfan.trot.model.Star
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.ui.home.vote.voteTopShareText
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable
import com.trotfan.trot.ui.utils.clickableSingle
import com.trotfan.trot.ui.utils.getShareChar


@Composable
fun VoteItem(
    beforeRank: Boolean = false,
    isMyStar: Boolean = false,
    star: VoteMainStar?,
    onSharedClick: () -> Unit,
    onVotingClick: (star: VoteMainStar?) -> Unit
) {

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
            .padding(
                top = if (beforeRank) 12.dp else 16.dp,
                bottom = if (beforeRank) 12.dp else 16.dp,
                start = 25.dp,
                end = 24.dp
            )
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(
                Modifier.wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!beforeRank) {
                    Text(
                        text = "현재",
                        color = Primary900,
                        style = FanwooriTypography.subtitle3,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = if (beforeRank) "-" else "${star?.rank}",
                    color = Primary900,
                    style = FanwooriTypography.subtitle3,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${star?.image}")
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 23.5.dp, end = 12.dp)
                    .size(if (beforeRank) 64.dp else 88.dp)
                    .clip(CircleShape)
                    .border(
                        width = if (beforeRank || isMyStar) 1.dp else 4.dp,
                        brush = if (beforeRank)
                            Brush.linearGradient(1f to Color(0xFFCFD5D8), 1f to Color(0xFFCFD5D8))
                        else {
                            if (isMyStar) {
                                Brush.linearGradient(
                                    1f to Color(0xFFFDEAEB),
                                    1f to Color(0xFFFDEAEB)
                                )
                            } else {
                                gradient01
                            }

                        },
                        shape = CircleShape
                    )
                    .border(
                        width = if (beforeRank || isMyStar) 0.dp else 8.dp,
                        color = Color.White,
                        CircleShape
                    )
            )


            Column(Modifier.wrapContentWidth()) {
                if (isMyStar) {
                    Row(
                        Modifier
                            .background(
                                brush = gradient01,
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
                    text = star?.name ?: "",
                    color = Gray800,
                    style = FanwooriTypography.subtitle1,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Text(
                    text = "${star?.votes} 표",
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
                    .size(width = 56.dp, height = 40.dp)
                    .clickableSingle {
                        onSharedClick()
                    },
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
                    .clip(RoundedCornerShape(20.dp))
                    .size(width = 72.dp, height = 40.dp)
                    .clickable {
                        onVotingClick(star)
                    },
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
    FanwooriTheme {
        VoteItem(
            star = VoteMainStar(id = 1, name = "임영웅", rank = 1, votes = 3000),
            onVotingClick = {},
            onSharedClick = {})
    }
}