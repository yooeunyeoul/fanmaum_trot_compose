package com.trotfan.trot.ui.signup.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.model.UserInfo
import com.trotfan.trot.repository.SampleRepository
import com.trotfan.trot.ui.components.input.SearchStatus
import com.trotfan.trot.ui.signup.Sample
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: SampleRepository
) : ViewModel() {
    private val _testData = MutableStateFlow<List<Sample>>(emptyList())
    val testData: StateFlow<List<Sample>>
        get() = _testData
    private val _searchState = MutableStateFlow(SearchStatus.TrySearch)
    val searchStatus: StateFlow<SearchStatus>
        get() = _searchState

    private val _requestComplete = MutableStateFlow(false)
    val requestComplete: StateFlow<Boolean>
        get() = _requestComplete


    init {
//        getRestApiTest()
        Log.d("Initializing", "MainViewModel")
    }

    private fun getRestApiTest() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getApiServiceOne(
                userInfo = UserInfo(
                    id = "asdf",
                    pw = "1234",
                    nickname = "Seung Hee",
                    stateMessage = "Log Success"
                )
            )
        }

    }


    fun searchStar(keyword: String) {
        val sampleList = mutableListOf<Sample>()
        viewModelScope.launch {
            when (keyword) {
                "" -> {
                    _testData.emit(listOf())
                    _searchState.emit(SearchStatus.TrySearch)
                }
                "empty" -> {
                    _testData.emit(listOf())
                    _searchState.emit(SearchStatus.NoResult)
                }
                else -> {
                    for (i in 0..10) {
                        sampleList.add(Sample(id = i))
                    }
                    _testData.emit(sampleList)
                    _searchState.emit(SearchStatus.SearchResult)
                }

            }
        }

    }

    fun requestStar(starName: String) {
        viewModelScope.launch {
            _requestComplete.emit(true)
        }
    }

    fun dismissCompleteDialog() {
        viewModelScope.launch {
            _requestComplete.emit(false)
        }

    }
}
