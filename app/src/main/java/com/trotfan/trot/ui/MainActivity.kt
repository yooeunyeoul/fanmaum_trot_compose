package com.trotfan.trot.ui

import AppSignatureHelper
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.integration.IntegrationHelper
import com.trotfan.trot.BaseApplication
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.R
import com.trotfan.trot.ui.utils.SmsReceiver
import com.trotfan.trot.ui.utils.composableActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    private val invitationViewModel: InvitationViewModel by viewModels()
    @Inject
    lateinit var purchaseHelper: PurchaseHelper

    @Inject
    lateinit var loadingHelper: LoadingHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDynamicLink()
        AppSignatureHelper
        settingIronSource()
        startSmsRetriever()

        val appSignature = AppSignatureHelper(this)
        Log.e("HASH", "\"${appSignature.appSignatures}\"")


        setContent {
            val isProgressVisible by loadingHelper.progressbar.collectAsState()
            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_spinner))

            Box() {
                if (isProgressVisible) {
                    Dialog(
                        onDismissRequest = { },
                        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                    ) {
                        Box(
                            contentAlignment = Center,
                            modifier = Modifier
                                .size(120.dp)
                                .background(Transparent, shape = RoundedCornerShape(8.dp))
                                .align(Center)
                        ) {
                            LottieAnimation(
                                composition = composition,
                                iterations = LottieConstants.IterateForever,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                }
                FanwooriApp(
                    purchaseHelper = purchaseHelper
                )
            }

        }
    }


    private fun getDynamicLink() {
        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener {
                var deepLink: Uri? = null
                if (it != null) {
                    lifecycleScope.launch {
                        deepLink = it.link
                        val code = deepLink?.getQueryParameters("invite_code")
                        code?.get(0)?.let { c ->
//                            invitationViewModel.setInviteCode(code = c)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d("dynamicLinkTest", it.toString())
            }
    }

    private fun settingIronSource() {
        IronSource.init(this, "17f942e2d", IronSource.AD_UNIT.REWARDED_VIDEO)
        IntegrationHelper.validateIntegration(this)
    }

    private fun startSmsRetriever() {
        val client = SmsRetriever.getClient(this)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            Timber.e("startSmsRetriever Success: $it")
            startSMSListener()
        }
        task.addOnFailureListener {
            Timber.e("startSmsRetriever fail: $it")
        }
    }

    private fun startSMSListener() {
        SmsReceiver.bindListener(object : SmsReceiver.SmsBroadcastReceiverListener {
            override fun onSuccess(code: String?) {
                if (code?.length != 6) {
                    Timber.e("Finish")
                }
                Timber.e(code.toString())
            }

            override fun onFailure() {
                Timber.e("SmsBroadcastReceiverListener onFailure")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        IronSource.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }
}
