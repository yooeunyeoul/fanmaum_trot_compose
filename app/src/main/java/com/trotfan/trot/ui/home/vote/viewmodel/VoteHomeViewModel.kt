package com.trotfan.trot.ui.home.vote.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.trotfan.trot.datastore.FavoriteStarDataStore
import com.trotfan.trot.datastore.FavoriteStarManager
import com.trotfan.trot.datastore.VoteMainManager
import com.trotfan.trot.model.*
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.VoteRepository
import com.trotfan.trot.ui.utils.getTime
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

enum class VoteStatus(
) {
    VoteEnd, Available, NotVoteForFiveTimes

}


@HiltViewModel
class VoteHomeViewModel @Inject constructor(
    private val repository: VoteRepository,
    application: Application
) : AndroidViewModel(application) {

    lateinit var mBoardSocket: Socket
    lateinit var mRankSocket: Socket

    var favoriteStarManager: FavoriteStarManager
    var voteMainManager: VoteMainManager

    private val context = getApplication<Application>()

    val voteStatus: StateFlow<VoteStatus>
        get() = _voteStatus
    private val _voteStatus =
        MutableStateFlow(VoteStatus.Available)

    val voteId: StateFlow<Int>
        get() = _voteId
    private val _voteId =
        MutableStateFlow(0)

    val stars: StateFlow<VoteMainStars>
        get() = _stars
    private val _stars =
        MutableStateFlow(VoteMainStars())

    val favoriteStar: StateFlow<FavoriteStarInfo>
        get() = _favoriteStar
    private val _favoriteStar =
        MutableStateFlow(FavoriteStarInfo())


    val voteDataList: StateFlow<ArrayList<VoteData>>
        get() = _voteDataList
    private val _voteDataList =
        MutableStateFlow(arrayListOf(VoteData(quantity = -1, "null", user_name = "null")))

    val voteDataListCount: StateFlow<Int>
        get() = _voteDataListCount
    private val _voteDataListCount =
        MutableStateFlow(1)

    var currentPage = -1

    val dummyData = VoteData(quantity = -1, star_name = "null", user_name = "null")


    init {
        getVoteList()
        favoriteStarManager = FavoriteStarManager(context.FavoriteStarDataStore)
        voteMainManager = VoteMainManager(context.FavoriteStarDataStore)
        connectBoardSocket()
        connectRankSocket()
        getStarRank()
        getTime()
    }

    private fun getVoteList() {
        viewModelScope.launch {
            val response = repository.getVote()
            _stars.emit(response?.data?.voteMainStars ?: VoteMainStars())
            Log.d("TOP3Benefit", response?.data?.voteMainStars.toString())
        }
    }

    private fun getStarRank() {
        viewModelScope.launch {
            favoriteStarManager.favoriteStarIdFlow.collectLatest {
                val response = repository.getStarRank(it ?: 2)
                when (response.result.code) {
                    ResultCodeStatus.StarRankNoResult.code -> {
                        _favoriteStar.emit(FavoriteStarInfo())
                    }
                    ResultCodeStatus.Success.code -> {
                        _favoriteStar.emit(response.data ?: FavoriteStarInfo())
                    }
                }


            }
        }
    }

    private fun connectRankSocket() {
        viewModelScope.launch {
            val options = IO.Options()
            options.transports = arrayOf(WebSocket.NAME)
            mRankSocket = IO.socket("https://socket.dev.fanmaum.ap.ngrok.io/rank", options)
            mRankSocket.on(Socket.EVENT_CONNECT) {
                Log.e("CONNECT", "연결됐다!!!")
            }
            mRankSocket.on(Socket.EVENT_CONNECT_ERROR) {
//                Log.e("CONNECT ERROR", "에러났다" + it.get(0).toString())
            }
            mRankSocket.on("rank men status") {
                Log.e("rank men status", "rank men status" + it.get(0).toString())
            }
            mRankSocket.on("rank women status") {
                Log.e("rank women status", "rank women status" + it.get(0).toString())
            }
            mRankSocket.connect()

        }
    }

    fun connectBoardSocket() {
        viewModelScope.launch {
            val options = IO.Options()
            options.transports = arrayOf(WebSocket.NAME)
            mBoardSocket = IO.socket("https://socket.dev.fanmaum.ap.ngrok.io/board", options)

            mBoardSocket.on(Socket.EVENT_CONNECT) {
                Log.e("CONNECT", "연결됐다!!!")
            }
            mBoardSocket.on(Socket.EVENT_CONNECT_ERROR) {
//                Log.e("CONNECT ERROR", "에러났다" + it.get(0).toString())
            }
            mBoardSocket.on("vote status board") {
                if (it.isNotEmpty()) {
                    val list = arrayListOf<VoteData>()
                    val voteStatusData = it[0] as JSONObject
                    val status = voteStatusData.get("vote_status").toString()
                    Log.e("status", status)
                    changeVoteStatus(status)
                    val voteDataList = voteStatusData.get("data") as JSONArray
//                    Log.e("voteDataList", voteDataList.toString())
                    for (i in 0 until voteDataList.length()) {
                        val jsonObject = voteDataList.get(i).toString()
//                        Log.e("JSONOBEJECT",jsonObject.toString())
                        val data = Gson().fromJson(jsonObject, VoteData::class.java)
//                        Log.e("data",data.toString())
                        list.add(data)
//
                    }
                    sendEvent(list)
                }

//                Log.e("vote status board", "이벤트" + it[0].toString())


            }
            mBoardSocket.connect()
        }

    }

    private fun sendEvent(list: ArrayList<VoteData>) {
        viewModelScope.launch {
            val oldList = _voteDataList.value
            oldList.addAll(list)
            _voteDataList.emit(oldList)
            _voteDataListCount.emit(oldList.count())
        }

    }


    override fun onCleared() {
        super.onCleared()
        if (mBoardSocket.connected()) {
            mBoardSocket.disconnect()
        }
        if (mRankSocket.connected()) {
            mRankSocket.disconnect()
        }

    }

    fun changeVoteStatus(status: String) {
        viewModelScope.launch {
            when (status) {
                "available" -> {
                    _voteStatus.emit(VoteStatus.Available)
                }

                "counting" -> {
                    _voteStatus.emit(VoteStatus.VoteEnd)
                }
                "unavailable" -> {
//                    val oldList = _voteDataList.value
//                    oldList.add(VoteData(quantity = -1, star_name = "", user_name = ""))
//                    _voteDataList.emit(oldList)
//                    _voteDataListCount.emit(oldList.count())
                    val oldList = _voteDataList.value
                    if (!oldList.contains(dummyData)) {
                        oldList.add(dummyData)
                    }
                    _voteDataList.emit(oldList)
                    _voteDataListCount.emit(oldList.count())
                    _voteStatus.emit(VoteStatus.Available)
                }

            }
        }
    }

    fun saveTooltipState(isShowToolTIp: Boolean) {
        viewModelScope.launch {
            voteMainManager.storeTooltipState(
                isShowToolTIp
            )

        }

    }

}