package com.trotfan.trot.ui.home.vote.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.model.Top3Benefit
import com.trotfan.trot.repository.VoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class VoteStatus(
) {
    VoteEnd, VoteStar, NotVoteForFiveTimes

}


@HiltViewModel
class VoteHomeViewModel @Inject constructor(
    private val repository: VoteRepository,
    application: Application
) : AndroidViewModel(application) {

    lateinit var mSocket: Socket

    val voteStatus: StateFlow<VoteStatus>
        get() = _voteStatus
    private val _voteStatus =
        MutableStateFlow(VoteStatus.NotVoteForFiveTimes)

    val top3Info: StateFlow<Top3Benefit?>
        get() = _top3Info
    private val _top3Info =
        MutableStateFlow(Top3Benefit())

    init {
        getVoteList()
    }

    fun getVoteList() {
        viewModelScope.launch {
            val response = repository.getVoteList()
            Log.e("response",response.data.toString())
            _top3Info.emit(response.data.first())
        }
    }


    fun connectSocket() {
        viewModelScope.launch {
            val options = IO.Options()
            options.transports = arrayOf(WebSocket.NAME)
            mSocket = IO.socket("http://13.125.232.75:3000")

            mSocket.connect()
            mSocket.on(Socket.EVENT_CONNECT) {
                Log.e("CONNECT", "연결됐다!!!")
            }
            mSocket.on(Socket.EVENT_CONNECT_ERROR) {
                Log.e("CONNECT ERROR", "에러났다" + it.get(0).toString())
            }
            mSocket.on("vote status board") {
                Log.e("vote status board", "이벤트" + it.get(0).toString())
            }

        }

    }


    override fun onCleared() {
        super.onCleared()
        if (mSocket.connected()) {
            Log.e("disconnect", "disconnect!!!")
            mSocket.disconnect()
        }
    }

}