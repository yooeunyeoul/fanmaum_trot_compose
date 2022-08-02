package com.trotfan.trot.ui.signup

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.CustomTopAppBar
import com.trotfan.trot.ui.components.SearchTextField
import com.trotfan.trot.ui.signup.components.ListItemButton
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray600
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Primary100

data class Sample(
    var id: Int = 0,
    var checked: Boolean = false
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectStarScreen() {

    val listState = rememberLazyListState()
    val sampleList = mutableListOf<Sample>()
    for (i in 0..10) {
        sampleList.add(Sample(id = i))
    }

    var selectedItem by remember {
        mutableStateOf(-1)
    }

    Column(
        Modifier
            .fillMaxWidth()

    ) {
        CustomTopAppBar(title = "회원가입")
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                Text(
                    text = "내 스타\n1명을 선택해주세요",
                    color = Gray700,
                    style = FanwooriTypography.h1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "내 스타 선택은 최초 1회만 가능해요.",
                    color = Gray600,
                    style = FanwooriTypography.caption1
                )
                Spacer(modifier = Modifier.height(20.dp))

            }

            stickyHeader {
                SearchTextField(
                    onclick = {
                        Log.e("Text ", "Text")
                    }, inputText = {
                        Log.e("Text ", it)
                    }, isEnabled = false)
            }

            itemsIndexed(sampleList) { index, item ->
                if (index == 0) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "전체 스타",
                        color = Gray600,
                        style = FanwooriTypography.caption2
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
                ListItemButton(
                    text = "팡야팡야",
                    subText = null,
                    imageUrl = "https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg",
                    unCheckedTrailingIcon = R.drawable.icon_heart,
                    checkedTrailingIcon = R.drawable.icon_heartfilled,
                    checked = selectedItem == item.id,
                    onClick = {
                        selectedItem = it
                    },
                    index = item.id
                )
            }
        }

    }

}

@Preview(
    name = "Preview Select Star",
    device = Devices.NEXUS_6,
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewSelectStarScreen() {
    SelectStarScreen()
}