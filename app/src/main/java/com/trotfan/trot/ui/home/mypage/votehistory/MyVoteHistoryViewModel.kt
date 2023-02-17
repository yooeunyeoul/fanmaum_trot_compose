package com.trotfan.trot.ui.home.mypage.votehistory

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.trotfan.trot.LoadingHelper
import com.trotfan.trot.datasource.UserTicketHistoryDataSource
import com.trotfan.trot.datastore.UserTicketManager
import com.trotfan.trot.datastore.UserTicketStore
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.model.TicketItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyVoteHistoryViewModel @Inject constructor(
    private val userTicketHistoryDataSource: UserTicketHistoryDataSource,
    application: Application
) : AndroidViewModel(application) {

    lateinit var userTicketManager: UserTicketManager
    private val context = getApplication<Application>()

    private val _userTicketHistory = mutableStateOf<Flow<PagingData<TicketItem>>?>((null))
    val userTicketHistory: State<Flow<PagingData<TicketItem>>?>
        get() = _userTicketHistory

    val isListEmpty: StateFlow<Boolean?>
        get() = _isListEmpty
    private val _isListEmpty =
        MutableStateFlow(null)


    val unlimitedTicket: StateFlow<Long>
        get() = _unlimitedTicket
    private val _unlimitedTicket =
        MutableStateFlow(0L)

    val todayTicket: StateFlow<Long>
        get() = _todayTicket
    private val _todayTicket =
        MutableStateFlow(0L)

    init {
        viewModelScope.launch {
            userTicketManager = UserTicketManager(context.UserTicketStore)
            _unlimitedTicket.emit(userTicketManager.expiredUnlimited.first() ?: 0)
            _todayTicket.emit(userTicketManager.expiredToday.first() ?: 0)
            getUserTicketHistory()
        }
    }

    fun getUserTicketHistory(_filter: String? = null) {
        viewModelScope.launch {
            val _token = context.userTokenStore.data.first().token
            val _userId = context.userIdStore.data.first().userId
            _userTicketHistory.value = Pager(PagingConfig(pageSize = 10)) {
                userTicketHistoryDataSource.apply {
                    token = _token
                    userId = _userId
                    filter = _filter
                }
            }.flow
        }
    }
}