package com.trotfan.trot.ui.signup

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
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
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.components.input.SearchTextField
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.signup.components.HorizontalDialogSelectStar
import com.trotfan.trot.ui.signup.components.ListItemButton
import com.trotfan.trot.ui.signup.viewmodel.StarSelectViewModel
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable

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
    val starListState: LazyPagingItems<FavoriteStar> =
        viewModel.starListState.collectAsLazyPagingItems()

    var selectedItem: FavoriteStar? by remember {
        mutableStateOf(null)
    }

    var starSelectDialog by remember {
        mutableStateOf(false)
    }

    if (viewModel.onComplete.collectAsState().value) {
        LaunchedEffect(Unit) {
            navController.navigate(Route.SettingNickname.route) {
                popUpTo(Route.SelectStar.route) {
                    inclusive = true
                }
            }
        }
    }

    if (starSelectDialog) {
        HorizontalDialogSelectStar(
            content = {
                Text(
                    text = "내 스타 선택은",
                    color = Gray700,
                    style = FanwooriTypography.subtitle1
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "최초 1회",
                            style = FanwooriTypography.h2,
                            color = Primary500
                        )

                        Box(
                            modifier = Modifier
                                .background(Primary500)
                                .width(62.dp)
                                .height(1.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "만 가능해요!",
                        color = Gray700,
                        style = FanwooriTypography.subtitle1
                    )
                }
            },
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
                contentPadding = PaddingValues(bottom = 96.dp)
            ) {
                item {

                    Text(
                        text = "내 스타\n1명을 선택해주세요",
                        color = Gray900,
                        style = FanwooriTypography.h1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        buildAnnotatedString {
                            append("내 스타 선택은")
                            withStyle(
                                style = SpanStyle(
                                    fontStyle = FanwooriTypography.body2.fontStyle,
                                    fontWeight = FanwooriTypography.body2.fontWeight,
                                    color = Primary500,
                                    fontSize = FanwooriTypography.body2.fontSize
                                )
                            ) {
                                append(" 최초 1회")
                            }
                            append("만 가능해요.")
                        }, maxLines = 1, style = FanwooriTypography.caption1,
                        color = Gray700
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                }

                stickyHeader {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Route.SearchStar.route)
                            }
                            .background(color = Color.White)
                    ) {
                        SearchTextField(
                            inputText = {
                                Log.e("Text ", it)
                            }, isEnabled = false, placeHolder = R.string.hint_search_star,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                    }
                }
                item {
                    Text(
                        text = "전체 스타",
                        color = Gray600,
                        style = FanwooriTypography.caption2,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                }

                when (starListState.loadState.refresh) {
                    is LoadState.NotLoading -> {
                        items(items = starListState) { item ->
                            ListItemButton(
                                text = item?.name ?: "",
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
                    is LoadState.Loading -> {
                        item {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                CircularProgressIndicator()
                            }

                        }


                    }
                    is LoadState.Error -> {
                        item {
                            Column(verticalArrangement = Arrangement.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    else -> {
                        item {
                            Column(verticalArrangement = Arrangement.Center) {
                                CircularProgressIndicator()
                            }
                        }
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
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    starSelectDialog = true
                }
            }

        }

    }


}

@Preview(
    name = "Preview Select com.trotfan.trot.model.com.trotfan.trot.model.Star",
    device = Devices.NEXUS_6,
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewSelectStarScreen() {
    SelectStarScreen(navController = rememberNavController())
}

