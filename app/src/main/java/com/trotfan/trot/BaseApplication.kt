package com.trotfan.trot

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.lyft.kronos.AndroidClockFactory
import com.lyft.kronos.KronosClock
import com.lyft.kronos.KronosTime
import com.zoyi.channel.plugin.android.ChannelIO
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    lateinit var kronosClock: KronosClock
    override fun onCreate() {
        super.onCreate()

        kronosClock = AndroidClockFactory.createKronosClock(applicationContext)
        kronosClock.syncInBackground()



        if (BuildConfig.DEBUG) {
            KakaoSdk.init(this, getString(R.string.kakao_navite_app_key_debug))
        } else {
            KakaoSdk.init(this, getString(R.string.kakao_navite_app_key_qa))
        }

        ChannelIO.initialize(this)
        ChannelIO.showChannelButton()
    }
}