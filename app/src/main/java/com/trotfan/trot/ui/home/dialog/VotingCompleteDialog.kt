package com.trotfan.trot.ui.home.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineLGray
import com.trotfan.trot.ui.components.chip.ChipFilledLSecondary
import com.trotfan.trot.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VotingCompleteDialog(
    modifier: Modifier = Modifier,
    starName: String,
    quantity: Int,
    onPositive: () -> Unit = { },
    onDismiss: () -> Unit
) {


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
                modifier = Modifier.padding(top = 58.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ChipFilledLSecondary(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Secondary50),
                    text = starName ?: "",
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
                    text = "${quantity} 투표 완료",
                    style = FanwooriTypography.subtitle1,
                    color = Primary700,
                    modifier = Modifier,
                )
            }

            Text(
                text = "친구에게 공유하고\n" +
                        "2,500투표권 더 받아가세요!",
                style = FanwooriTypography.body1,
                color = Gray700,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(CenterHorizontally),
            )

            Row(Modifier.padding(top = 24.dp, bottom = 24.dp)) {
                Spacer(modifier = Modifier.width(24.dp))
                BtnOutlineLGray(text = "완료", modifier = Modifier.weight(1f)) {
                    onDismiss()
                }
                Spacer(modifier = Modifier.width(8.dp))
                BtnFilledLPrimary(text = "공유하기", modifier = Modifier.weight(1f)) {
                    onPositive()
                }
                Spacer(modifier = Modifier.width(24.dp))
            }
        }


    }
}
