package com.trotfan.trot.ui.signup.viewmodel

import android.R.id
import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.trotfan.trot.datasource.GetStarDataSourceForName
import com.trotfan.trot.model.Person
import com.trotfan.trot.repository.SignUpRepository
import com.trotfan.trot.ui.components.input.SearchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StarSearchViewModel @Inject constructor(
    private val repository: SignUpRepository,
    private val dataSource: GetStarDataSourceForName
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchStatus.TrySearch)
    val searchStatus: StateFlow<SearchStatus>
        get() = _searchState

    private val _requestComplete = MutableStateFlow(false)
    val requestComplete: StateFlow<Boolean>
        get() = _requestComplete

    private val _startListState = mutableStateOf<Flow<PagingData<Person>>?>((null))
    val starListState: State<Flow<PagingData<Person>>?>
        get() = _startListState


    fun searchStar(keyword: String) {
        viewModelScope.launch {

            when (keyword) {
                "" -> {
                    _searchState.emit(SearchStatus.TrySearch)
                    _startListState.value = null
                }
                "empty" -> {
                    _searchState.emit(SearchStatus.NoResult)
                }
                else -> {
                    _startListState.value = Pager(PagingConfig(pageSize = 15)) {
                        dataSource.apply { starName = keyword }
                    }.flow
                }
            }
        }

    }

    fun requestStar(starName: String) {
        if (starName.isEmpty()) {
            return
        }
        viewModelScope.launch {
            Firebase.analytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_NAME, starName)
            })
            _requestComplete.emit(true)
        }
    }

    fun dismissCompleteDialog() {
        viewModelScope.launch {
            _requestComplete.emit(false)
        }

    }

    fun selectStar(selectedItem: Person?) {
        viewModelScope.launch {

        }
    }

    fun changeSearchState(searchState: SearchStatus) {
        viewModelScope.launch {
            _searchState.emit(searchState)
        }

    }
}