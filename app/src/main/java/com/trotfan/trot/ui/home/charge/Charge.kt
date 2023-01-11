package com.trotfan.trot.ui.home.charge

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.billingclient.api.BillingClient
import com.trotfan.trot.ConsumeState
import com.trotfan.trot.InAppProduct
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.ui.components.list.StoreItem
import com.trotfan.trot.ui.components.navigation.AppbarL
import com.trotfan.trot.ui.home.BottomNavHeight
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.charge.component.MyVote
import com.trotfan.trot.ui.home.charge.viewmodel.ChargeHomeViewModel
import com.trotfan.trot.ui.theme.FanwooriTypography

@Composable
fun ChargeHome(
    onItemClick: (Long) -> Unit,
    navController: NavController,
    onNavigateClick: (HomeSections) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChargeHomeViewModel = hiltViewModel(),
    purchaseHelper: PurchaseHelper,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val tickets by viewModel.tickets.collectAsState()
    val purchaseStatusText by purchaseHelper.statusText.collectAsState()
    val consumeState by purchaseHelper.consumeState.collectAsState()

    LaunchedEffect(key1 = consumeState, block = {
        if (consumeState == ConsumeState.Success) {
            viewModel.getVoteTickets()
            purchaseHelper.changeConsumeStatus()
            Log.e("이거 호출 되긴 되냐 Success", "이거 호출 되긴 되냐 Success")
        }
    })

    BackHandler {
        onNavigateClick.invoke(HomeSections.Vote)
    }
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavHeight)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            AppbarL(title = "충전")
            LazyColumn(
                Modifier.fillMaxWidth(), state = lazyListState ?: rememberLazyListState(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    Text(
                        text = purchaseStatusText,
                        style = FanwooriTypography.h1,
                        color = Color.Black
                    )
                }
                item {
                    MyVote(tickets = tickets)
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "전체상품",
                        style = FanwooriTypography.body3,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(InAppProduct.values().size) { index ->

                    StoreItem(
                        onItemClick = { product ->
                            purchaseHelper.makePurchaseInApp(
                                billingType = BillingClient.ProductType.INAPP,
                                inAppProduct = product
                            )
                        },
                        product = InAppProduct.values()[index]
                    )
                }
            }

        }

    }
}