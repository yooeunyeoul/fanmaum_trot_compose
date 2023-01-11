package com.trotfan.trot.ui.home.charge

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.billingclient.api.BillingClient
import com.trotfan.trot.InAppProduct
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.list.StoreItem
import com.trotfan.trot.ui.components.navigation.AppbarL
import com.trotfan.trot.ui.home.BottomNavHeight
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.home.charge.component.MyVote
import com.trotfan.trot.ui.home.charge.viewmodel.ChargeHomeViewModel
import com.trotfan.trot.ui.home.vote.viewmodel.VoteHomeViewModel
import com.trotfan.trot.ui.login.components.LoginButton
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray800

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
            Text(
                text = purchaseStatusText,
                style = FanwooriTypography.h1,
                color = Color.Black
            )
            LazyColumn(
                Modifier.fillMaxWidth(), state = lazyListState ?: rememberLazyListState(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
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