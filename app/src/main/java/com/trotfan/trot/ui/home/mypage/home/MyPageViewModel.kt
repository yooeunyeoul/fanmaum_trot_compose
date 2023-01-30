package com.trotfan.trot.ui.home.mypage.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.UserInfoDataStore
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.datastore.UserTicketManager
import com.trotfan.trot.datastore.UserTicketStore
import com.trotfan.trot.datastore.userIdStore
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


    val unlimitedTicket: StateFlow<Long>
        get() = _unlimitedTicket
    private val _unlimitedTicket =
        MutableStateFlow(0L)

    val todayTicket: StateFlow<Long>
        get() = _todayTicket
    private val _todayTicket =
        MutableStateFlow(0L)

    val isLoading: StateFlow<Boolean>
        get() = _isLoading
    private val _isLoading =
        MutableStateFlow(false)

    init {
        viewModelScope.launch {
            userInfoManager = UserInfoManager(context.UserInfoDataStore)
            userTicketManager = UserTicketManager(context.UserTicketStore)
            _userName.emit(userInfoManager.userNameFlow.first() ?: "")
            _starName.emit(userInfoManager.favoriteStarNameFlow.first() ?: "")
            _unlimitedTicket.emit(userTicketManager.expiredUnlimited.first() ?: 0)
            _todayTicket.emit(userTicketManager.expiredToday.first() ?: 0)
            _userEmail.emit(userInfoManager.userMailFlow.first() ?: "")
            _userIdp.emit(userInfoManager.userIdpFlow.first() ?: 0)
        }
    }

    fun postLogout(result: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                userLocalToken.value?.token?.let { myPageRepository.postLogout(it) }
            }.onSuccess {
                result()
            }.onFailure {

            }
        }
    }

    fun postUserProfile(image: File) {
        viewModelScope.launch {
            kotlin.runCatching {
                _isLoading.emit(true)
                userLocalToken.value?.token?.let { token ->
                    context.userIdStore.data.collect { id ->
                        myPageRepository.postUserProfile(token, id.userId, image)
                    }
                }
            }.onSuccess {
                _isLoading.emit(false)
                Log.d("onSuccess", it.toString())
            }.onFailure {
                _isLoading.emit(false)
                Log.d("onFailure", it.toString())
            }
        }
    }
}