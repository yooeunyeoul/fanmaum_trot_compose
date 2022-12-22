package com.trotfan.trot.ui.home.ranking.viewmodel

import android.app.Application
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.trotfan.trot.datastore.*
import com.trotfan.trot.model.FavoriteStarInfo
import com.trotfan.trot.model.VoteMainStar
import com.trotfan.trot.repository.VoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankHomeViewModel @Inject constructor(
    private val repository: VoteRepository,
    application: Application
) : AndroidViewModel(application) {


    var userInfoManager: UserInfoManager
    var rankMainManager: RankMainManager

    private val context = getApplication<Application>()

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
        rankMainManager = RankMainManager(context.RankMainDataStore)
    }

    fun saveScrollTooltipState(isShowToolTIp: Boolean) {
        viewModelScope.launch {
            rankMainManager?.storeScrollTooltipState(
                isShowToolTIp
            )
        }
    }
}