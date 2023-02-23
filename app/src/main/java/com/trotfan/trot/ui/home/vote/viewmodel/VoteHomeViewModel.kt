package com.trotfan.trot.ui.home.vote.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.trotfan.trot.BaseApplication
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.datastore.*
import com.trotfan.trot.model.FavoriteStarInfo
import com.trotfan.trot.model.Tickets
import com.trotfan.trot.model.VoteData
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.VoteRepository
import com.trotfan.trot.ui.BaseViewModel
import com.trotfan.trot.ui.utils.getTime
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

enum class VoteStatus(
) {
    VoteEnd, Available
}

enum class Gender {
    MEN, WOMEN
}

@HiltViewModel
class VoteHomeViewModel @Inject constructor(
    private val repository: VoteRepository,
    application: Application,
    private val loadingHelper: LoadingHelper
) : BaseViewModel(application) {


    lateinit var mBoardSocket: Socket
    lateinit var mRankSocket: Socket

    lateinit var userInfoManager: UserInfoManager
    lateinit var voteMainManager: VoteMainManager
    lateinit var userTicketManager: UserTicketManager

    private val context = getApplication<Application>()

    val voteStatus: StateFlow<VoteStatus>
        get() = _voteStatus
    private val _voteStatus =
        MutableStateFlow(VoteStatus.Available)

    val menHashMap: StateFlow<LinkedHashMap<Int?, VoteMainStar>>
        get() = _menHashMap
    private val _menHashMap =
        MutableStateFlow(LinkedHashMap<Int?, VoteMainStar>())

    val womenHashMap: StateFlow<LinkedHashMap<Int?, VoteMainStar>>
        get() = _womenHashMap
    private val _womenHashMap =
        MutableStateFlow(LinkedHashMap<Int?, VoteMainStar>())
    val voteId: StateFlow<Int>
        get() = _voteId
    private val _voteId =
        MutableStateFlow(0)

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

    private val _ticks =
        MutableStateFlow(0)
    val ticks = _ticks.asStateFlow()

//    private val _tickets =
//        MutableStateFlow<Tickets?>(null)
//    val tickets = _tickets.asStateFlow()

    private val dummyData = VoteData(quantity = -1, star_name = "null", user_name = "null")

    val gender: StateFlow<Gender>
        get() = _gender
    private val _gender =
        MutableStateFlow(Gender.WOMEN)

    var currentBoardPage = 0

    var today = 1000L

    var socketUrl = "http://3.34.129.230:3000"


    init {
        viewModelScope.launch {
            getVoteList()
            userInfoManager = UserInfoManager(context.UserInfoDataStore)
            voteMainManager = VoteMainManager(context.VoteMainDataStore)
            userTicketManager = UserTicketManager(context.UserTicketStore)
            connectBoardSocket()
            connectRankSocket()
            getStarRank()
            tickRemainingTime()
            observeGender()
        }
    }

    private fun observeGender() {
        viewModelScope.launch {
            userInfoManager.favoriteStarGenderFlow.collectLatest {
                _gender.emit(it ?: Gender.WOMEN)
            }
        }
    }

    private fun getVoteList() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            val response = repository.getVote()
            val voteMainStars = response?.data?.voteMainStars
            val menHashMap =
                voteMainStars?.men?.associate { it.id to it } as LinkedHashMap
            val womenHashMap =
                voteMainStars?.women?.associate { it.id to it } as LinkedHashMap
            _voteId.emit(response.data.id)
            _menHashMap.emit(menHashMap)
            _womenHashMap.emit(womenHashMap)
            loadingHelper.hideProgress()
        }
    }

    private fun getStarRank() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            userInfoManager?.favoriteStarIdFlow?.collectLatest {
                val response = repository.getStarRank(it ?: 2)
                when (response.result.code) {
                    ResultCodeStatus.SuccessWithNoData.code -> {
                        _favoriteStar.emit(FavoriteStarInfo())
                    }
                    ResultCodeStatus.SuccessWithData.code -> {
                        _favoriteStar.emit(response.data ?: FavoriteStarInfo())
                    }
                }
                loadingHelper.hideProgress()
            }
        }
    }

    fun getVoteTickets(purchaseHelper: PurchaseHelper) {
        viewModelScope.launch {
            loadingHelper.showProgress()
            context.userIdStore.data.collect {
                kotlin.runCatching {
                    repository.getVoteTickets(
                        userId = it.userId,
                        token = userLocalToken.value?.token ?: ""
                    )

                }.onSuccess { response ->
                    when (response.result.code) {
                        ResultCodeStatus.SuccessWithData.code -> {
                            userTicketManager.storeUserTicket(
                                response.data?.unlimited ?: 0,
                                response.data?.limited ?: 0
                            )
//                            _tickets.emit(response.data)
                            purchaseHelper.closeApiCall()
                        }
                        ResultCodeStatus.Fail.code -> {
                            Log.e("VoteHomeViewModel", response.result.message.toString())
                        }
                    }
                    loadingHelper.hideProgress()
                }.onFailure {
                    loadingHelper.hideProgress()
                    Log.e("VoteHomeViewModel", it.message.toString())
                }
            }
        }
    }

    private fun connectRankSocket() {
        viewModelScope.launch {
            val options = IO.Options()
            options.transports = arrayOf(WebSocket.NAME)
            mRankSocket = IO.socket("${socketUrl}/rank", options)
            mRankSocket.on(Socket.EVENT_CONNECT) {
                Log.e("CONNECT", "연결됐다!!!")
            }
            mRankSocket.on(Socket.EVENT_CONNECT_ERROR) {
//                Log.e("CONNECT ERROR", "에러났다" + it.get(0).toString())
            }
            mRankSocket.on("rank men status") { result ->
//                Log.e("rank men status", "rank men status" + result[0].toString())
                updateRankList(result, Gender.MEN)

            }
            mRankSocket.on("rank women status") { result ->
//                Log.e("rank women status", "rank women status" + it.get(0).toString())
                updateRankList(result, Gender.WOMEN)
            }
            mRankSocket.connect()

        }
    }

    private fun updateRankList(result: Array<Any>, gender: Gender) {
        viewModelScope.launch {
            if (result.isNotEmpty()) {
                val starData = result[0] as JSONObject
                val starList = starData.get("data") as JSONArray
                var starHashMap: HashMap<Int?, VoteMainStar>? = null
                starHashMap = when (gender) {
                    Gender.MEN -> {
                        _menHashMap.value
                    }
                    Gender.WOMEN -> {
                        _womenHashMap.value
                    }
                }
                for (i in 0 until starList.length()) {
                    val jsonObject = starList.get(i).toString()
                    val data = Gson().fromJson(jsonObject, VoteMainStar::class.java)
                    starHashMap[data.id]?.run {
                        this.rank = data.rank
                        if ((this.votes ?: 0) < (data.votes ?: 0)) {
                            this.votes = data.votes
                        }
                    }
                    if (_favoriteStar.value.id == data.id) {
                        _favoriteStar.value.rank?.daily = data.rank ?: 0
                    }
                }
                when (gender) {
                    Gender.MEN -> {
                        _menHashMap.emit(starHashMap)
                    }
                    Gender.WOMEN -> {
                        _womenHashMap.emit(starHashMap)
                    }
                }
            }
        }
    }


    fun connectBoardSocket() {
        viewModelScope.launch {
            val options = IO.Options()
            options.transports = arrayOf(WebSocket.NAME)
            mBoardSocket = IO.socket("${socketUrl}/board", options)

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
//                    Log.e("status", status)
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
            val voteData = _voteDataList.value
            voteData.addAll(list)
            _voteDataList.emit(voteData)
            _voteDataListCount.emit(voteData.count())
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

                }

            }
        }
    }

    fun saveTooltipState(isShowToolTIp: Boolean) {
        viewModelScope.launch {
            voteMainManager?.storeTooltipState(
                isShowToolTIp
            )
        }
    }

    fun saveScrollTooltipState(isShowToolTIp: Boolean) {
        viewModelScope.launch {
            voteMainManager?.storeScrollTooltipState(
                isShowToolTIp
            )
        }
    }

    fun clearDataAndAddDummyData() {
        viewModelScope.launch {
            val voteData = _voteDataList.value
            voteData.clear()
            if (voteData.lastOrNull() != dummyData) {
                voteData.add(dummyData)
                _voteDataList.emit(voteData)
                _voteDataListCount.emit(voteData.count())
            }
        }

    }

    fun addMyTicketsToBoard(votes: Int, starName: String) {
        viewModelScope.launch {
            _voteDataList.value.add(
                if (currentBoardPage + 3 >= _voteDataList.value.size) _voteDataList.value.size else currentBoardPage + 3,
                VoteData(
                    quantity = votes,
                    star_name = starName,
                    user_name = userInfoManager.userNameFlow.first() ?: ""
                )
            )
            _voteDataListCount.emit(_voteDataList.value.count())
        }

    }

    fun refreshLocalVoteList(votes: Int, star: VoteMainStar?) {
        viewModelScope.launch {
            _menHashMap.value[star?.id]?.let {
                it.votes = it.votes?.plus(votes)
            }
            _womenHashMap.value[star?.id]?.let {
                it.votes = it.votes?.plus(votes)
            }
        }
    }

    private fun tickRemainingTime() {
        viewModelScope.launch(Dispatchers.IO) {
            _ticks.value = getTime(targetSecond = 0, application = context as BaseApplication)
            while (true) {
                delay(1000)
                val tick = _ticks.value.minus(1)
                if (tick < 0) {
                    _ticks.value =
                        getTime(targetSecond = 0, application = context as BaseApplication)
                } else {
                    _ticks.value = tick
                }
            }
        }
    }

}