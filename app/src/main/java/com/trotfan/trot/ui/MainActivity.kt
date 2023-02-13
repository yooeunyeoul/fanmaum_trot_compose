package com.trotfan.trot.ui

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.ui.home.charge.roullete.luckyRoulette
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val invitationViewModel: InvitationViewModel by viewModels()
    @Inject
    lateinit var purchaseHelper: PurchaseHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDynamicLink()

        setContent {

            FanwooriApp(
                purchaseHelper = purchaseHelper
            )
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
}
