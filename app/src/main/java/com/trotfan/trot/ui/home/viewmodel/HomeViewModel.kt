package com.trotfan.trot.ui.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.ironsource.mediationsdk.IronSource
import com.trotfan.trot.BuildConfig
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.datastore.*
import com.trotfan.trot.model.*
import com.trotfan.trot.repository.HomeRepository
import com.trotfan.trot.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val loadingHelper: LoadingHelper,
    application: Application
) : BaseViewModel(application) {
    private val context = getApplication<Application>()

    private val _mainPopups = MutableStateFlow<MainPopups?>(null)
    val mainPopups: StateFlow<MainPopups?>
        get() = _mainPopups

    var votingCompleteState = MutableStateFlow(0)
    var voteStar = MutableStateFlow<VoteMainStar?>(null)
    var voteId = MutableStateFlow(0)
    var voteTickets = MutableStateFlow(Tickets(0, 0))
    var starShareState = MutableStateFlow(false)
    var isMyStar = MutableStateFlow(false)

    var myVoteCnt = MutableStateFlow(1000L)
    var voteCnt = MutableStateFlow(TextFieldValue(""))

    var updateState = MutableStateFlow(false)
        private set

    var rollingState = MutableStateFlow(false)
        private set

    var feverStatus = MutableStateFlow(false)
        private set

    var autoVoteStatus = MutableStateFlow(false)
        private set

    val userInfoManager = UserInfoManager(context.UserInfoDataStore)
    var userTotalUsedVote: Long = 0

    init {
        getMainPopups()
        settingIronSourceUserId()
        viewModelScope.launch {
            context.UserInfoDataStore.data.collect {
                userTotalUsedVote = it[UserInfoManager.USER_TOTAL_USED_VOTE] ?: 0
            }
        }
    }

    fun settingIronSourceUserId() {
        viewModelScope.launch {
            context.userIdStore.data.collect {
                IronSource.setUserId(it.userId.toString())
            }
        }
    }

    fun getMainPopups() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userTokenStore.data.collect {
                kotlin.runCatching {
                    repository.getMainPopups(it.token)
                }.onSuccess {
                    _mainPopups.emit(it.data)
                    it.data.let { mainPopups ->
                        mainPopups?.update?.version?.let { version ->
                            val versionManager = AppVersionManager(context.AppVersionManager)
                            versionManager.setStoreVersion(version)
                            if (version.replace(".", "")
                                    .toInt() > BuildConfig.VERSION_NAME.replace(".", "")
                                    .replace("_dev", "")
                                    .replace("_qa", "")
                                    .toInt()
                            ) {
                                updateState.emit(true)
                            } else {
                                autoVoteStatus.emit(it.data?.auto_vote?.is_voted == true)
                                feverStatus.emit(it.data?.is_rewarded == true)

                                context.dateManager.data.collect { date ->
                                    if (date.rollingDate != LocalDate.now()
                                            .toString() && mainPopups.layers != null
                                    ) {
                                        rollingState.emit(true)
                                    }
                                }
                            }
                        }
                    }
                    loadingHelper.hideProgress()
                }.onFailure {
                    Log.d("HomeViewModel", it.message.toString())
                    loadingHelper.hideProgress()
                }
            }
        }
    }

    fun postVoteTicket(voteId: Int, starId: Int, quantity: Long) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            kotlin.runCatching {
                repository.postVoteTicket(
                    VoteTicket(voteId, starId, quantity),
                    userLocalToken.value?.token ?: ""
                )
            }.onSuccess {
                votingCompleteState.emit(1)
                userInfoManager.updateUserTotalUsedVote(userTotalUsedVote.plus(quantity))
                loadingHelper.hideProgress()
            }.onFailure {
                votingCompleteState.emit(2)
                Log.d("HomeViewModel", it.message.toString())
                loadingHelper.hideProgress()
            }
        }
    }

    fun postShareStar() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.postShareStar(userLocalToken.value?.token ?: "")
            }.onSuccess {

            }.onFailure {

            }
        }
    }
}