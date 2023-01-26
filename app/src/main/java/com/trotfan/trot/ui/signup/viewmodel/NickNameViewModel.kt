package com.trotfan.trot.ui.signup.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.SignUpRepository
import com.trotfan.trot.ui.BaseViewModel
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
    private val repository: SignUpRepository,
    application: Application
) : BaseViewModel(application) {

    private val context = getApplication<Application>()

    val nickNameCheckStatus: StateFlow<NickNameCheckStatus>
        get() = _nickNameCheckStatus
    private val _nickNameCheckStatus =
        MutableStateFlow(NickNameCheckStatus.None)

    val inputNickName: StateFlow<String>
        get() = _inputNickName
    private val _inputNickName = MutableStateFlow("")


    init {
        Log.d("Initializing", "MainViewModel")
    }

    fun checkNickNameLocal(nickName: String) {
        viewModelScope.launch {
            when {
                !Pattern.matches("^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$", nickName)
                -> {
                    _nickNameCheckStatus.emit(NickNameCheckStatus.SpecialCharacterEmpty)
                }
                else -> {
                    _nickNameCheckStatus.emit(NickNameCheckStatus.None)
                    _inputNickName.emit(nickName)
                }
            }
        }
    }

    fun checkNickNameApi(nickName: String) {
        viewModelScope.launch {
            context.userIdStore.data.collect {
                kotlin.runCatching {
                    val response = repository.updateUser(
                        userid = it.userId,
                        nickName,
                        token = userLocalToken.value?.token ?: ""
                    )

                    when (response.result.code) {
                        ResultCodeStatus.UnAcceptableName.code -> {
                            _nickNameCheckStatus.emit(NickNameCheckStatus.NotAuth)
                        }
                        ResultCodeStatus.AlreadyInUse.code -> {
                            _nickNameCheckStatus.emit(NickNameCheckStatus.Duplicate)
                        }
                        ResultCodeStatus.Success.code -> {
                            _nickNameCheckStatus.emit(NickNameCheckStatus.AuthSuccess)
                        }
                    }
                }
            }
        }
    }


}
