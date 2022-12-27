package com.trotfan.trot.ui.home.ranking.history.component.daily

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.model.StarRanking
import com.trotfan.trot.model.StarRankingDaily
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.NumberComma

@Composable
fun RankingStarDailyItem(star: StarRankingDaily) {
    if (star.rank < 4) {
        RankingDailyRankerItem(star = star)
    } else {
        RankingDailyDefaultItem(star = star)
    }
}

@Composable
fun RankingDailyRankerItem(star: StarRankingDaily?) {
    val color: Color = when (star?.rank) {
        1 -> Primary500
        else -> Primary800
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = star?.rank.toString(),
            color = color,
            style = FanwooriTypography.h2,
            modifier = Modifier
                .align(CenterVertically)
                .size(24.dp),
            textAlign = TextAlign.Center
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
                    .data(star?.image)
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
                text = star?.name.toString(),
                color = Gray900,
                style = FanwooriTypography.subtitle1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "${NumberComma.decimalFormat.format(star?.score)} 점",
                color = Gray700,
                style = FanwooriTypography.body5
            )
        }
    }
}

@Composable
fun RankingDailyDefaultItem(star: StarRankingDaily?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = star?.rank.toString(),
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
                    .data(star?.image)
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
                text = star?.name.toString(),
                color = Gray900,
                style = FanwooriTypography.subtitle1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "${NumberComma.decimalFormat.format(star?.score)} 점",
                color = Gray700,
                style = FanwooriTypography.body5
            )
        }
    }
}

@Preview
@Composable
fun RankingStarItemPreview() {
    FanwooriTheme {
        RankingDailyRankerItem(StarRankingDaily("최영화", "", 50, 1, "0", 11))
    }
}

@Preview
@Composable
fun RankingStarDailyPreview() {
    FanwooriTheme {
        RankingStarDailyItem(StarRankingDaily("최영화", "", 50, 1, "0", 11))
    }
}