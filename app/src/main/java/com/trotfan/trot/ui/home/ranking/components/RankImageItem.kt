package com.trotfan.trot.ui.home.ranking.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.trotfan.trot.model.StarRanking
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.utils.NumberComma
import com.trotfan.trot.ui.utils.clickable


val textShadow =
    Shadow(color = Color.Black.copy(alpha = 0.30f), offset = Offset(2f, 2f), blurRadius = 4f)

@Composable
fun RankImageItem(top3List: List<StarRanking>, onClick: (Any?) -> Unit) {
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
                    .clickable {
                        onClick.invoke(top3List[0])
                    }
            ) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Image(
                        painter = painterResource(id = R.drawable.ranking_1st),
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
                            text = "${NumberComma.decimalFormat.format(top3List[0].score)}점",
                            style = FanwooriTypography.body3.copy(shadow = textShadow),
                            color = Color.White,
                            modifier = Modifier
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = top3List[0].name ?: "",
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
                            .data(top3List[0].image)
                            .crossfade(true).build(),
                        contentDescription = null,
                        error = painterResource(id = com.google.android.material.R.drawable.mtrl_ic_error),
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxHeight()
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

                Box(
                    Modifier
                        .size(136.dp)
                        .clickable {
                            onClick.invoke(top3List[1])
                        }) {

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .zIndex(2f)
                            .padding(start = 8.dp, bottom = 12.dp)
                    ) {
                        Text(
                            text = "${NumberComma.decimalFormat.format(top3List[1].score)}점",
                            style = FanwooriTypography.body2.copy(shadow = textShadow),
                            color = Color.White,
                            modifier = Modifier
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = top3List[1].name ?: "",
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
                        painter = painterResource(id = R.drawable.ranking_2nd),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopStart)
                            .zIndex(1f)
                    )

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(top3List[1].image)
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
                Box(
                    Modifier
                        .size(136.dp)
                        .clickable {
                            onClick.invoke(top3List[2])
                        }) {

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .zIndex(2f)
                            .padding(start = 8.dp, bottom = 12.dp)
                    ) {
                        Text(
                            text = "${NumberComma.decimalFormat.format(top3List[2].score)}점",
                            style = FanwooriTypography.body2.copy(shadow = textShadow),
                            color = Color.White,
                            modifier = Modifier
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = top3List[2].name ?: "",
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
                            text = top3List[2].name ?: "",
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
                        painter = painterResource(id = R.drawable.ranking_3rd),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopStart)
                            .zIndex(1f)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(top3List[2].image)
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
fun PreviewRankImageItem() {
    RankImageItem(listOf(),{})
}