package com.trotfan.trot.ui.signup.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.SignUpRepository
import com.trotfan.trot.ui.BaseViewModel
import com.trotfan.trot.ui.signup.viewmodel.InviteCodeCheckStatus.InvalidCodeError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

enum class InviteCodeCheckStatus(val code: String) {
    None(""),
    FirstCharacterError("초대 코드는 #으로 시작해요."),
    SpecialCharacterEmpty("#을 제외한 특수문자와 공백은 입력할 수 없어요."),
    InvalidCodeError("유효하지 않는 코드에요.")
}

@HiltViewModel
class InvitationViewModel @Inject constructor(
    private val repository: SignUpRepository,
    private val loadingHelper: LoadingHelper,
    application: Application
) : BaseViewModel(application) {

    private val context = getApplication<Application>()
    val completeStatus: StateFlow<Boolean>
        get() = _completeStatus
    private val _completeStatus = MutableStateFlow(false)

    val skipStatus: StateFlow<Boolean>
        get() = _skipStatus
    private val _skipStatus = MutableStateFlow(false)

    val inviteCodeCheckStatus: StateFlow<InviteCodeCheckStatus>
        get() = _inviteCodeCheckStatus
    private val _inviteCodeCheckStatus =
        MutableStateFlow(InviteCodeCheckStatus.None)

    val inviteCode: StateFlow<String>
        get() = _inviteCode
    private val _inviteCode = MutableStateFlow("")

    fun checkInviteCodeLocal(code: String) {
        viewModelScope.launch {
            when {
                !Pattern.matches("^[0-9|a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣#]*\$", code)
                -> {
                    _inviteCodeCheckStatus.emit(InviteCodeCheckStatus.SpecialCharacterEmpty)
                }
                code.isNotBlank() && code[0] != '#'
                -> {
                    _inviteCodeCheckStatus.emit(InviteCodeCheckStatus.FirstCharacterError)
                }
                else -> {
                    _inviteCodeCheckStatus.emit(InviteCodeCheckStatus.None)
                    _inviteCode.emit(code)
                }
            }
        }
    }

    fun postInviteCode() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userIdStore.data.collect {
                kotlin.runCatching {
                    repository.postUserRedeemCode(
                        userid = it.userId,
                        redeemCode = _inviteCode.value,
                        token = userLocalToken.value?.token ?: ""
                    )
                }.onSuccess {
                    when (it.result?.code) {
                        ResultCodeStatus.InvalidCode.code -> _inviteCodeCheckStatus.emit(
                            InvalidCodeError
                        )
                        ResultCodeStatus.SuccessWithNoData.code -> {
                            if (_inviteCode.value != "") _completeStatus.emit(true)
                            else _skipStatus.emit(true)
                        }
                    }
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                }
            }
        }
    }
}