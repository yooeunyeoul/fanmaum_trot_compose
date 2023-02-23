package com.trotfan.trot

import android.content.Context
import android.util.Log
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.QueryProductDetailsParams.Product
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.model.Tickets
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.ChargeRepository
import com.trotfan.trot.ui.MainActivity
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class InAppProduct(
    val id: String,
    val productName: String,
    val price: String,
    val image: Int,
    val votes: String,
    val bonus: String
) {
    Votes_6000(
        id = "votes_6000",
        productName = "6,000투표권",
        price = "1,500원",
        image = R.drawable.charge6000,
        votes = "5,500",
        bonus = "500"

    ),
    Votes_22000(
        "votes_22000",
        productName = "22,000투표권",
        price = "4,400원",
        image = R.drawable.charge22000,
        votes = "20,000",
        bonus = "2,000"
    ),
    Votes_63000(
        "votes_63000",
        productName = "63,000투표권",
        price = "9,900원",
        image = R.drawable.charge63000,
        votes = "55,000",
        bonus = "8,000"
    ),
    Votes_160000(
        "votes_160000",
        productName = "160,000투표권",
        price = "19,000원",
        image = R.drawable.charge160000,
        votes = "135,000",
        bonus = "25,000"
    ),
    Votes_450000(
        "votes_450000",
        productName = "450,000투표권",
        price = "50,000원",
        image = R.drawable.charge450000,
        votes = "360,000",
        bonus = "90,000"
    )
}

enum class RefreshTicket {
    Need, Unnecessary
}

enum class BillingResponse(val message: String) {
    Success("결제완료"),
    Fail(
        "결제실패\n" +
                "처음부터 다시 시도해주세요."
    )
}

class PurchaseHelper @Inject constructor(
    @ActivityContext private val context: Context,
    val repository: ChargeRepository,
    private val loadingHelper: LoadingHelper
) {

    private lateinit var mListener: (BillingResponse) -> Unit
    private lateinit var mSelectedProductId: String
    private var activity: MainActivity
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private lateinit var billingClient: BillingClient
    private var productDetailList: List<ProductDetails> = listOf()
    private lateinit var purchasedItem: Purchase

    private val _productName = MutableStateFlow("Searching...")
    val productName = _productName.asStateFlow()

    val tickets: StateFlow<Tickets>
        get() = _tickets
    private val _tickets =
        MutableStateFlow<Tickets>(Tickets(0, 0))

    private val _refreshState = MutableStateFlow(RefreshTicket.Need)
    val refreshState = _refreshState.asStateFlow()

    private val _statusText = MutableStateFlow("Initializing...")
    val statusText = _statusText.asStateFlow()

    var mBillingType = BillingClient.ProductType.INAPP


    init {
        activity = (context as MainActivity)
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
                            //서버에 구매성공 요청 날리기

                            coroutineScope.launch {
                                loadingHelper.showProgress()
                                context.userIdStore.data.collect {
                                    kotlin.runCatching {
                                        val userToken = context.userTokenStore.data.map {
                                            it.token
                                        }.first()

                                        _statusText.emit(
                                            _statusText.value + "\n" +
                                                    "userToken=${userToken},\n" +
                                                    "userId = ${it.userId.toInt()}\n," +
                                                    "purchaseId= ${mSelectedProductId}\n," +
                                                    "purchaseToken =${purchase.purchaseToken},\n" +
                                                    "packageName=${context.packageName}"
                                        )
                                        repository?.certificationCharge(
                                            userToken = userToken,
                                            userId = it.userId.toInt(),
                                            productId = mSelectedProductId,
                                            purchaseToken = purchase.purchaseToken,
                                            packageName = context.packageName
                                        )

                                    }.onSuccess { response ->
                                        coroutineScope.launch {
                                            _statusText.emit(
                                                _statusText.value + "\n" +
                                                        "리스폰스값 : ${response?.result}"
                                            )
                                        }
                                        when (response?.result?.code) {
                                            ResultCodeStatus.SuccessWithData.code -> {
                                                coroutineScope.launch {
                                                    _statusText.emit(
                                                        _statusText.value + "\n" +
                                                                "Server Auth Success"
                                                    )
                                                }
                                                completePurchase(purchase)
                                            }
                                            ResultCodeStatus.Fail.code -> {
                                                Log.e("Api Fail", "Charge Fail")
                                                coroutineScope.launch {
                                                    _statusText.emit(
                                                        _statusText.value + "\n" +
                                                                "Server Auth Fail"
                                                    )
                                                }
                                                completePurchase(purchase)
                                            }
                                        }
                                        loadingHelper.hideProgress()
                                    }.onFailure {
                                        Log.e("Api Fail", "Api Fail")
                                        coroutineScope.launch {
                                            _statusText.emit(
                                                _statusText.value + "\n" +
                                                        "Api Fail\n" +
                                                        "reason : ${it.message}"
                                            )
                                        }
                                        completePurchase(purchase)
                                        loadingHelper.hideProgress()
                                    }
                                }
                            }
                        }
                    }
                    // 구매 취소 시
                    billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED -> {
                        Log.e("User Cancelled", "Cancelled")
                        coroutineScope.launch {
                            _statusText.emit(
                                _statusText.value + "\n" +
                                        "User Cancelled"
                            )
                        }
                        loadingHelper.hideProgress()
                    }
                    // 에러
                    else -> {
                        Log.e("Purchase Error", "Error")
                        coroutineScope.launch {
                            _statusText.emit(
                                _statusText.value + "\n" +
                                        "Purchase Error"
                            )
                        }
                        loadingHelper.hideProgress()
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
                    coroutineScope.launch {
                        _statusText.emit(
                            _statusText.value + "\n" +
                                    "Billing Connected Success"
                        )

                    }
                    queryProduct()
                    reloadPurchase()
                } else {
                    Log.e("Billing Connected", "Failed")
                    coroutineScope.launch {
                        _statusText.emit(
                            _statusText.value + "\n" +
                                    "Billing Connected Failed"
                        )

                    }

                }

            }

            override fun onBillingServiceDisconnected() {
                // 연결 실패 시 재시도 로직을 구현.
                Log.e("Billing Connected", "Disconnected")
                coroutineScope.launch {
                    _statusText.emit(
                        _statusText.value + "\n" +
                                "Billing Disconnected"
                    )
                }
            }
        })
    }

    fun makePurchaseInApp(
        billingType: String,
        inAppProduct: InAppProduct,
        listener: (BillingResponse) -> Unit
    ) {
        mListener = listener
        mBillingType = billingType
        mSelectedProductId = inAppProduct.id
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
                coroutineScope.launch {
                    _statusText.emit(
                        _statusText.value + "\n" +
                                "구매 에러 발생"
                    )
                }
                mListener.invoke(BillingResponse.Fail)
            }
        }
    }

    private fun completePurchase(item: Purchase) {
        purchasedItem = item
        if (purchasedItem.purchaseState == Purchase.PurchaseState.PURCHASED) {
            Log.e("Purchase Complete", "Purchase Complete")
            coroutineScope.launch {
                _statusText.emit(
                    _statusText.value + "\n" +
                            "Purchase Complete"
                )
            }
            if (mBillingType == BillingClient.ProductType.INAPP) {
                coroutineScope.launch {
                    _statusText.emit(
                        _statusText.value + "\n" +
                                "소비 처리 하기"
                    )
                }
                consumePurchase(isLastIndex = true)
                mListener.invoke(BillingResponse.Success)
            }
        }
    }

    //소비성 상품인 경우에는 소비 처리를 해주어야 상품을 다시 구매 할 수 있다.
    fun consumePurchase(isLastIndex: Boolean) {
        val consumeParams =
            ConsumeParams.newBuilder().setPurchaseToken(purchasedItem.purchaseToken).build()

        coroutineScope.launch {
            billingClient.consumeAsync(
                consumeParams
            ) { billingResult, s ->
                Log.e("Purchase Consumed", "Purchase Consumed")
                if (isLastIndex) {
                    coroutineScope.launch {
                        _refreshState.value = RefreshTicket.Need
                        _statusText.emit(
                            _statusText.value + "\n" +
                                    "소비 처리 완료"
                        )
                    }

                }
            }
        }

    }

    fun closeApiCall() {
        coroutineScope.launch {
            _statusText.emit(
                _statusText.value + "\n" +
                        "Votes APi 호출"
            )
        }
        _refreshState.value = RefreshTicket.Unnecessary
    }

    private fun queryProduct() {
        val productList = ArrayList<Product>()
        InAppProduct.values().map { inAppProduct ->
            productList.add(
                Product.newBuilder().setProductId(inAppProduct.id)
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
                    coroutineScope.launch {
                        _statusText.emit(
                            _statusText.value + "\n" +
                                    "Product${it.title}"
                        )
                    }

                }
            } else {
                Log.e("Product No Matching", "No Product Matching")
                coroutineScope.launch {
                    _statusText.emit(
                        _statusText.value + "\n" +
                                "Product No Matching"
                    )
                }

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
                purchases.forEachIndexed { index, purchase ->
                    coroutineScope.launch {
                        _statusText.emit(
                            _statusText.value + "\n" +
                                    "Previous Purchase ${purchase.orderId} is Consumed"
                        )
                    }

                    purchasedItem = purchase
                    consumePurchase(index == purchases.lastIndex)
                }
                Log.e("Previous Purchase Found", "Previous Purchase Found")
            }
        }
    }

    fun refreshTickets(tickets: Tickets) {
        _tickets.value = tickets
    }


}