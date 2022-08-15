package com.trotfan.trot.ui.signup.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.trotfan.trot.datasource.GetStarDataSource
import com.trotfan.trot.model.Person
import com.trotfan.trot.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarSelectViewModel @Inject constructor(
    private val repository: SignUpRepository,
    private val dataSource: GetStarDataSource
) : ViewModel() {

    private val _onComplete = MutableStateFlow(false)
    val onComplete: StateFlow<Boolean>
        get() = _onComplete

    private val _startListState = mutableStateOf<Flow<PagingData<Person>>?>(null)
    val starListState: State<Flow<PagingData<Person>>?>
        get() = _startListState


    init {
        getStartList()
    }

    fun getStartList() {
        viewModelScope.launch {
            _startListState.value = Pager(PagingConfig(pageSize = 15)) { dataSource }.flow
        }
    }

    fun selectStar(selectedItem: Person?) {
        viewModelScope.launch {
            val response = repository.updateUser(userid = "1", starId = selectedItem?.id.toString())
            if (response.code == 200) {
                _onComplete.emit(true)
            }
        }
    }
}
