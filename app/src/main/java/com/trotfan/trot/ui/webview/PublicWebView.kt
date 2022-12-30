package com.trotfan.trot.ui.webview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PublicWebView(uri: String? = null) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )

                webViewClient = WebViewClient()

                isVerticalScrollBarEnabled = false
                isHorizontalScrollBarEnabled = false
                settings.javaScriptEnabled = true
            }
        },
        update = {
            uri?.let { it1 -> it.loadUrl(it1) }
        }
    )
}