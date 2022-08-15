package com.trotfan.trot.ui.invitation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.repository.InvitationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationViewModel @Inject constructor(
    private val repository: InvitationRepository
) : ViewModel() {
    val inviteCode = MutableStateFlow<String?>(null)

    suspend fun setInviteCode(code: String) {
        inviteCode.emit(code)
    }

    fun postInviteCode(inviteCode: String) {
        viewModelScope.launch {
//            repository.postInviteCode(inviteCode)
        }
    }
}