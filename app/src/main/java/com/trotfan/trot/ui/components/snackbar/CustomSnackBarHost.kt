package com.trotfan.trot.ui.components.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Primary400
import com.trotfan.trot.ui.utils.clickable

val CustomSnackBarHost: @Composable (SnackbarHostState) -> Unit =
    { snackBarHostState: SnackbarHostState ->
        SnackbarHost(
            hostState = snackBarHostState
        ) { data ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .alpha(0.8f)
                    .background(Color(0XFF22282A))
                    .clickable {
                        data.performAction()
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.message,
                        style = FanwooriTypography.body3,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .weight(1f)
                    )

                    Text(
                        text = "더보기",
                        style = FanwooriTypography.button1,
                        color = Primary400,
                        modifier = Modifier
                            .padding(end = 16.dp)
                    )
                }
            }
        }
    }