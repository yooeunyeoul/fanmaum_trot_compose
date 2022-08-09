package com.trotfan.trot.viewmodel

import androidx.lifecycle.ViewModel
import com.trotfan.trot.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class InvitationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    val inviteCode = MutableStateFlow<String?>(null)

    suspend fun setInviteCode(code: String) {
        inviteCode.emit(code)
    }
}