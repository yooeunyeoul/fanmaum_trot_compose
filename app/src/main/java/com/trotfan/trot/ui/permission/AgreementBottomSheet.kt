package com.trotfan.trot.ui.permission

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.theme.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AgreementBottomSheet(
    modalBottomSheetState: ModalBottomSheetState,
    onConfirmClick: (String) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            painter = painterResource(id = R.drawable.icon_close),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Gray50)
        ) {
            Text(
                text = "모두 확인, 동의합니다.",
                style = FanwooriTypography.body3,
                color = Gray800,
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 16.dp)
                    .weight(1f)
            )

            Checkbox(
                checked = false, onCheckedChange = {

                }, modifier = Modifier.align(CenterVertically), colors = CheckboxDefaults.colors(
                    uncheckedColor = Gray300,
                    checkedColor = Primary500
                )
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MonthlyCalenderPickerPreview() {
    FanwooriTheme {
        AgreementBottomSheet(
            modalBottomSheetState =
            rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true
            ),
            onConfirmClick = {}
        )
    }
}