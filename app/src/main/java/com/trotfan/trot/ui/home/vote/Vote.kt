package com.trotfan.trot.ui.home.vote

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.home.dialog.FeverTimeDialog
import com.trotfan.trot.ui.home.dialog.RollingDialog
import com.trotfan.trot.ui.home.vote.guide.VoteGuide
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VoteHome(
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    votingBottomSheetState: ModalBottomSheetState
) {
    var updateStatus by rememberSaveable { mutableStateOf(false) }
    var autoVoteStatus by rememberSaveable { mutableStateOf(true) }
    var feverStatus by rememberSaveable { mutableStateOf(false) }
    var rollingStatus by rememberSaveable { mutableStateOf(false) }
    var voteGuideStatus by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
//
//
//        if (autoVoteStatus) {
//            VerticalDialog(
//                contentText = "팬우리에 매일 출석만 해도,\n" +
//                        "내 스타에게 자동으로 투표가 돼요!",
//                buttonOneText = "출석했어요!",
//                onDismiss = {
//                    autoVoteStatus = false
//                    coroutineScope.launch {
//                        votingBottomSheetState.show()
//                    }
//                }
//            )
//        }
//
//        if (feverStatus) {
//            FeverTimeDialog(
//                onDismiss = {
//                    feverStatus = false
//                }
//            )
//        }
//
//        if (voteGuideStatus) {
//            VoteGuide(
//                onDismiss = {
//                    voteGuideStatus = false
//                }
//            )
//        }

        Text(text = "This is VoteHome", fontWeight = FontWeight.Bold)
    }
}