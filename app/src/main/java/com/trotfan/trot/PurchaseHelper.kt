package com.trotfan.trot

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.QueryProductDetailsParams.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class PurchaseHelper(val activity: Activity) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private lateinit var billingClient: BillingClient
    private lateinit var productDetails: List<ProductDetails>
    private lateinit var purchase: Purchase

    private val demoProductId = "one_button_click"

    private val _productName = MutableStateFlow("Searching...")
    val productName = _productName.asStateFlow()

    private val _buyEnabled = MutableStateFlow(false)
    val buyEnabled = _buyEnabled.asStateFlow()

    private val _consumeEnabled = MutableStateFlow(false)
    val consumeEnabled = _consumeEnabled.asStateFlow()

    private val _statusText = MutableStateFlow("Initializing...")
    val statusText = _statusText.asStateFlow()

    var mBillingType = BillingClient.ProductType.INAPP


    fun billingSetup() {
        billingClient = BillingClient.newBuilder(activity)
            .setListener { billingResult, purchseList ->
                //모든 구매 관련 업데이트를 수신한다.
                when {

                    // 구매 성공 시 처리
                    billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchseList != null -> {
                        for (purchase in purchseList) {
                            completePurchase(purchase)
                            //서버에 구매성공 요청 날리기
                        }
                    }
                    // 구매 취소 시
                    billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED -> {
                        Log.e("User Cancelled", "Cancelled")
                    }
                    // 에러
                    else -> {
                        Log.e("Purchase Error", "Error")
                    }
                }
            }
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // 준비 완료가 되면 상품 쿼리를 처리 할 수 있다!
                    _statusText.value = "Billing Client Connected"
                    Log.e("Billing Connected", "Success")
                    queryProduct()
                    reloadPurchase()
                } else {
                    Log.e("Billing Connected", "Failed")

                }

            }

            override fun onBillingServiceDisconnected() {
                // 연결 실패 시 재시도 로직을 구현.
                Log.e("Billing Connected", "Disconnected")
            }
        })
    }

    fun makePurchase(billingType: String) {
        mBillingType = billingType
        val billingFlowParamList = arrayListOf<ProductDetailsParams>()
        productDetails.forEach {
            billingFlowParamList.add(
                ProductDetailsParams.newBuilder().setProductDetails(it).build()
            )
        }

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                billingFlowParamList
            ).build()
        val billingResult = billingClient.launchBillingFlow(activity, billingFlowParams)
        if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
            Log.e("구매 에러 발생", "구매 에러 발생")
        }
    }

    private fun completePurchase(item: Purchase) {
        purchase = item
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            Log.e("Purchase Complete", "Purchase Complete")
            if (mBillingType == BillingClient.ProductType.INAPP) {
                consumePurchase()
            }
        }
    }

    //소비성 상품인 경우에는 소비 처리를 해주어야 상품을 다시 구매 할 수 있다.
    fun consumePurchase() {
        val consumeParams =
            ConsumeParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()

        coroutineScope.launch {
            billingClient.consumeAsync(
                consumeParams
            ) { billingResult, s ->
                Log.e("Purchase Consumed", "Purchase Consumed")
            }
        }

    }

    private fun queryProduct() {
        val productIdList = listOf(
            "votes_6000",
            "votes_21000",
            "votes_63000",
            "votes_450000",
            "votes_160000"
        )
        val productList = ArrayList<Product>()
        productIdList.forEach { id ->
            productList.add(
                Product.newBuilder().setProductId(id)
                    .setProductType(BillingClient.ProductType.INAPP).build()
            )
        }

        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()


        billingClient.queryProductDetailsAsync(
            queryProductDetailsParams
        ) { billingResult, productDetailList ->
            if (productDetailList.isNotEmpty()) {
                productDetails = productDetailList
                productDetailList.forEach {
                    Log.e("Product${it.title}", it.name)
                }
            } else {
                Log.e("Product No Matching", "No Product Matching")
            }
        }
    }

    private fun reloadPurchase() {
        val queryPurchasesParams =
            QueryPurchasesParams
                .newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()

        billingClient.queryPurchasesAsync(
            queryPurchasesParams
        ) { billingResult, purchases ->
            if (purchases.isNotEmpty()) {
                purchase = purchases.first()
                Log.e("Previous Purchase Found", "Previous Purchase Found")

            }
        }
    }


}