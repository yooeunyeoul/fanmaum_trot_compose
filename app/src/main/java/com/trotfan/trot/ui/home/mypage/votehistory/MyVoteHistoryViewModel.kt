package com.trotfan.trot.ui.home.mypage.votehistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.FavoriteStarDataStore
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.datastore.UserTicketManager
import com.trotfan.trot.datastore.UserTicketStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyVoteHistoryViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    lateinit var userTicketManager: UserTicketManager
    private val context = getApplication<Application>()

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
        }
    }
}