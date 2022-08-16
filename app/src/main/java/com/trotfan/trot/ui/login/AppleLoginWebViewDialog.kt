package com.trotfan.trot.ui.login

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.trotfan.trot.model.AppleToken
import java.util.*


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AppleLoginWebViewDialog(onDismissRequest: (AppleToken?) -> Unit) {
    val context = LocalContext.current
    val accessCode: AppleToken? = null

    Dialog(
        onDismissRequest = { onDismissRequest(accessCode) },
        properties = DialogProperties()
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        ) {
            val uri =
                "${AppleConst.APPLE_AUTH_URL}?response_type=code&v=1.1.6&response_mode=form_post&client_id=${AppleConst.APPLE_CLIENT_ID}&scope=${AppleConst.APPLE_SCOPE}&state=${UUID.randomUUID()}&redirect_uri=${AppleConst.APPLE_REDIRECT_URI}"
            AndroidView(
                factory = {
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )

                        webViewClient = AppleWebViewClient(onDismissRequest)

                        isVerticalScrollBarEnabled = false
                        isHorizontalScrollBarEnabled = false
                        settings.javaScriptEnabled = true
                    }
                },
                update = {
                    it.loadUrl(uri)
                }
            )
        }
    }
}


class AppleWebViewClient(private val onDismissRequest: (AppleToken?) -> Unit) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Log.d("AuthViewModel", request?.url?.toString() + "")
        val idToken = request?.url?.getQueryParameter("id-token")
        val accessToken = request?.url?.getQueryParameter("access-token")
        val refreshToken = request?.url?.getQueryParameter("refresh-token")
        onDismissRequest(AppleToken(idToken, accessToken, refreshToken))
        return super.shouldOverrideUrlLoading(view, request)
    }
}