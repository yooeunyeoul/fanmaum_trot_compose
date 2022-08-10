package com.trotfan.trot.ui.signup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.BackIcon
import com.trotfan.trot.ui.components.button.ContainedButton
import com.trotfan.trot.ui.components.button.OutlineIconButton
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.components.input.SearchStatus
import com.trotfan.trot.ui.components.input.SearchTextField
import com.trotfan.trot.ui.signup.components.HorizontalDialogSelectStar
import com.trotfan.trot.ui.signup.components.ListItemButton
import com.trotfan.trot.ui.signup.viewmodel.StarSelectViewModel
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray600
import com.trotfan.trot.ui.theme.Gray700


@Composable
fun SearchStarScreen(viewModel: StarSelectViewModel = viewModel()) {

    val testData by viewModel.testData.collectAsState()
    val searchState by viewModel.searchStatus.collectAsState()
    val requestComplete by viewModel.requestComplete.collectAsState()

    var selectedItem: Sample? by remember {
        mutableStateOf(null)
    }

    var requestDialog by remember {
        mutableStateOf(false)
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
            contentText = selectedItem?.id.toString()
        ) {
            viewModel.selectStar(selectedItem)
            starSelectDialog = false
        }
    }


    if (requestDialog) {
        HorizontalDialog(
            titleText = "스타 추가 요청",
            positiveText = "요청",
            negativeText = "취소",
            onPositiveWithInputText = { starName ->
                viewModel.requestStar(starName)
            },
            inputPlaceHolderText = "스타 이름 입력",
            onDismiss = {
                requestDialog = false
            })
    }
    if (requestComplete) {
        VerticalDialog(
            contentText = "스타 추가요청이 접수되었어요.\n" +
                    "검토 후 등록까지\n" +
                    "시간이 걸릴 수 있어요.",
            buttonOneText = "확인",
            modifier = Modifier,
            onDismiss = {
                viewModel.dismissCompleteDialog()
            }
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp)
    ) {

        Spacer(modifier = Modifier.padding((8.dp)))
        Row(
            Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(bottom = 8.dp)
        ) {
            BackIcon(
                onCLick = {

                }, modifier = Modifier.align(CenterVertically)
            )
            Spacer(modifier = Modifier.width(4.dp))
            SearchTextField(
                modifier = Modifier,
                onclick = {
                    Log.e("Text ", "Text")
                }, inputText = {
                    if (it.isEmpty()) {
                        viewModel.searchStar(it)
                    } else {
                        selectedItem = null
                    }
                }, isEnabled = true,
                onClickSearch = {
                    viewModel.searchStar(it)

                }, placeHolder = R.string.hint_search_star
            )
        }

        when (searchState) {
            SearchStatus.TrySearch -> {

                Spacer(modifier = Modifier.height(128.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "좋아하는 스타를 검색해보세요!",
                    style = FanwooriTypography.body1,
                    color = Gray600
                )

            }
            SearchStatus.SearchResult -> {
                Box() {
                    LazyColumn(
                        state = rememberLazyListState(),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        contentPadding = PaddingValues(bottom = 72.dp)
                    ) {

                        itemsIndexed(testData) { index, item ->
                            if (index == 0) {
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    text = "검색 결과",
                                    color = Gray600,
                                    style = FanwooriTypography.caption2
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            ListItemButton(
                                text = "팡야팡야",
                                subText = null,
                                imageUrl = "https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg",
                                unCheckedTrailingIcon = R.drawable.icon_heart,
                                checkedTrailingIcon = R.drawable.icon_heartfilled,
                                checked = selectedItem == item,
                                onClick = {
                                    selectedItem = it as Sample
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
            SearchStatus.NoResult -> {
                Spacer(modifier = Modifier.height(189.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "앗! 검색결과가 없어요.",
                    style = FanwooriTypography.subtitle1,
                    color = Gray700
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "이렇게 해보세요.",
                    style = FanwooriTypography.subtitle2,
                    color = Gray600
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                    Row(
                        Modifier
                            .background(shape = CircleShape, color = Gray600)
                            .size(4.dp)
                            .align(CenterVertically),
                        content = {}
                    )
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "검색어를 바르게 입력했는지 확인해주세요.",
                        style = FanwooriTypography.caption1,
                        color = Gray600
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Row(
                        Modifier
                            .background(shape = CircleShape, color = Gray600)
                            .size(4.dp)
                            .align(CenterVertically),
                        content = {}
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "스타 추가 요청을 할 수 있어요.",
                        style = FanwooriTypography.caption1,
                        color = Gray600
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                OutlineIconButton(
                    text = "스타 추가 요청",
                    icon = R.drawable.icon_add,
                    enabled = true,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        requestDialog = true
                    }
                )


            }
        }
    }
}


@Composable
fun PreviewSearchStarScreen() {
    SearchStarScreen()
}