package com.trotfan.trot.ui.home.ranking.history.component.daily

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.NumberComma

@Composable
fun RankingStarDailyItem(star: VoteMainStar) {
    if (star.rank!! < 4) {
        RankingDailyRankerItem(star = star)
    } else {
        RankingDailyDefaultItem(star = star)
    }
}

@Composable
fun RankingDailyRankerItem(star: VoteMainStar?) {
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
            modifier = Modifier.align(CenterVertically).size(24.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .size(56.dp)
                .background(Color.Yellow, CircleShape)
                .border(1.dp, Gray100, CircleShape)
                .align(CenterVertically)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kakao_symbol),
                contentDescription = null,
                modifier = Modifier.align(Center)
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
                text = "${NumberComma.decimalFormat.format(star?.votes)} 점",
                color = Gray700,
                style = FanwooriTypography.body5
            )
        }
    }
}

@Composable
fun RankingDailyDefaultItem(star: VoteMainStar?) {
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
                .background(Color.Yellow, CircleShape)
                .border(1.dp, Gray100, CircleShape)
                .align(CenterVertically)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kakao_symbol),
                contentDescription = null,
                modifier = Modifier.align(Center)
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
                text = "${NumberComma.decimalFormat.format(star?.votes)} 점",
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
        RankingDailyRankerItem(VoteMainStar(0, null, "최영화", 1, 3000))
    }
}

@Preview
@Composable
fun RankingStarDailyPreview() {
    FanwooriTheme {
        RankingStarDailyItem(VoteMainStar(0, null, "최영화", 4, 3000))
    }
}