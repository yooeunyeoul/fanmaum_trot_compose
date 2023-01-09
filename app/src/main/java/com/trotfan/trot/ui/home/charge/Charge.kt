package com.trotfan.trot.ui.home.charge

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.billingclient.api.BillingClient
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.R
import com.trotfan.trot.ui.home.HomeSections
import com.trotfan.trot.ui.login.components.LoginButton
import com.trotfan.trot.ui.theme.Gray800

@Composable
fun ChargeHome(
    onItemClick: (Long) -> Unit,
    navController: NavController,
    onNavigateClick: (HomeSections) -> Unit,
    modifier: Modifier = Modifier,
    purchaseHelper: PurchaseHelper
) {

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(Modifier.fillMaxSize()) {
            Text(text = "This is ChargeHome", fontWeight = FontWeight.Bold)
            BackHandler {
                onNavigateClick.invoke(HomeSections.Vote)
            }
            Column(Modifier.height(100.dp)) {
                LoginButton(
                    text = "상품 구매하기",
                    icon = painterResource(id = R.drawable.kakao_symbol),
                    textColor = Gray800,
                    backgroundColor = Color(0XFFFEE500),
                    borderWidth = 0.dp,
                ) {
                    purchaseHelper.makePurchase(BillingClient.ProductType.INAPP)
                }

            }

        }

    }
}