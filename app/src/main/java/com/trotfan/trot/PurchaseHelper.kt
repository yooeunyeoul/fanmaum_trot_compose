package com.trotfan.trot

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.QueryProductDetailsParams.Product
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


enum class InAppProduct(
    val id: String,
    val productName: String,
    val price: String,
    val image: String,
    val bonus: String
) {
    Votes_6000(
        id = "votes_6000",
        productName = "6,000투표권",
        price = "1,200원",
        image = "https://play-lh.googleusercontent.com/FYqtvM48ZjqrUOF8dpZTYyY67H2ei4uVwBAZFHpXDS-6vtbQHqf1xf1KbLgZV5V6ScQ",
        bonus = "500"

    ),
    Votes_21000(
        "votes_21000",
        productName = "21,000투표권",
        price = "4,400원",
        image = "https://play-lh.googleusercontent.com/FYqtvM48ZjqrUOF8dpZTYyY67H2ei4uVwBAZFHpXDS-6vtbQHqf1xf1KbLgZV5V6ScQ",
        bonus = "1,500"
    ),
    Votes_63000(
        "votes_63000",
        productName = "63,000투표권",
        price = "9,900원",
        image = "https://play-lh.googleusercontent.com/FYqtvM48ZjqrUOF8dpZTYyY67H2ei4uVwBAZFHpXDS-6vtbQHqf1xf1KbLgZV5V6ScQ",
        bonus = "5,000"
    ),
    Votes_160000(
        "votes_160000",
        productName = "160,000투표권",
        price = "19,000원",
        image = "https://play-lh.googleusercontent.com/FYqtvM48ZjqrUOF8dpZTYyY67H2ei4uVwBAZFHpXDS-6vtbQHqf1xf1KbLgZV5V6ScQ",
        bonus = "10,000"
    ),
    Votes_450000(
        "votes_450000",
        productName = "450,000투표권",
        price = "50,000원",
        image = "https://play-lh.googleusercontent.com/FYqtvM48ZjqrUOF8dpZTYyY67H2ei4uVwBAZFHpXDS-6vtbQHqf1xf1KbLgZV5V6ScQ",
        bonus = "25,000"
    )
}

data class PurchaseHelper @Inject constructor(val activity: Activity) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private lateinit var billingClient: BillingClient
    private var productDetailList: List<ProductDetails> = listOf()
    private lateinit var purchasedItem: Purchase

    private val _productName = MutableStateFlow("Searching...")
    val productName = _productName.asStateFlow()

    private val _buyEnabled = MutableStateFlow(false)
    val buyEnabled = _buyEnabled.asStateFlow()

    private val _consumeEnabled = MutableStateFlow(false)
    val consumeEnabled = _consumeEnabled.asStateFlow()

    private val _statusText = MutableStateFlow("Initializing...")
    val statusText = _statusText.asStateFlow()

    var mBillingType = BillingClient.ProductType.INAPP

    val productIdList = listOf(
        InAppProduct.Votes_6000.id,
        InAppProduct.Votes_21000.id,
        InAppProduct.Votes_63000.id,
        InAppProduct.Votes_160000.id,
        InAppProduct.Votes_450000.id
    )

    init {
        billingSetup()
    }


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

    fun makePurchaseInApp(billingType: String, inAppProduct: InAppProduct) {
        mBillingType = billingType
        val product = productDetailList.firstOrNull() { it.productId == inAppProduct.id }
        if (product != null) {
            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(
                    listOf(
                        ProductDetailsParams.newBuilder()
                            .setProductDetails(product).build()
                    )
                ).build()
            val billingResult = billingClient.launchBillingFlow(activity, billingFlowParams)
            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                Log.e("구매 에러 발생", "구매 에러 발생")
            }
        }

    }

    private fun completePurchase(item: Purchase) {
        purchasedItem = item
        if (purchasedItem.purchaseState == Purchase.PurchaseState.PURCHASED) {
            Log.e("Purchase Complete", "Purchase Complete")
            if (mBillingType == BillingClient.ProductType.INAPP) {
                consumePurchase()
            }
        }
    }

    //소비성 상품인 경우에는 소비 처리를 해주어야 상품을 다시 구매 할 수 있다.
    fun consumePurchase() {
        val consumeParams =
            ConsumeParams.newBuilder().setPurchaseToken(purchasedItem.purchaseToken).build()

        coroutineScope.launch {
            billingClient.consumeAsync(
                consumeParams
            ) { billingResult, s ->
                Log.e("Purchase Consumed", "Purchase Consumed")
            }
        }

    }

    private fun queryProduct() {
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
                this.productDetailList = productDetailList
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
                purchasedItem = purchases.first()
                Log.e("Previous Purchase Found", "Previous Purchase Found")

            }
        }
    }


}