package com.trotfan.trot.ui.home.vote.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.trotfan.trot.datastore.FavoriteStarDataStore
import com.trotfan.trot.datastore.FavoriteStarManager
import com.trotfan.trot.model.Top3Benefit
import com.trotfan.trot.model.VoteStatusBoard
import com.trotfan.trot.repository.VoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
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

    var favoriteStarManager: FavoriteStarManager

    private val context = getApplication<Application>()

    val voteStatus: StateFlow<VoteStatus>
        get() = _voteStatus
    private val _voteStatus =
        MutableStateFlow(VoteStatus.VoteStar)

    val top3Info: StateFlow<Top3Benefit?>
        get() = _top3Info
    private val _top3Info =
        MutableStateFlow(Top3Benefit())

    val voteStatusBoardList: StateFlow<ArrayList<VoteStatusBoard>>
        get() = _voteStatusBoardList
    private val _voteStatusBoardList =
        MutableStateFlow(arrayListOf<VoteStatusBoard>())

    val voteStatusBoardListCount: StateFlow<Int>
        get() = _voteStatusBoardListCount
    private val _voteStatusBoardListCount =
        MutableStateFlow(0)

    private val sampleCount =
        MutableStateFlow(0)


    init {
        getVoteList()
        favoriteStarManager = FavoriteStarManager(context.FavoriteStarDataStore)
    }

    fun getVoteList() {
        viewModelScope.launch {
            val response = repository.getVoteList()
            Log.e("response", response.data.toString())
            _top3Info.emit(response.data.first())
        }
    }


    fun connectSocket() {
        viewModelScope.launch {
            val options = IO.Options()
            options.transports = arrayOf(WebSocket.NAME)
            mSocket = IO.socket("http://13.125.232.75:3000/", options)
            mSocket.on(Socket.EVENT_CONNECT) {
//                Log.e("CONNECT", "연결됐다!!!")
            }
            mSocket.on(Socket.EVENT_CONNECT_ERROR) {
//                Log.e("CONNECT ERROR", "에러났다" + it.get(0).toString())
//                val list = arrayListOf<VoteStatusBoard>()
////                val olderList = _voteStatusBoardList.value
//                list.addAll(
//                    arrayListOf(
//                        VoteStatusBoard(
//                            quantity = System.currentTimeMillis().toInt(),
//                            starName = "아무개",
//                            userName = "유저네임"
//                        ),
//                        VoteStatusBoard(
//                            quantity = System.currentTimeMillis().toInt(),
//                            starName = "아무개2",
//                            userName = "유저네임2"
//                        ),
//                        VoteStatusBoard(
//                            quantity = System.currentTimeMillis().toInt(),
//                            starName = "아무개3",
//                            userName = "유저네임3"
//                        )
//                    )
//                )
////                _voteStatusBoardList.value = olderList
////                _voteStatus.value = VoteStatus.VoteStar
//                sendEvent(list)

            }
            mSocket.on("vote status board") {
                if (it.isNotEmpty()) {
                    val list = arrayListOf<VoteStatusBoard>()
                    val jsonArray = it[0] as JSONArray
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.get(i).toString()
//                        Log.e("JSONOBEJECT",jsonObject.toString())
                        val data = Gson().fromJson(jsonObject, VoteStatusBoard::class.java)
//                        Log.e("data",data.toString())
                        list.add(data)
//
                    }
                    sendEvent(list)
                }

//                Log.e("vote status board", "이벤트" + it[0].toString())


            }
            mSocket.connect()

        }

    }

    private fun sendEvent(list: ArrayList<VoteStatusBoard>) {
        viewModelScope.launch {
            val oldList = _voteStatusBoardList.value
            oldList.addAll(list)
            _voteStatusBoardList.emit(oldList)
            _voteStatusBoardListCount.emit(oldList.count())
        }

    }


    override fun onCleared() {
        super.onCleared()
        if (mSocket.connected()) {
            Log.e("disconnect", "disconnect!!!")
            mSocket.disconnect()
        }
    }

    fun changeVoteStatus() {
        viewModelScope.launch {
            when (sampleCount.value) {
                0 -> {
                    _voteStatus.emit(VoteStatus.VoteStar)
                    sampleCount.value = 1
                }
                1 -> {
                    val oldList = _voteStatusBoardList.value
                    oldList.add(VoteStatusBoard(quantity = -1, star_name = "", user_name = ""))
                    _voteStatusBoardList.emit(oldList)
                    _voteStatusBoardListCount.emit(oldList.count())
                    _voteStatus.emit(VoteStatus.NotVoteForFiveTimes)
                    sampleCount.value = 2
                }
                2 -> {
                    _voteStatus.emit(VoteStatus.VoteEnd)
                    sampleCount.value = 0
                }

            }

        }
    }
}