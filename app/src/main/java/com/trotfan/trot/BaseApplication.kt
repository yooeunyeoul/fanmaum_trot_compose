package com.trotfan.trot

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            KakaoSdk.init(this, getString(R.string.kakao_navite_app_key_debug))
        } else {
            KakaoSdk.init(this, getString(R.string.kakao_navite_app_key_qa))
        }
    }
}