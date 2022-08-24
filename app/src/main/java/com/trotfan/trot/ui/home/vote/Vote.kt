package com.trotfan.trot.ui.home.vote

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.components.dialog.VerticalDialog

@Composable
fun VoteHome(
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier

) {
    var updateStatus by rememberSaveable { mutableStateOf(true) }
    var autoVoteStatus by rememberSaveable { mutableStateOf(true) }
    var feverStatus by rememberSaveable { mutableStateOf(true) }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {


        if (autoVoteStatus) {
            VerticalDialog(
                contentText = "팬우리에 매일 출석만 해도,\n" +
                        "내 스타에게 자동으로 투표가 돼요!",
                buttonOneText = "출석했어요!",
                onDismiss = {
                    autoVoteStatus = false
                }
            )
        }

        if (updateStatus) {
            HorizontalDialog(
                titleText = "새로운 버전이\n" + "업데이트되었어요!",
                contentText = "업데이트 내용\n" +
                        "업데이트 내용\n" +
                        "\n" +
                        "지금 바로 업데이트하고\n" +
                        "즐겨보세요 \uD83D\uDE09",
                positiveText = "다음에",
                negativeText = "업데이트",
                onPositive = {
                    updateStatus = false
                },
                onDismiss = {
                    updateStatus = false
                }
            )
        }


        Text(text = "This is VoteHome", fontWeight = FontWeight.Bold)
    }
}