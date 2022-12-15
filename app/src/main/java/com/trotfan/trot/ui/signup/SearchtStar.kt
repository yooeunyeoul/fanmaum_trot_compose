package com.trotfan.trot.ui.signup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.trotfan.trot.R
import com.trotfan.trot.model.FavoriteStar
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.button.BtnIcon
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.button.BtnOutlineSecondaryLeftIcon
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.components.dialog.VerticalDialog
import com.trotfan.trot.ui.components.input.SearchStatus
import com.trotfan.trot.ui.components.input.SearchTextField
import com.trotfan.trot.ui.signup.components.HorizontalDialogSelectStar
import com.trotfan.trot.ui.signup.components.ListItemButton
import com.trotfan.trot.ui.signup.viewmodel.StarSearchViewModel
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray600
import com.trotfan.trot.ui.theme.Gray700
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchStarScreen(
    navController: NavController,
    viewModel: StarSearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val starListState: LazyPagingItems<FavoriteStar>? =
        viewModel.starListState.value?.collectAsLazyPagingItems()
    val searchState by viewModel.searchStatus.collectAsState()
    val requestComplete by viewModel.requestComplete.collectAsState()

    var selectedItem: FavoriteStar? by remember {
        mutableStateOf(null)
    }

    var requestDialog by remember {
        mutableStateOf(false)
    }

    var starSelectDialog by remember {
        mutableStateOf(false)
    }

    var requestStarName by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }

    if (viewModel.onComplete.collectAsState().value) {
        LaunchedEffect(Unit) {
            navController.navigate(Route.SettingNickname.route) {
                popUpTo(Route.SearchStar.route) {
                    inclusive = true
                }
                navController.backQueue.removeIf { it.destination.route == Route.SelectStar.route }
            }
        }
    }



    LaunchedEffect(focusRequester) {
        delay(200)
        focusRequester.requestFocus()
    }


    if (starSelectDialog) {
        HorizontalDialogSelectStar(
            titleText = "내 스타 선택은\n" +
                    "최초 1회만 가능해요!",
            positiveText = "선택",
            negativeText = "취소",
            contentText = selectedItem?.name ?: "",
            onPositive = {
                viewModel.selectStar(selectedItem)
            }
        ) {
            starSelectDialog = false
        }
    }


    if (requestDialog) {
        HorizontalDialog(
            titleText = "스타 추가 요청",
            positiveText = "요청",
            negativeText = "취소",
            onInputText = {
                requestStarName = it
            },
            onPositiveWithInputText = {
                viewModel.requestStar(requestStarName) {
                    requestStarName = ""
                    requestDialog = false

                }
            },
            positiveButtonEnabled = requestStarName.trim().isNotEmpty(),
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
        modifier = modifier
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
            BtnIcon(
                onCLick = {
                    navController.popBackStack()
                }, modifier = Modifier.align(CenterVertically)
            )
            Spacer(modifier = Modifier.width(4.dp))
            SearchTextField(
                modifier = Modifier
                    .focusRequester(focusRequester),
                inputText = {
                    if (it.isEmpty()) {
                        viewModel.searchStar(it)
                    } else {
                        selectedItem = null
                    }
                }, isEnabled = true,
                onClickSearch = {
                    viewModel.searchStar(it)

                },
                placeHolder = R.string.hint_search_star
            )
        }

        when (starListState?.loadState?.refresh) {
            is LoadState.Error -> {
                viewModel.changeSearchState(SearchStatus.NoResult)
            }
            is LoadState.Loading -> {
                viewModel.changeSearchState(SearchStatus.Loading)
            }
            is LoadState.NotLoading -> {
                viewModel.changeSearchState(SearchStatus.SearchResult)
            }
            else -> {}
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
                Box(Modifier.fillMaxHeight()) {
                    LazyColumn(
                        state = rememberLazyListState(),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        contentPadding = PaddingValues(bottom = 72.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "검색 결과",
                                color = Gray600,
                                style = FanwooriTypography.caption2
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        starListState?.let {
                            items(it) { item ->
                                ListItemButton(
                                    text = item?.name?:"",
                                    subText = item?.group?.name,
                                    imageUrl = item?.image,
                                    unCheckedTrailingIcon = R.drawable.icon_heart,
                                    checkedTrailingIcon = R.drawable.icon_heartfilled,
                                    checked = selectedItem == item,
                                    onClick = {
                                        val clickedItem = it as FavoriteStar
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
                        BtnFilledLPrimary(
                            text = "다음",
                            enabled = selectedItem != null,
                            modifier = Modifier.align(Alignment.BottomCenter)
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
                BtnOutlineSecondaryLeftIcon(
                    text = "스타 추가 요청",
                    icon = R.drawable.icon_add,
                    enabled = true,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        requestDialog = true
                    }
                )


            }
            SearchStatus.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }

            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PreviewSearchStarScreen() {
    SearchStarScreen(navController = rememberNavController())
}