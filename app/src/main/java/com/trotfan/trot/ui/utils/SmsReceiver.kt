package com.trotfan.trot.ui.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import timber.log.Timber

class SmsReceiver : BroadcastReceiver() {
    companion object {
        private var smsListener: SmsBroadcastReceiverListener? = null
        fun bindListener(smsListener: SmsBroadcastReceiverListener) {
            this.smsListener = smsListener
        }

        fun unbindListener() {
            this.smsListener = null
        }
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            val extras = intent.extras
            val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status
            Timber.e(extras.toString())

            when (smsRetrieverStatus?.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    Timber.e("Success")
                    // 정규표현식을 활용해서 인증번호를 파싱한다
                    var message: String = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                    message = message.replace("<#> ", "")
                    message = message.split("\n".toRegex())[1]
                    val regex = """인증번호 (\d+) 를 입력창에 기입해주세요.""".toRegex()
                    val matchResult = regex.find(message)
                    matchResult?.destructured?.let {
                        val (authCode) = it
                        smsListener?.onSuccess(authCode)

                    }
                }
                CommonStatusCodes.TIMEOUT -> {
                    Timber.e("에러났는데 이거 뭐냐")
                }
            }
        }
    }

    interface SmsBroadcastReceiverListener {
        fun onSuccess(authCode: String?)
        fun onFailure()
    }
}