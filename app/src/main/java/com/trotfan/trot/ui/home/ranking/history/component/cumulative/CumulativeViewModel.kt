package com.trotfan.trot.ui.home.ranking.history.component.cumulative

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
import com.trotfan.trot.model.StarRankingDetail
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

@HiltViewModel
class CumulativeViewModel @Inject constructor(
    private val repository: RankingHistoryRepository,
    application: Application
) : AndroidViewModel(application) {
    val starRankingDetail = MutableStateFlow(listOf<StarRankingDetail>())

    fun getStarRankingDetail(starId: Int, year: String?, month: String?) {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getStarRankingDetail(starId, year, month)
            }.onSuccess {
                it.data?.let { list ->
                    starRankingDetail.emit(list)
                }
            }.onFailure {
                Log.d("RankingHistoryViewModel", it.message.toString())
            }
        }
    }
}