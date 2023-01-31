package com.trotfan.trot.ui.home.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineLGray
import com.trotfan.trot.ui.components.chip.ChipFilledLSecondary
import com.trotfan.trot.ui.home.viewmodel.HomeViewModel
import com.trotfan.trot.ui.theme.*

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

        Box(Modifier.height(399.dp)) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.popup_daily)
                    .crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(144.dp)
                    .zIndex(1f)
                    .align(Alignment.TopCenter)
            )

            Column(
                modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .height(303.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Row(
                    modifier = Modifier.padding(top = 58.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ChipFilledLSecondary(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Secondary50),
                        text = "",
                        textColor = Secondary600
                    )

                    Text(
                        text = "님에게",
                        style = FanwooriTypography.body1,
                        color = Gray700,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_vote_iconcolored),
                        contentDescription = null
                    )

                    Text(
                        text = "${mainPopups?.auto_vote?.quantity} 투표 완료",
                        style = FanwooriTypography.subtitle1,
                        color = Primary700,
                        modifier = Modifier,
                    )
                }

                Text(
                    text = "팬마음에 매일 출석만 해도,\n" +
                            "내 스타에게 자동으로 투표가 돼요!",
                    style = FanwooriTypography.body1,
                    color = Gray700,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(CenterHorizontally),
                )

                BtnFilledLPrimary(
                    text = "출석했어요!",
                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 24.dp,
                        bottom = 24.dp
                    )
                ) {
                    onDismiss()
                }
            }

        }


    }
}