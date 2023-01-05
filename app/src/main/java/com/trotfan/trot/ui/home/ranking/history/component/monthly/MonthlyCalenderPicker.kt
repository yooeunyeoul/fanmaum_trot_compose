package com.trotfan.trot.ui.home.ranking.history.component.monthly

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.home.ranking.history.viewmodel.RankingHistoryViewModel
import com.trotfan.trot.ui.theme.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MonthlyCalenderPicker(
    modalBottomSheetState: ModalBottomSheetState,
    onConfirmClick: (String) -> Unit,
    rankingHistoryViewModel: RankingHistoryViewModel = viewModel()
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val year = rankingHistoryViewModel.monthlyYear.collectAsState()
    val month = rankingHistoryViewModel.monthlyMonth.collectAsState()
    val startYear = rankingHistoryViewModel.startYear.collectAsState()
    var tempYear by remember {
        mutableStateOf(year.value)
    }
    var tempMonth by remember {
        mutableStateOf(month.value)
    }

    var errState by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            PickerChip(text = "년")
            Spacer(modifier = Modifier.width(20.dp))
            PickerChip(text = "월")
        }
        Spacer(modifier = Modifier.height(40.dp))

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            NumberPickerComponent(
                context = context,
                year.value,
                startYear.value,
                year.value,
            ) { p0, _, _ ->
                tempYear = p0.value
            }
            Spacer(modifier = Modifier.width(20.dp))
            NumberPickerComponent(context = context, month.value, 1, 12) { p0, _, _ ->
                tempMonth = p0.value
            }
        }

        if (errState) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "내역이 없는 날짜입니다.\n다른 날짜를 선택해주세요.",
                textAlign = TextAlign.Center,
                color = SemanticNegative500,
                style = FanwooriTypography.body2,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        BtnFilledLPrimary(
            text = "확인",
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp),
            onClick = {
                val simpleDate = SimpleDateFormat("yyyy-MM", Locale.KOREA)
                val pickDate = simpleDate.parse("$tempYear-$tempMonth")
                val startedAt =
                    rankingHistoryViewModel.datePickerRange.value?.started_at?.split("-")
                val endedAt =
                    rankingHistoryViewModel.datePickerRange.value?.ended_at?.split("-")
                val sDate = simpleDate.parse("${startedAt?.get(0)}-${startedAt?.get(1)}")
                val eDate = simpleDate.parse("${endedAt?.get(0)}-${endedAt?.get(1)}")
                pickDate?.let {
                    if (it >= sDate && it <= eDate) {
                        coroutineScope.launch {
                            rankingHistoryViewModel.monthlyYear.emit(tempYear)
                            rankingHistoryViewModel.monthlyMonth.emit(tempMonth)
                            modalBottomSheetState.hide()
                            onConfirmClick("$tempYear/$tempMonth")
                            errState = false
                        }
                    } else {
                        errState = true
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NumberPickerComponent(
    context: Context,
    start: Int,
    min: Int,
    max: Int,
    onValueChangeListener: NumberPicker.OnValueChangeListener
) {
    val numberPicker = NumberPicker(context).apply {
        maxValue = max
        minValue = min
        value = start
        textSize = 60f
        textColor = Gray900.toArgb()
        selectionDividerHeight = 0
        setOnValueChangedListener(onValueChangeListener)
    }
    AndroidView(factory = {
        numberPicker
    }, modifier = Modifier.width(70.dp)) {

    }
}

@Composable
fun PickerChip(text: String) {
    Box(
        contentAlignment = Center,
        modifier = Modifier
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(Gray100)
            .width(70.dp)
            .height(28.dp)
    ) {
        Text(
            text = text,
            color = Gray900,
            style = FanwooriTypography.caption1,
            textAlign = TextAlign.Center
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MonthlyCalenderPickerPreview() {
    FanwooriTheme {
        MonthlyCalenderPicker(
            modalBottomSheetState =
            rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true
            ),
            onConfirmClick = {}
        )
    }
}