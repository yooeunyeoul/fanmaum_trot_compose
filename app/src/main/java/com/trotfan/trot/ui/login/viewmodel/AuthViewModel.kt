package com.trotfan.trot.ui.login.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.datastore.*
import com.trotfan.trot.model.*
import com.trotfan.trot.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val loadingHelper: LoadingHelper,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val _serverAvailable = MutableStateFlow(true)
    val serverAvailable: StateFlow<Boolean>
        get() = _serverAvailable

    private val _userToken = MutableStateFlow<UserToken?>(null)
    val userToken: StateFlow<UserToken?>
        get() = _userToken

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?>
        get() = _userInfo

    var userInfoManager: UserInfoManager = UserInfoManager(context.UserInfoDataStore)

    fun getServerState() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            kotlin.runCatching {
                repository.getServerStateService()
            }.onSuccess { state ->
                if (state.isAvailable) {
                    context.userTokenStore.data.collect {
                        if (it.token.isNullOrEmpty().not()) {
                            getUserInfo(it.token)
                        }
                    }
                } else {
                    //서버 점검
                    _serverAvailable.emit(false)
                }
                loadingHelper.hideProgress()
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
            loadingHelper.showProgress()
            kotlin.runCatching {
                handleGoogleLogin.let { repository.postGoogleLogin(GoogleToken(it)) }
            }.onSuccess {
                val userToken = it.data
                userToken?.token?.let { token ->
                    getUserInfo(token)
                    setUserToken(token)
                }
                _userToken.emit(userToken)
                loadingHelper.hideProgress()
            }.onFailure {
                Log.d("AuthViewModel", it.toString())
                loadingHelper.hideProgress()
            }
        }
    }

    fun postAppleToken(token: AppleToken) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            kotlin.runCatching {
                repository.postAppleLogin(token)
            }.onSuccess {
                val userToken = it.data
                userToken?.token?.let { token ->
                    getUserInfo(token)
                    setUserToken(token)
                }
                _userToken.emit(userToken)
                loadingHelper.hideProgress()
            }.onFailure {
                Log.d("AuthViewModel", it.message.toString())
                loadingHelper.hideProgress()
            }
        }
    }

    fun postKakaoToken(kakaoTokens: KakaoTokens) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            kotlin.runCatching {
                repository.postKakaoLogin(kakaoTokens)
            }.onSuccess {
                val userToken = it.data
                userToken?.token?.let { token ->
                    getUserInfo(token)
                    setUserToken(token)
                }
                _userToken.emit(userToken)
                loadingHelper.hideProgress()
            }.onFailure {
                Log.d("AuthViewModel", it.message.toString())
                loadingHelper.hideProgress()
            }
        }
    }

    fun getUserInfo(token: String) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            kotlin.runCatching {
                repository.getUserInfo(token)
            }.onSuccess {
                val userInfo = it.data
                saveUserInfo(userInfo)
                setUserId(it.data?.id ?: 0)
                _userInfo.emit(userInfo)
                Log.d("AuthViewModel", userInfo.toString())
                loadingHelper.hideProgress()
            }.onFailure {
                Log.d("AuthViewModel", it.message.toString())
                loadingHelper.hideProgress()
            }
        }
    }

    private fun saveUserInfo(userInfo: UserInfo?) {
        viewModelScope.launch {
            userInfo?.star?.let {
                userInfoManager.storeUserInfo(
                    favoriteStarId = it.id,
                    favoriteGender = it.gender,
                    favoriteStarName = it.name,
                    favoriteStarImage = it.image,
                    userName = userInfo.name ?: "",
                    userIdp = userInfo.idp,
                    userMail = userInfo.email ?: "",
                    userProfileImage = userInfo.image ?: "",
                    userCreatedAt = userInfo.created_at,
                    userTotalUsedVote = userInfo.total_used_votes
                )

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
            Log.d("setUserToken", token)
            it.toBuilder().setToken(token).build()
        }
    }

    suspend fun setUserId(userId: Int) {
        context.userIdStore.updateData {
            Log.d("setUserId", userId.toString())
            it.toBuilder().setUserId(userId.toLong()).build()
        }
    }
}