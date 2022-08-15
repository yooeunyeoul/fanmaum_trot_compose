package com.trotfan.trot.ui.signup.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class CertificationNumberCheckStatus(
    val content: String,
    val buttonText: String
) {
    TimeExceeded(
        content = "입력 가능한 시간이 지났어요.\n" +
                "인증번호를 다시 받아주세요.",
        buttonText = "인증번호 다시 받기"
    ),
    NotAuth(
        content = "유효하지 않은 인증번호예요.\n" +
                "인증번호를 다시 받아주세요.",
        buttonText = "인증번호 다시 받기"
    ),
    AuthSuccess(
        content = "인증번호가 확인 되었어요.",
        buttonText = "확인"
    )
}

@HiltViewModel
class CertificationPhoneNumberViewModel @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {

    val certificationNumberStatus: StateFlow<CertificationNumberCheckStatus?>
        get() = _certificationNumberStatus
    private val _certificationNumberStatus =
        MutableStateFlow<CertificationNumberCheckStatus?>(null)

    private val _certificationNumber = MutableStateFlow<String>("")
    val certificationNumber = _certificationNumber

    private val _onComplete = MutableStateFlow(false)
    val onComplete: StateFlow<Boolean>
        get() = _onComplete


    fun requestCertificationCode(phoneNumber: String, randomCode: String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
//                val randomCode = (111111..999999).shuffled().last()
                val message = "팬우리 인증번호 ${randomCode} 입니다."
                val response = repository.requestSmsCertification(
                    phoneNumber = phoneNumber,
                    message = message
                )
                if (response.result_code == "200") {
                    Log.e("문자인증", "성공")
                    _certificationNumber.emit(randomCode.toString())
                } else {
                    Log.e("문자인증", "실패")
                }
                Log.e("문자인증 코드", randomCode.toString())
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }
    }


    fun checkAuthNumber(number: String, time: Int) {
        viewModelScope.launch {
            if (time == 0) {
                _certificationNumberStatus.emit(CertificationNumberCheckStatus.TimeExceeded)
            } else {
                when (number) {
                    _certificationNumber.value -> {
                        _certificationNumberStatus.emit(CertificationNumberCheckStatus.AuthSuccess)
                    }
                    else -> {
                        _certificationNumberStatus.emit(CertificationNumberCheckStatus.NotAuth)
                    }
                }
            }
        }
    }

    fun hideCertificateDialog() {
        viewModelScope.launch {
            _certificationNumberStatus.emit(null)
        }
    }

    fun updateUser(phoneNum: String) {
        viewModelScope.launch {
            val response = repository.updateUser(userid = "1", phoneNumber = phoneNum)
            if (response.code == 200) {
                _onComplete.emit(true)
            }
        }
    }


}
