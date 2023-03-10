package com.trotfan.trot.ui.home.mypage.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.datastore.*
import com.trotfan.trot.repository.MyPageRepository
import com.trotfan.trot.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val loadingHelper: LoadingHelper,
    application: Application
) : BaseViewModel(application) {

    lateinit var userInfoManager: UserInfoManager
    lateinit var userTicketManager: UserTicketManager
    private val context = getApplication<Application>()

    val userName: StateFlow<String>
        get() = _userName
    private val _userName =
        MutableStateFlow("")

    val starName: StateFlow<String>
        get() = _starName
    private val _starName =
        MutableStateFlow("")

    val userIdp: StateFlow<Int>
        get() = _userIdp
    private val _userIdp =
        MutableStateFlow(0)

    val userEmail: StateFlow<String>
        get() = _userEmail
    private val _userEmail =
        MutableStateFlow("")

    val isLoading: StateFlow<Boolean>
        get() = _isLoading
    private val _isLoading =
        MutableStateFlow(false)

    val profileImage: StateFlow<String?>
        get() = _profileImage
    private val _profileImage =
        MutableStateFlow<String?>(null)

    init {
        viewModelScope.launch {
            userInfoManager = UserInfoManager(context.UserInfoDataStore)
            userTicketManager = UserTicketManager(context.UserTicketStore)
            _userName.emit(userInfoManager.userNameFlow.first() ?: "")
            _starName.emit(userInfoManager.favoriteStarNameFlow.first() ?: "")
            _userEmail.emit(userInfoManager.userMailFlow.first() ?: "")
            _userIdp.emit(userInfoManager.userIdpFlow.first() ?: 0)
            _profileImage.emit(userInfoManager.userProfileImageFlow.first() ?: "")
        }
    }

    fun postLogout(result: () -> Unit) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            kotlin.runCatching {
                userLocalToken.value?.token?.let { myPageRepository.postLogout(it) }
            }.onSuccess {
                result()
                loadingHelper.hideProgress()
            }.onFailure {
                loadingHelper.hideProgress()
            }
        }
    }

    fun postUserProfile(image: File) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            _isLoading.emit(true)
            userLocalToken.value?.token?.let { token ->
                context.userIdStore.data.collect { id ->
                    kotlin.runCatching {
                        myPageRepository.postUserProfile(token, id.userId, image)
                    }.onSuccess {
                        _profileImage.emit(it.data?.image)
                        _isLoading.emit(false)
                        userInfoManager.setUserProfileImage(
                            userProfileImage = it.data?.image ?: ""
                        )
                        loadingHelper.hideProgress()
                    }.onFailure {
                        _isLoading.emit(false)
                        loadingHelper.hideProgress()
                    }
                }
            }
        }
    }
}