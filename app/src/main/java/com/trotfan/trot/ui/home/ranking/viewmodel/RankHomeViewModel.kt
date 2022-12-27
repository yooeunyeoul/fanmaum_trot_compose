package com.trotfan.trot.ui.home.ranking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.FavoriteStarDataStore
import com.trotfan.trot.datastore.RankMainDataStore
import com.trotfan.trot.datastore.RankMainManager
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.model.Banner
import com.trotfan.trot.model.FavoriteStarInfo
import com.trotfan.trot.model.StarRanking
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.RankingHistoryRepository
import com.trotfan.trot.repository.VoteRepository
import com.trotfan.trot.ui.utils.convertStringToTime
import com.trotfan.trot.ui.utils.getTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

enum class MonthlyRankViewType {
    IMAGE, NUMBER, NONE
}

enum class RankRemainingStatus {
    VoteIng, VoteWaiting
}

enum class RankStatus {
    Available, UnAvailable
}

const val ApiNotRoad = -1004

@HiltViewModel
class RankHomeViewModel @Inject constructor(
    private val repository: RankingHistoryRepository,
    private val voteRepository: VoteRepository,
    application: Application
) : AndroidViewModel(application) {

    var userInfoManager: UserInfoManager
    var rankMainManager: RankMainManager

    private val context = getApplication<Application>()

    val pairMenRankList: StateFlow<Pair<MonthlyRankViewType, List<StarRanking>?>>
        get() = _pairMenRankList
    private val _pairMenRankList =
        MutableStateFlow(
            Pair<MonthlyRankViewType, List<StarRanking>?>(
                first = MonthlyRankViewType.NONE,
                listOf()
            )
        )

    val pairWomenRankList: StateFlow<Pair<MonthlyRankViewType, List<StarRanking>?>>
        get() = _pairWomenRankList
    private val _pairWomenRankList =
        MutableStateFlow(
            Pair<MonthlyRankViewType, List<StarRanking>?>(
                first = MonthlyRankViewType.NONE,
                listOf()
            )
        )

    val favoriteStar: StateFlow<FavoriteStarInfo>
        get() = _favoriteStar
    private val _favoriteStar =
        MutableStateFlow(FavoriteStarInfo())

    val banners: StateFlow<List<Banner>>
        get() = _banners
    private val _banners =
        MutableStateFlow(
            listOf<Banner>()
        )

    val rankRemainingStatus: StateFlow<Pair<String, RankRemainingStatus>>
        get() = _rankRemainingStatus
    private val _rankRemainingStatus =
        MutableStateFlow(Pair("", RankRemainingStatus.VoteIng))

    val rankStatus: StateFlow<RankStatus>
        get() = _rankStatus
    private val _rankStatus =
        MutableStateFlow(RankStatus.Available)

    var remain24HourTime: Int = 0
    var remainMonthlyVoteTime: Int = ApiNotRoad

    init {
        userInfoManager = UserInfoManager(context.FavoriteStarDataStore)
        rankMainManager = RankMainManager(context.RankMainDataStore)
        getMonthStarRank()
        getBanners()
        refreshRank()
        getVoteEndedTime()
        refreshVoteStatus()
    }

    private fun getVoteEndedTime() {
        viewModelScope.launch {
            val response = voteRepository.getVote()
            val milliSecond = convertStringToTime(response?.data?.endedAt ?: "")
//            val milliSecond = convertStringToTime("2022-12-27 11:45:11")
            val differenceTime = getTime(milliSecond)
            remainMonthlyVoteTime = if (differenceTime < 0) ApiNotRoad else differenceTime
            Log.e("remain", remainMonthlyVoteTime.toString())

        }
    }


    private fun refreshVoteStatus() {
        viewModelScope.launch {
            while (true) {
                delay(1_000)
                if (remainMonthlyVoteTime != ApiNotRoad) {
                    remainMonthlyVoteTime -= 1
                    if (remainMonthlyVoteTime < 0) {
                        remainMonthlyVoteTime = ApiNotRoad
                        getVoteEndedTime()
                    } else {
                        if (LocalDate.now().dayOfMonth == 2) {
                            _rankRemainingStatus.emit(Pair("", RankRemainingStatus.VoteWaiting))
                        } else {

                            val day = remainMonthlyVoteTime / (24 * 60 * 60)

                            when {
                                day == 0 -> {
                                    val hour = remainMonthlyVoteTime / (60 * 60)
                                    val minute = remainMonthlyVoteTime / 60 % 60
                                    var hourString = ""
                                    var minuteString = ""
                                    hourString = if (hour < 10) "0${hour}" else "$hour"
                                    minuteString = if (minute < 10) "0${minute}" else "$minute"
                                    _rankRemainingStatus.emit(
                                        Pair(
                                            "${hourString}:${minuteString}",
                                            RankRemainingStatus.VoteIng
                                        )
                                    )
                                }
                                day > 0 -> {
                                    _rankRemainingStatus.emit(
                                        Pair(
                                            "${day}일",
                                            RankRemainingStatus.VoteIng
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun refreshRank() {
        remain24HourTime = getTime(targetHour = 24, targetMinute = 0)
        viewModelScope.launch {
            while (true) {
                delay(1_000)
                remain24HourTime -= 1
                if (remain24HourTime < 0) {
                    remain24HourTime = getTime(targetHour = 24, targetMinute = 0)
                    getMonthStarRank()
                }
            }
        }
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
            val response = repository.getMonthlyStarList(
                LocalDate.now().year.toString(),
                LocalDate.now().month.value.toString()
            )
            when (response.result.code) {
                ResultCodeStatus.Success.code -> {
                    val result = response.data

                    when {
                        result?.men.isNullOrEmpty() and result?.women.isNullOrEmpty() -> {
                            _rankStatus.emit(RankStatus.UnAvailable)
                        }
                        else -> {
                            _rankStatus.emit(RankStatus.Available)
                            val menList = result?.men?.filter { it.rank < 4 }
                            val originMenCount = menList?.count()
                            val distinctMen =
                                menList?.distinctBy { it.rank }?.filter { it.rank != 0 }
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
    }

    private fun getBanners() {
        viewModelScope.launch {
            val response = repository.getBanners(group = "rank", platform = "aos")
            when (response.result.code) {
                ResultCodeStatus.Success.code -> {
                    val data = response.data
                    _banners.emit(data)
                }

            }
        }
    }
}