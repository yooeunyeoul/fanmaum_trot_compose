package com.trotfan.trot.ui.home.ranking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.trotfan.trot.datastore.FavoriteStarDataStore
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.datastore.VoteMainManager
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.model.Expired
import com.trotfan.trot.model.FavoriteStarInfo
import com.trotfan.trot.model.VoteData
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.VoteRepository
import com.trotfan.trot.ui.utils.getTime
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
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
class RankHomeViewModel @Inject constructor(
    private val repository: VoteRepository,
    application: Application
) : AndroidViewModel(application) {

    lateinit var mBoardSocket: Socket
    lateinit var mRankSocket: Socket

    var userInfoManager: UserInfoManager
    var voteMainManager: VoteMainManager

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





    init {
        userInfoManager = UserInfoManager(context.FavoriteStarDataStore)
        voteMainManager = VoteMainManager(context.FavoriteStarDataStore)
    }
}