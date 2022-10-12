package com.trotfan.trot.ui.home.vote.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.model.*
import com.trotfan.trot.repository.AuthRepository
import com.trotfan.trot.repository.VoteRepository
import com.trotfan.trot.ui.signup.viewmodel.CertificationNumberCheckStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

enum class VoteStatus(
) {
    VoteEnd, VoteStar, NotVoteForFiveTimes

}


@HiltViewModel
class VoteHomeViewModel @Inject constructor(
    private val repository: VoteRepository,
    application: Application
) : AndroidViewModel(application) {

    val voteStatus: StateFlow<VoteStatus>
        get() = _voteStatus
    private val _voteStatus =
        MutableStateFlow(VoteStatus.NotVoteForFiveTimes)

}