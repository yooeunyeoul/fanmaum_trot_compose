package com.trotfan.trot.ui.home.ranking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.BaseApplication
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.datastore.UserInfoDataStore
import com.trotfan.trot.datastore.RankMainDataStore
import com.trotfan.trot.datastore.RankMainManager
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.model.Banner
import com.trotfan.trot.model.FavoriteStarInfo
import com.trotfan.trot.model.StarRanking
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.RankingHistoryRepository
import com.trotfan.trot.repository.VoteRepository
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import com.trotfan.trot.ui.utils.convertStringToTime
import com.trotfan.trot.ui.utils.getTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
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

const val StopLoop = -1004

@HiltViewModel
class RankHomeViewModel @Inject constructor(
    private val repository: RankingHistoryRepository,
    private val voteRepository: VoteRepository,
    private val loadingHelper: LoadingHelper,
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
        MutableStateFlow(
            Pair(
                "08:50",
                RankRemainingStatus.VoteIng
            )
        )

    val rankStatus: StateFlow<RankStatus>
        get() = _rankStatus
    private val _rankStatus =
        MutableStateFlow(RankStatus.Available)


    var remain24HourTime: Int = 0
    var remainMonthlyVoteTime: Int = StopLoop

    var sampleCount = 0

    val tabIndex: StateFlow<Int>
        get() = _tabIndex
    private val _tabIndex = MutableStateFlow(0)

    init {
        userInfoManager = UserInfoManager(context.UserInfoDataStore)
        rankMainManager = RankMainManager(context.RankMainDataStore)
        getMonthStarRank()
        getBanners()
        refreshRank()
        getVoteEndedTime()
        observeGender()
        refreshVoteStatus()
    }

    private fun observeGender() {
        viewModelScope.launch {
            userInfoManager.favoriteStarGenderFlow.collectLatest {
                _tabIndex.emit(if (it == Gender.MEN) 0 else 1)
            }
        }
    }

    fun changeIndex(index: Int) {
        viewModelScope.launch {
            _tabIndex.emit(index)
        }
    }

    private fun getVoteEndedTime() {
        viewModelScope.launch {
            val response = voteRepository.getVote()
            val milliSecond = convertStringToTime(response?.data?.endedAt ?: "")
//            val milliSecond = convertStringToTime("2022-12-27 19:11:00")
            val differenceTime = getTime(milliSecond, application = context as BaseApplication)
            remainMonthlyVoteTime = if (differenceTime < 0) StopLoop else differenceTime
//            Log.e("remain", remainMonthlyVoteTime.toString())

        }
    }


    private fun refreshVoteStatus() {
        viewModelScope.launch {
            while (true) {
                delay(1_000)
                when {
                    remainMonthlyVoteTime == StopLoop || LocalDate.now().dayOfMonth == 1 -> {
                        _rankRemainingStatus.emit(Pair("", RankRemainingStatus.VoteWaiting))
                    }
                    else -> {
                        remainMonthlyVoteTime -= 1
                        if (remainMonthlyVoteTime < 0) {
                            remainMonthlyVoteTime = StopLoop
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
        remain24HourTime = getTime(targetHour = 24, targetMinute = 0, application = context as BaseApplication)
        viewModelScope.launch {
            while (true) {
                delay(1_000)
                remain24HourTime -= 1
                if (remain24HourTime < 0) {
                    remain24HourTime = getTime(targetHour = 24, targetMinute = 0, application = context as BaseApplication)
                    getMonthStarRank()
                    getVoteEndedTime()
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
            loadingHelper.showProgress()
            val response = repository.getMonthlyStarList(
                null,
                null
            )
            when (response.result.code) {
                ResultCodeStatus.SuccessWithNoData.code -> {
                    _rankStatus.emit(RankStatus.UnAvailable)
                }
                ResultCodeStatus.SuccessWithData.code -> {
                    val result = response.data
                    _rankStatus.emit(RankStatus.Available)
                    val menList: List<StarRanking> = if ((result?.men?.size ?: 0) > 4) {
                        result?.men?.subList(0, 4) ?: listOf()
                    } else {
                        result?.men ?: listOf()
                    }
                    val originMenCount = menList.count()
                    val distinctMen =
                        menList.distinctBy { it.rank }
                    val distinctMenCount = distinctMen.count()
                    if (originMenCount == distinctMenCount) {
                        if (distinctMen.count { (it.rank ?: 0) < 4 && (it.rank ?: 0) > 0 } == 3) {
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
                    val womenList: List<StarRanking> = if ((result?.women?.size ?: 0) > 4) {
                        result?.women?.subList(0, 4) ?: listOf()
                    } else {
                        result?.women ?: listOf()
                    }
                    val originWomenCount = womenList.count()
                    val distinctWomen = womenList.distinctBy { it.rank }
                    val distinctWomenCount = distinctWomen.count()
                    if (originWomenCount == distinctWomenCount) {

                        if (distinctWomen.count {
                                (it.rank ?: 0) < 4 && (it.rank ?: 0) > 0
                            } == 3) {
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
            loadingHelper.hideProgress()
        }
    }

    private fun getBanners() {
        viewModelScope.launch {
            loadingHelper.showProgress()
            val response = repository.getBanners(group = "rank", platform = "aos")
            when (response.result.code) {
                ResultCodeStatus.SuccessWithData.code -> {
                    val data = response.data
                    _banners.emit(data?: listOf())
                }
                ResultCodeStatus.SuccessWithNoData.code -> {
                    _banners.emit(listOf())
                }

            }
            loadingHelper.hideProgress()
        }
    }

    fun changeStatus() {
        viewModelScope.launch {
            if (_rankStatus.value == RankStatus.UnAvailable) {
                _rankStatus.emit(RankStatus.Available)
            } else {
                _rankStatus.emit(RankStatus.UnAvailable)
            }
        }
    }

    fun changeTime() {
        viewModelScope.launch {
            sampleCount += 1
            when {
                sampleCount % 3 == 0 -> {
                    _rankRemainingStatus.emit(
                        Pair(
                            "08:50",
                            RankRemainingStatus.VoteIng
                        )
                    )

                }
                sampleCount % 3 == 1 -> {
                    _rankRemainingStatus.emit(
                        Pair(
                            "36일",
                            RankRemainingStatus.VoteIng
                        )
                    )

                }
                sampleCount % 3 == 2 -> {
                    _rankRemainingStatus.emit(
                        Pair(
                            "36일",
                            RankRemainingStatus.VoteWaiting
                        )
                    )
                }
            }
        }

    }
}