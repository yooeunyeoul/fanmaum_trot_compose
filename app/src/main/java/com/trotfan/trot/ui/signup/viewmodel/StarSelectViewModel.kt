package com.trotfan.trot.ui.signup.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.trotfan.trot.datasource.GetStarDataSource
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.model.Star
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarSelectViewModel @Inject constructor(
    private val repository: SignUpRepository,
    private val dataSource: GetStarDataSource,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()
    private val _onComplete = MutableStateFlow(false)
    val onComplete: StateFlow<Boolean>
        get() = _onComplete

    val starListState =
        Pager(PagingConfig(pageSize = 15)) { dataSource }.flow.cachedIn(viewModelScope)

    fun selectStar(selectedItem: Star?) {
        viewModelScope.launch {
            context.userIdStore.data.collect {
                val response = repository.updateUser(
                    userid = it.userId.toInt(),
                    starId = selectedItem?.id
                )
                if (response.result.code == ResultCodeStatus.Success.code) {
                    _onComplete.emit(true)
                } else {
                    Log.e("Return Error", "Message:"+response.result.message)
                }
            }
        }
    }
}
