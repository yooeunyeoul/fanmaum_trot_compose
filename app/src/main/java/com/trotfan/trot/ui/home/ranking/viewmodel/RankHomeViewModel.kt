package com.trotfan.trot.ui.home.ranking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.*
import com.trotfan.trot.model.FavoriteStarInfo
import com.trotfan.trot.model.MonthStarRank
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.RankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

enum class MonthlyRankViewType {
    IMAGE, NUMBER, NONE
}

@HiltViewModel
class RankHomeViewModel @Inject constructor(
    private val repository: RankRepository,
    application: Application
) : AndroidViewModel(application) {


    var userInfoManager: UserInfoManager
    var rankMainManager: RankMainManager

    private val context = getApplication<Application>()

    val pairMenRankList: StateFlow<Pair<MonthlyRankViewType, List<MonthStarRank>?>>
        get() = _pairMenRankList
    private val _pairMenRankList =
        MutableStateFlow(
            Pair<MonthlyRankViewType, List<MonthStarRank>?>(
                first = MonthlyRankViewType.NONE,
                listOf()
            )
        )

    val pairWomenRankList: StateFlow<Pair<MonthlyRankViewType, List<MonthStarRank>?>>
        get() = _pairWomenRankList
    private val _pairWomenRankList =
        MutableStateFlow(
            Pair<MonthlyRankViewType, List<MonthStarRank>?>(
                first = MonthlyRankViewType.NONE,
                listOf()
            )
        )

    val favoriteStar: StateFlow<FavoriteStarInfo>
        get() = _favoriteStar
    private val _favoriteStar =
        MutableStateFlow(FavoriteStarInfo())


    init {
        userInfoManager = UserInfoManager(context.FavoriteStarDataStore)
        rankMainManager = RankMainManager(context.RankMainDataStore)
        getMonthStarRank()
    }

    fun saveScrollTooltipState(isShowToolTIp: Boolean) {
        viewModelScope.launch {
            rankMainManager.storeScrollTooltipState(
                isShowToolTIp
            )
        }
    }

    private fun getMonthStarRank() {
        viewModelScope.launch {
            val response = repository.getMonthStarRank(LocalDate.now().month.value)
            when (response.result.code) {
                ResultCodeStatus.Success.code -> {
                    val result = response.data
                    val menList = result?.men?.subList(0, 4)
                    val originMenCount = menList?.count()
                    val distinctMen = menList?.distinctBy { it.rank }?.filter { it.rank != 0 }
                    val distinctMenCount = distinctMen?.count()
                    if (originMenCount == distinctMenCount) {
                        if (distinctMen?.count { (it.rank ?: 0) < 4 } == 3) {
                            Log.e("IMAGE VIEW", "남자 이미지")
                            _pairMenRankList.emit(
                                Pair(
                                    first = MonthlyRankViewType.IMAGE,
                                    second = result?.men
                                )
                            )
                        } else {
                            Log.e("NUMBER VIEW", "남자 넘버")
                            _pairMenRankList.emit(
                                Pair(
                                    first = MonthlyRankViewType.NUMBER,
                                    second = result?.men
                                )
                            )
                        }

                    } else {
                        Log.e("NUMBER VIEW", "남자 넘버")
                        _pairMenRankList.emit(
                            Pair(
                                first = MonthlyRankViewType.NUMBER,
                                second = result?.men
                            )
                        )
                    }
                    val womenList = result?.women?.subList(0, 4)
                    val originWomenCount = womenList?.count()
                    val distinctWomen = womenList?.distinctBy { it.rank }
                    val distinctWomenCount = distinctWomen?.count()
                    if (originWomenCount == distinctWomenCount) {

                        if (distinctMen?.count { (it.rank ?: 0) < 4 } == 3) {
                            Log.e("IMAGE VIEW", "여자 이미지")
                            _pairWomenRankList.emit(
                                Pair(
                                    first = MonthlyRankViewType.IMAGE,
                                    second = result?.women
                                )
                            )
                        } else {
                            Log.e("NUMBER VIEW", "여자 넘버")
                            _pairWomenRankList.emit(
                                Pair(
                                    first = MonthlyRankViewType.NUMBER,
                                    second = result?.women
                                )
                            )
                        }
                    } else {
                        Log.e("NUMBER VIEW", "여자 넘버")
                        _pairWomenRankList.emit(
                            Pair(
                                first = MonthlyRankViewType.NUMBER,
                                second = result?.women
                            )
                        )
                    }
                }
            }
        }
    }
}