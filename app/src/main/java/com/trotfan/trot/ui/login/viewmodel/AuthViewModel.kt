package com.trotfan.trot.ui.login.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.model.GoogleToken
import com.trotfan.trot.model.KakaoTokens
import com.trotfan.trot.model.UserInfo
import com.trotfan.trot.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val _serverAvailable = MutableStateFlow(true)
    val serverAvailable: StateFlow<Boolean>
        get() = _serverAvailable

    private val _accessCode = MutableStateFlow("")
    val accessCode: StateFlow<String>
        get() = _accessCode

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?>
        get() = _userInfo

    init {
        getServerState()
    }

    fun getServerState() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getServerStateService()
            }.onSuccess { state ->
                if (state.isAvailable) {
                    Log.d("AuthViewModel", state.isAvailable.toString())
                    context.userTokenStore.data.collect {
                        _accessCode.emit(it.accessToken)
                    }
                } else {
                    //서버 점검
                    _serverAvailable.emit(false)
                }
            }.onFailure {
                Log.d("AuthViewModel", it.message.toString())
            }
        }
    }

    fun googleLogin(completedTask: Task<GoogleSignInAccount>) {
        viewModelScope.launch() {
            postGoogleToken(handleGoogleLogin(completedTask))
        }
    }

    fun postGoogleToken(handleGoogleLogin: String?) {
        viewModelScope.launch() {
            kotlin.runCatching {
                handleGoogleLogin.let { repository.postGoogleLogin(GoogleToken(it)) }
            }.onSuccess {
                Log.d("AuthViewModel", it.access_token)
                setUserToken(it.access_token)
                setUserId(it.user_id)
            }.onFailure {
                Log.d("AuthViewModel", it.toString())
            }
        }
    }

    fun postKakaoToken(kakaoTokens: KakaoTokens) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.postKakaoLogin(kakaoTokens)
            }.onSuccess {
                setUserToken(it.access_token)
                setUserId(it.user_id)
                Log.d("AuthViewModel", it.access_token)
            }.onFailure {
                Log.d("AuthViewModel", it.message.toString())
            }
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            context.userIdStore.data.collect {
                kotlin.runCatching {
                    repository.getUserInfo(it.userId.toInt())
                }.onSuccess {
                    _userInfo.emit(it.data)
                    Log.d("AuthViewModel", it.data.id.toString())
                }.onFailure {
                    Log.d("AuthViewModel", it.message.toString())
                }
            }
        }
    }

    private suspend fun handleGoogleLogin(completedTask: Task<GoogleSignInAccount>): String? =
        suspendCoroutine { continuation ->
            try {
                val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
                Log.d("AuthViewModel", "로그인 성공 ${account.serverAuthCode}")
                continuation.resume(account.serverAuthCode)
            } catch (e: ApiException) {
                Log.d("AuthViewModel", "로그인 실패 ${e.statusCode}")
                continuation.resume(null)
            }
        }

    suspend fun setUserToken(token: String) {
        context.userTokenStore.updateData {
            Log.d("setUserToken", "setUserToken")
            _accessCode.emit(token)
            it.toBuilder().setAccessToken(token).build()
        }
    }

    suspend fun setUserId(userId: Int) {
        context.userIdStore.updateData {
            Log.d("setUserId", userId.toString())
            it.toBuilder().setUserId(userId.toLong()).build()
        }
    }
}