package com.trotfan.trot.ui.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.BuildConfig
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.model.MainPopups
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.model.VoteTicket
import com.trotfan.trot.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    application: Application
) : AndroidViewModel(application) {
    private val context = getApplication<Application>()

    private val _mainPopups = MutableStateFlow<MainPopups?>(null)
    val mainPopups: StateFlow<MainPopups?>
        get() = _mainPopups

    var votingCompleteState = MutableStateFlow(0)
    var voteStar = MutableStateFlow<VoteMainStar?>(null)
    var voteId = MutableStateFlow(0)

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

    init {
        getMainPopups()
    }

    private fun getMainPopups() {
        viewModelScope.launch {
            context.userTokenStore.data.collect {
                kotlin.runCatching {
                    repository.getMainPopups(it.accessToken)
                }.onSuccess {
                    _mainPopups.emit(it.data)
                    it.data.let { mainPopups ->
                        mainPopups?.update?.version?.let { version ->
                            if (version.replace(".", "")
                                    .toInt() > BuildConfig.VERSION_NAME.replace(".", "")
                                    .replace("_dev", "")
                                    .replace("_qa","")
                                    .toInt()
                            ) {
                                updateState.emit(true)
                            } else {
                                autoVoteStatus.emit(it.data?.autoVote?.is_available == true)
                                feverStatus.emit(it.data?.is_rewarded == true)

                                if (it.data?.layers != null) {
                                    rollingState.emit(true)
                                }
                            }
                        }
                    }
                }.onFailure {
                    Log.d("HomeViewModel", it.message.toString())
                }
            }
        }
    }

    fun postVoteTicket(voteId: Int, starId: Int, quantity: Long) {
        viewModelScope.launch {
            context.userTokenStore.data.collect {
                kotlin.runCatching {
                    repository.postVoteTicket(VoteTicket(voteId, starId, quantity), it.accessToken)
                }.onSuccess {
                    votingCompleteState.emit(1)
                    Log.d("HomeViewModel", it.toString())
                }.onFailure {
                    votingCompleteState.emit(2)
                    Log.d("HomeViewModel", it.message.toString())
                }
            }
        }
    }
}