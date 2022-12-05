package com.trotfan.trot.ui.home.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.ui.components.button.Outline2Button
import com.trotfan.trot.ui.components.chip.Chip
import com.trotfan.trot.ui.home.viewmodel.HomeViewModel
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray500
import com.trotfan.trot.ui.theme.Secondary50
import com.trotfan.trot.ui.theme.Secondary600

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AutoVotingDialog(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val mainPopups by viewModel.mainPopups.collectAsState()

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                modifier = Modifier.padding(top = 77.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Chip(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Secondary50),
                    text = mainPopups?.autoVote?.star?.name ?: "",
                    textColor = Secondary600
                )

                Text(
                    text = "님에게",
                    style = FanwooriTypography.body1,
                    color = Gray500,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            Text(
                text = "${mainPopups?.autoVote?.quantity}장 투표 완료 \uD83C\uDF89",
                style = FanwooriTypography.subtitle1,
                color = Secondary600,
                modifier = Modifier
                    .padding(top = 9.dp)
                    .align(CenterHorizontally),
            )

            Text(
                text = "팬마음에 매일 출석만 해도,\n" +
                        "내 스타에게 자동으로 투표가 돼요!",
                style = FanwooriTypography.body1,
                color = Gray500,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(CenterHorizontally),
            )

            Outline2Button(
                text = "출석했어요!",
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 14.dp, bottom = 24.dp)
            ) {
                onDismiss()
            }
        }
    }
}