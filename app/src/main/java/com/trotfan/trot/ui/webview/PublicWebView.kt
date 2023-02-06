package com.trotfan.trot.ui.webview

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.google.accompanist.web.*

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PublicWebView(uri: String? = null, navController: NavController?) {
    val webViewNavigator = rememberWebViewNavigator()
    val webViewState = rememberWebViewState(url = uri.toString())
    val webViewClient = AccompanistWebViewClient()
    val webChromeClient = AccompanistWebChromeClient()

    WebView(
        state = webViewState,
        client = webViewClient,
        chromeClient = webChromeClient,
        navigator = webViewNavigator,
        onCreated = { webView ->
            with(webView) {
                settings.run {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    javaScriptCanOpenWindowsAutomatically = false
                }
            }
        }
    )

    BackHandler(enabled = true) {
        if (webViewNavigator.canGoBack) {
            webViewNavigator.navigateBack()
        } else {
            navController?.popBackStack()
        }
    }
}