package com.trotfan.trot.ui.home.mypage.invite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.model.InviteInfo
import com.trotfan.trot.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendInviteViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val loadingHelper: LoadingHelper,
    application: Application
) : AndroidViewModel(application) {
    private val context = getApplication<Application>()
    val inviteInfo: StateFlow<InviteInfo?>
        get() = _inviteInfo
    private val _inviteInfo = MutableStateFlow<InviteInfo?>(null)

    init {
        getUserInviteInfo()
    }

    fun getUserInviteInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                loadingHelper.showProgress()
                myPageRepository.getUserInviteInfo(
                    userToken = context.userTokenStore.data.first().token,
                    userId = context.userIdStore.data.first().userId
                )
            }.onSuccess {
                _inviteInfo.emit(it.data)
                loadingHelper.hideProgress()
            }.onFailure {
                loadingHelper.hideProgress()
            }
        }
    }
}