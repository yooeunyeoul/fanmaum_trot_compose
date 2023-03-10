package com.trotfan.trot.ui.components.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineLGray
import com.trotfan.trot.ui.utils.clickable

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HorizontalImageDialog(
    modifier: Modifier = Modifier,
    onPositive: () -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier.widthIn(0.dp, 500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.charge_missionpopup)
                    .crossfade(true).build(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDismiss()
                    }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp)
            ) {
                BtnOutlineLGray(text = "닫기", modifier = Modifier.weight(1f)) {
                    onDismiss()
                }
                Spacer(modifier = Modifier.width(8.dp))
                BtnFilledLPrimary(text = "지금 받기", modifier = Modifier.weight(1f)) {
                    onPositive()
                }
            }
        }
    }
}