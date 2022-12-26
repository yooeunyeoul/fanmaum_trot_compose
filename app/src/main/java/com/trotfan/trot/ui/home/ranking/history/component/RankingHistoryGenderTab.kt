package com.trotfan.trot.ui.home.ranking.history.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trotfan.trot.ui.home.ranking.history.viewmodel.RankingHistoryViewModel
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray200
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Gray800
import com.trotfan.trot.ui.utils.clickable

@Composable
fun RankingHistoryGenderTab(
    viewModel: RankingHistoryViewModel = viewModel(),
    onGenderClick: (Int) -> Unit
) {
    val favoriteStarGender by viewModel.userInfoManager.favoriteStarGenderFlow.collectAsState(
        initial = Gender.MEN
    )
    var tabIndex by remember { mutableStateOf(if (favoriteStarGender == Gender.MEN) 0 else 1) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StarGenderTab("남자스타", tabIndex == 0) {
            tabIndex = 0
            onGenderClick(tabIndex)
        }

        Spacer(modifier = Modifier.width(6.dp))

        StarGenderTab("여자스타", tabIndex == 1) {
            tabIndex = 1
            onGenderClick(tabIndex)
        }
    }
}


@Composable
fun StarGenderTab(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = text,
        color = if (isSelected) Color.White else Gray700,
        style = FanwooriTypography.button1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .width(104.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Gray800 else Color.White)
            .border(
                border = if (isSelected) BorderStroke(0.dp, Gray800) else BorderStroke(
                    1.dp,
                    Gray200
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .wrapContentHeight(Alignment.CenterVertically)

    )
}