package com.trotfan.trot.ui.signup.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

enum class NickNameCheckStatus(val message: String) {
    None(""),
    Duplicate("이미 사용 중인 닉네임이에요."),
    NotAuth("허용되지 않는 닉네임이에요."),
    SpecialCharacterEmpty("특수문자, 공백은 입력할 수 없어요."),
    AuthSuccess("사용 가능한 닉네임이에요.")
}

@HiltViewModel
class NickNameViewModel @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {

    val nickNameCheckStatus: StateFlow<NickNameCheckStatus>
        get() = _nickNameCheckStatus
    private val _nickNameCheckStatus =
        MutableStateFlow(NickNameCheckStatus.None)


    init {
        Log.d("Initializing", "MainViewModel")
    }

    fun checkNickNameLocal(nickName: String) {
        viewModelScope.launch {
            when {
                !Pattern.matches("^[0-9|a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣#]*\$", nickName)
                -> {
                    _nickNameCheckStatus.emit(NickNameCheckStatus.SpecialCharacterEmpty)
                }
                else -> {
                    _nickNameCheckStatus.emit(NickNameCheckStatus.None)
                }
            }
        }
    }

    fun checkNickNameApi(nickName: String) {
        viewModelScope.launch {
            val response = repository.updateUser(userid = "2", nickName)

            when (response.code) {
                1 -> {
                    _nickNameCheckStatus.emit(NickNameCheckStatus.NotAuth)
                }
                2 -> {
                    _nickNameCheckStatus.emit(NickNameCheckStatus.Duplicate)
                }
                200 -> {
                    _nickNameCheckStatus.emit(NickNameCheckStatus.AuthSuccess)
                }
            }
        }
    }


}
