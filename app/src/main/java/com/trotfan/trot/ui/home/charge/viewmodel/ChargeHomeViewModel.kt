package com.trotfan.trot.ui.home.charge.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.model.Expired
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.ChargeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChargeHomeViewModel @Inject constructor(
    private val repository: ChargeRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    val tickets: StateFlow<Expired>
        get() = _tickets
    private val _tickets =
        MutableStateFlow(Expired())

    init {
        getVoteTickets()
    }


    fun getVoteTickets() {
        viewModelScope.launch {
            context.userIdStore.data.collect {
                kotlin.runCatching {
                    repository.getVoteTickets(
                        userId = it.userId
                    )

                }.onSuccess { response ->
                    when (response.result.code) {
                        ResultCodeStatus.Success.code -> {
                            _tickets.emit(response.data?.expired ?: Expired())
                        }
                        ResultCodeStatus.Fail.code -> {
                            Log.e("ChargeHomeViewModel", response.result.message.toString())
                        }
                    }
                }.onFailure {
                    Log.e("ChargeHomeViewModel", it.message.toString())
                }
            }
        }
    }

}