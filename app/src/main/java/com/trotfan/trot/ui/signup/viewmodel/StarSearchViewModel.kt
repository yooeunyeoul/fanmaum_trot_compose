package com.trotfan.trot.ui.signup.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.trotfan.trot.datasource.GetStarDataSource
import com.trotfan.trot.datastore.UserInfoDataStore
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.model.FavoriteStar
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.SignUpRepository
import com.trotfan.trot.ui.BaseViewModel
import com.trotfan.trot.ui.components.input.SearchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StarSearchViewModel @Inject constructor(
    private val repository: SignUpRepository,
    private val dataSource: GetStarDataSource,
    application: Application
) : BaseViewModel(application) {

    private val context = getApplication<Application>()

    private val _searchState = MutableStateFlow(SearchStatus.TrySearch)
    val searchStatus: StateFlow<SearchStatus>
        get() = _searchState

    private val _requestComplete = MutableStateFlow(false)
    val requestComplete: StateFlow<Boolean>
        get() = _requestComplete

    private val _startListState = mutableStateOf<Flow<PagingData<FavoriteStar>>?>((null))
    val starListState: State<Flow<PagingData<FavoriteStar>>?>
        get() = _startListState

    private val _onComplete = MutableStateFlow(false)
    val onComplete: StateFlow<Boolean>
        get() = _onComplete

    var userInfoManager: UserInfoManager = UserInfoManager(context.UserInfoDataStore)


    fun searchStar(keyword: String) {
        viewModelScope.launch {

            when (keyword) {
                "" -> {
                    _searchState.emit(SearchStatus.TrySearch)
                    _startListState.value = null
                }
                else -> {
                    _startListState.value = Pager(PagingConfig(pageSize = 10)) {
                        dataSource.apply { starName = keyword }
                    }.flow
                }
            }
        }

    }

    fun requestStar(starName: String, completeListener: () -> Unit) {
        viewModelScope.launch {
            Firebase.analytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_NAME, starName)
            })
            _requestComplete.emit(true)
            completeListener.invoke()
        }
    }

    fun dismissCompleteDialog() {
        viewModelScope.launch {
            _requestComplete.emit(false)
        }

    }

    fun selectStar(selectedItem: FavoriteStar?) {
        viewModelScope.launch {
            context.userIdStore.data.collect {
                val response = repository.updateUser(
                    userid = it.userId,
                    starId = selectedItem?.id,
                    token = userLocalToken.value?.token ?: ""
                )
                if (response.result.code == ResultCodeStatus.SuccessWithNoData.code) {
                    userInfoManager.setFavoriteStar(selectedItem?.id ?: 0)
                    _onComplete.emit(true)
                }
            }
        }
    }

    fun changeSearchState(searchState: SearchStatus) {
        viewModelScope.launch {
            _searchState.emit(searchState)
        }

    }
}
