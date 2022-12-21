package com.trotfan.trot.ui.home.ranking.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.R
import com.trotfan.trot.ui.signup.Sample
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.NumberComma
import com.trotfan.trot.ui.utils.clickable

val rankDrawables = listOf(
    R.drawable.ranking_monthlytop1,
    R.drawable.ranking_monthlytop2,
    R.drawable.ranking_monthlytop3
)

val imageRankDrawable = listOf(
    R.drawable.ranking_monthlytop1,
    R.drawable.ranking_monthlytop2,
    R.drawable.ranking_monthlytop3
)

@Composable
fun RankItem(
    text: String,
    subText: Int?,
    imageUrl: String?,
    checked: Boolean = false,
    rank: Int,
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
            },
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(72.dp)
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 12.dp
                )
        ) {
            if (rank < 4) {
                Image(
                    painter = painterResource(
                        id = when (rank) {
                            1 -> {
                                rankDrawables[0]
                            }
                            2 -> {
                                rankDrawables[1]
                            }
                            3 -> {
                                rankDrawables[2]
                            }
                            else -> {
                                rankDrawables[0]
                            }
                        }
                    ), contentDescription = null
                )
                Spacer(modifier = Modifier.width(12.dp))

            } else {
                Text(text = "4", style = FanwooriTypography.subtitle3, color = Gray700)
                Spacer(modifier = Modifier.width(19.dp))
            }

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
                    color = Gray900,
                    style = FanwooriTypography.subtitle1,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${NumberComma.decimalFormat.format(1000)}점",
                    color = Gray700,
                    style = FanwooriTypography.body2,
                    maxLines = 1
                )

            }
            Spacer(modifier = Modifier.size(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow),
                contentDescription = null,
                tint = Gray600
            )


        }
    }
}

val textShadow =
    Shadow(color = Color.Black.copy(alpha = 0.30f), offset = Offset(2f, 2f), blurRadius = 4f)

@Composable
fun RankImageItem() {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .height(274.dp)
                .padding(start = 16.dp, end = 16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                ),
        ) {

            Box(
                Modifier
                    .weight(1f)
                    .background(color = Color.White)
            ) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Image(
                        painter = painterResource(id = R.drawable.ranking_1st_03),
                        contentDescription = null,
                        modifier = Modifier
                            .size(72.dp)
                            .align(Alignment.TopStart)
                            .zIndex(1f)
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .zIndex(2f)
                            .padding(start = 16.dp, bottom = 12.dp)
                    ) {
                        Text(
                            text = "1,500점",
                            style = FanwooriTypography.body3.copy(shadow = textShadow),
                            color = Color.White,
                            modifier = Modifier
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "가수명",
                                style = FanwooriTypography.subtitle1.copy(shadow = textShadow),
                                color = Color.White
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.icon_arrow),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                    }


                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2022/07/26/50865541-b973-46f4-9859-3298b9acbc5c.jpeg")
                            .crossfade(true).build(),
                        contentDescription = null,
                        error = painterResource(id = com.google.android.material.R.drawable.mtrl_ic_error),
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                    )

                    Box(
                        modifier = Modifier
                            .background(
                                Color.Black.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                            )
                            .matchParentSize()
                            .zIndex(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(2.dp))



            Column(
                Modifier
                    .background(
                        shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
                        color = Color.White
                    )
            ) {

                Box(Modifier.size(136.dp)) {

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .zIndex(2f)
                            .padding(start = 8.dp, bottom = 12.dp)
                    ) {
                        Text(
                            text = "1,500점",
                            style = FanwooriTypography.body2.copy(shadow = textShadow),
                            color = Color.White,
                            modifier = Modifier
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "가수명",
                                style = FanwooriTypography.subtitle2.copy(shadow = textShadow),
                                color = Color.White
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.icon_arrow),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                    }


                    Image(
                        painter = painterResource(id = R.drawable.ranking_1st),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopStart)
                            .zIndex(1f)
                    )

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://cdn.entermedia.co.kr/news/photo/202208/29726_57555_1418.jpg")
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        error = painterResource(id = com.google.android.material.R.drawable.mtrl_ic_error),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(136.dp)
                            .clip(RoundedCornerShape(topEnd = 16.dp))
                            .align(Alignment.TopStart),
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                Color.Black.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(topEnd = 16.dp)
                            )
                            .matchParentSize()
                            .zIndex(1f)
                    )

                }
                Spacer(modifier = Modifier.height(2.dp))
                Box(Modifier.size(136.dp)) {

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .zIndex(2f)
                            .padding(start = 8.dp, bottom = 12.dp)
                    ) {
                        Text(
                            text = "1,500점",
                            style = FanwooriTypography.body2.copy(shadow = textShadow),
                            color = Color.White,
                            modifier = Modifier
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "가수명",
                                style = FanwooriTypography.subtitle2.copy(shadow = textShadow),
                                color = Color.White
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.icon_arrow),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                    }


                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "가수명",
                            style = FanwooriTypography.subtitle1.copy(shadow = textShadow),
                            color = Color.White
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.icon_arrow),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }



                    Image(
                        painter = painterResource(id = R.drawable.ranking_1st),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopStart)
                            .zIndex(1f)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://cdn.ziksir.com/news/photo/202212/31765_62346_2612.jpg")
                            .crossfade(true).build(),
                        contentDescription = null,
                        error = painterResource(id = com.google.android.material.R.drawable.mtrl_ic_error),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(136.dp)
                            .clip(RoundedCornerShape(bottomEnd = 16.dp)),
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                Color.Black.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(bottomEnd = 16.dp)
                            )
                            .matchParentSize()
                            .zIndex(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

}


@Composable
@Preview
fun PreviewListItemButtonNotSub() {
    RankItem(
        text = "파쿵야파쿵야파쿵야파쿵야파쿵야파쿵야파쿵야파쿵야파쿵야",
        subText = null,
        imageUrl = "",
        onClick = {},
        item = Sample(),
        rank = 1
    )
}

@Composable
@Preview
fun PreviewRankImageItem() {
    RankImageItem()
}