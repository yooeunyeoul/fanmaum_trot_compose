package com.trotfan.trot.ui.home.ranking.history.component.daily

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.home.ranking.history.viewmodel.RankingHistoryViewModel
import com.trotfan.trot.ui.home.ranking.history.component.monthly.NumberPickerComponent
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray100
import com.trotfan.trot.ui.theme.Gray900
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DailyCalenderPicker(
    modalBottomSheetState: ModalBottomSheetState,
    onConfirmClick: (String) -> Unit,
    rankingHistoryViewModel: RankingHistoryViewModel = viewModel()
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val year = rankingHistoryViewModel.dailyYear.collectAsState()
    val month = rankingHistoryViewModel.dailyMonth.collectAsState()
    val day = rankingHistoryViewModel.dailyDay.collectAsState()
    val startYear = rankingHistoryViewModel.startYear.collectAsState()

    var tempYear by remember {
        mutableStateOf(year.value)
    }
    var tempMonth by remember {
        mutableStateOf(month.value)
    }
    var tempDay by remember {
        mutableStateOf(day.value)
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
            Spacer(modifier = Modifier.width(20.dp))
            PickerChip(text = "일")
        }
        Spacer(modifier = Modifier.height(40.dp))

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            NumberPickerComponent(
                context = context,
                year.value,
                startYear.value,
                year.value,
                onValueChangeListener = { p0, _, _ ->
                    tempYear = p0.value
                })
            Spacer(modifier = Modifier.width(20.dp))
            NumberPickerComponent(
                context = context,
                month.value,
                1,
                12,
                onValueChangeListener = { p0, _, _ ->
                    tempMonth = p0.value
                })
            Spacer(modifier = Modifier.width(20.dp))
            NumberPickerComponent(
                context = context,
                day.value,
                1,
                31,
                onValueChangeListener = { p0, _, _ ->
                    tempDay = p0.value
                })
        }

        Spacer(modifier = Modifier.height(24.dp))
        BtnFilledLPrimary(
            text = "확인",
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
        ) {
            val simpleDate = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val startedAt = rankingHistoryViewModel.datePickerRange.value?.started_at
            val endedAt = rankingHistoryViewModel.datePickerRange.value?.ended_at
            val pickDate = simpleDate.parse("$tempYear-$tempMonth-$tempDay")
            val sDate = simpleDate.parse(startedAt ?: "2022-11-11")
            val eDate = simpleDate.parse(endedAt ?: "2022-12-13")
            pickDate?.let {
                if (it >= sDate && it <= eDate) {
                    coroutineScope.launch {
                        rankingHistoryViewModel.dailyYear.emit(tempYear)
                        rankingHistoryViewModel.dailyMonth.emit(tempMonth)
                        rankingHistoryViewModel.dailyDay.emit(tempDay)
                        modalBottomSheetState.hide()
                        onConfirmClick("$tempYear/$tempMonth/$tempDay")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
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
fun DailyCalenderPickerPreview() {
    FanwooriTheme {
        DailyCalenderPicker(
            rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true
            ),
            onConfirmClick = {

            }
        )
    }
}