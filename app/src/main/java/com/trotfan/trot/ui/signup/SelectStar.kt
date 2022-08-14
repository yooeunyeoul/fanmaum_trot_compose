package com.trotfan.trot.ui.signup

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.trotfan.trot.R
import com.trotfan.trot.model.Person
import com.trotfan.trot.ui.components.button.ContainedButton
import com.trotfan.trot.ui.components.input.SearchTextField
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.signup.components.HorizontalDialogSelectStar
import com.trotfan.trot.ui.signup.components.ListItemButton
import com.trotfan.trot.ui.signup.viewmodel.StarSelectViewModel
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray600
import com.trotfan.trot.ui.theme.Gray700

data class Sample(
    var id: Int = 0,
    var checked: Boolean = false
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectStarScreen(
    navController: NavController,
    viewModel: StarSelectViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val listState = rememberLazyListState()
    val starListState: LazyPagingItems<Person>? =
        viewModel.starListState.value?.collectAsLazyPagingItems()

    var selectedItem: Person? by remember {
        mutableStateOf(null)
    }

    var starSelectDialog by remember {
        mutableStateOf(false)
    }

    if (starSelectDialog) {
        HorizontalDialogSelectStar(
            titleText = "내 스타 선택은\n" +
                    "최초 1회만 가능해요!",
            positiveText = "선택",
            negativeText = "취소",
            contentText = selectedItem?.name ?: "",
            onPositive = {
                navController.navigate(SignUpSections.SettingNickName.route) {
                    popUpTo(SignUpSections.SelectStar.route) {
                        inclusive = true
                    }
                }
            }
        ) {
            viewModel.selectStar(selectedItem)
            starSelectDialog = false
        }
    }



    Column(modifier = modifier.fillMaxSize()) {
        CustomTopAppBar(title = "회원가입")
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            Modifier
                .fillMaxSize()

        ) {

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(bottom = 72.dp)
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
                            navController.navigate(SignUpSections.SearchStar.route)
                        }, inputText = {
                            Log.e("Text ", it)
                        }, isEnabled = false, placeHolder = R.string.hint_search_star,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                itemsIndexed(
                    starListState?.itemSnapshotList?.items ?: emptyList()
                ) { index, item ->
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
                        text = item.name,
                        subText = item.group,
                        imageUrl = item.image,
                        unCheckedTrailingIcon = R.drawable.icon_heart,
                        checkedTrailingIcon = R.drawable.icon_heartfilled,
                        checked = selectedItem == item,
                        onClick = {
                            val clickedItem = it as Person
                            selectedItem = if (clickedItem == selectedItem) {
                                null
                            } else {
                                clickedItem
                            }
                        },
                        item = item
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.White
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .height(96.dp)
                    .align(Alignment.BottomCenter)
            ) {
                ContainedButton(
                    text = "다음",
                    enabled = selectedItem != null,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    starSelectDialog = true
                }
            }

        }

    }


}

@Preview(
    name = "Preview Select com.trotfan.trot.model.Star",
    device = Devices.NEXUS_6,
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewSelectStarScreen() {
    SelectStarScreen(navController = rememberNavController())
}

