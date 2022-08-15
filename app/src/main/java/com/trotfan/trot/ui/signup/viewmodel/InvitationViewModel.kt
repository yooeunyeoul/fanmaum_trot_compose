package com.trotfan.trot.ui.signup.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationViewModel @Inject constructor(
    private val repository: SignUpRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()
    val completeStatus: StateFlow<Boolean>
        get() = _completeStatus
    private val _completeStatus = MutableStateFlow(false)

    fun postInviteCode(inviteCode: String) {
        viewModelScope.launch {
            context.userIdStore.data.collect {
                kotlin.runCatching {
                    repository.updateUser(
                        userid = it.userId.toString(),
                        redeemCode = inviteCode
                    )
                }.onSuccess {
                    _completeStatus.emit(true)
                }
            }
        }
    }
}