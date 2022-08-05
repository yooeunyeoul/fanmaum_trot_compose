package com.trotfan.trot.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.trotfan.trot.R
import com.trotfan.trot.ui.invitation.InvitationScreen
import com.trotfan.trot.ui.signup.SearchStarScreen
import com.trotfan.trot.ui.signup.SelectStarScreen
import com.trotfan.trot.ui.signup.SettingNicknameScreen
import com.trotfan.trot.ui.theme.FanwooriTheme
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.network.selector.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FanwooriTheme {
//                SearchStarScreen()
                SettingNicknameScreen()
//                InvitationScreen {
//                    dynamicLinkTest()
//                }
            }
//            getDynamicLinkTest()
        }
    }

    private fun dynamicLinkTest() {
        val dynamicLink = Firebase.dynamicLinks.shortLinkAsync {
            link = Uri.parse("https://www.fanwoori.com/?test=123123&name=Jone")
            domainUriPrefix = getString(R.string.dynamic_link_url)
            androidParameters(packageName) {}
            iosParameters("com.trotfan.trotDev") {}
            socialMetaTagParameters {
                title = "Example of a Dynamic Link"
                description = "Ths link works whether the app is installed or not!"
            }
        }.addOnSuccessListener {
            Log.d("dynamicLinkTest", it.shortLink.toString())
        }
    }

    private fun getDynamicLinkTest() {
        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener {
                var deepLink: Uri? = null
                if (it != null) {
                    deepLink = it.link
                    val test = deepLink?.getQueryParameters("test")
                    val name = deepLink?.getQueryParameters("name")
                    Log.d("dynamicLinkTest", "${test?.get(0)} // ${name?.get(0)}")
                }
            }
            .addOnFailureListener {
                Log.d("dynamicLinkTest", it.toString())
            }
    }
}
