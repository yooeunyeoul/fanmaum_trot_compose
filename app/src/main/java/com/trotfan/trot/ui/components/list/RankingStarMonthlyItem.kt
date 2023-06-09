package com.trotfan.trot.ui.components.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.R
import com.trotfan.trot.model.StarRanking
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.NumberComma
import com.trotfan.trot.ui.utils.clickable

@Composable
fun RankingStarMonthlyItem(star: StarRanking, onItemClick: () -> Unit) {
    if (star.rank in 1..3) {
        RankingMonthlyRankerItem(star = star, onItemClick = onItemClick)
    } else {
        RankingMonthlyDefaultItem(star = star, onItemClick = onItemClick)
    }
}

@Composable
fun RankingMonthlyRankerItem(star: StarRanking, onItemClick: () -> Unit) {
    val painter: Painter = when (star.rank) {
        1 -> painterResource(id = R.drawable.ranking_monthlytop1)
        2 -> painterResource(id = R.drawable.ranking_monthlytop2)
        else -> painterResource(id = R.drawable.ranking_monthlytop3)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
    ) {
        Spacer(modifier = Modifier.width(24.dp))

        Image(
            painter = painter,
            modifier = Modifier
                .size(40.dp)
                .fillMaxHeight()
                .align(CenterVertically),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .size(56.dp)
                .border(1.dp, Gray100, CircleShape)
                .clip(CircleShape)
                .align(CenterVertically)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(star.image)
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .align(Center)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f)
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = star.name,
                color = Gray900,
                style = FanwooriTypography.subtitle1
            )

            Text(
                text = "${NumberComma.decimalFormat.format(star.score)} 점",
                color = Gray700,
                style = FanwooriTypography.body5
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.icon_arrow),
            contentDescription = null,
            tint = Gray600,
            modifier = Modifier
                .size(16.dp)
                .align(CenterVertically)
        )


        Spacer(modifier = Modifier.width(24.dp))
    }
}

@Composable
fun RankingMonthlyDefaultItem(star: StarRanking, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
            .padding(top = 8.dp, bottom = 8.dp)
    ) {

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = if (star.rank == 0) "-" else star.rank.toString(),
            style = FanwooriTypography.subtitle3,
            color = Gray700,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .size(24.dp)
                .align(CenterVertically)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .size(48.dp)
                .border(1.dp, Gray100, CircleShape)
                .clip(CircleShape)
                .align(CenterVertically)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(star.image)
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .align(Center)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = star.name,
                color = Gray900,
                style = FanwooriTypography.subtitle1
            )

            Text(
                text = "${NumberComma.decimalFormat.format(star.score)} 점",
                color = Gray700,
                style = FanwooriTypography.body5
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.icon_arrow),
            contentDescription = null,
            tint = Gray600,
            modifier = Modifier
                .size(16.dp)
                .align(CenterVertically)
        )


        Spacer(modifier = Modifier.width(24.dp))
    }
}

@Preview
@Composable
fun RankingStarItemPreview() {
    FanwooriTheme {
        RankingMonthlyRankerItem(StarRanking(0, 1, 1, "최영화", "", 1), onItemClick = {

        })
    }
}