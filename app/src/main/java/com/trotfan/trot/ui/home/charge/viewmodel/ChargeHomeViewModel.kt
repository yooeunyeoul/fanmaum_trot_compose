package com.trotfan.trot.ui.home.charge.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.PurchaseHelper
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.model.Expired
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.ChargeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChargeHomeViewModel @Inject constructor(
    private val repository: ChargeRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()


    fun getVoteTickets(purchaseHelper: PurchaseHelper) {
        viewModelScope.launch {
            context.userIdStore.data.collect {
                kotlin.runCatching {
                    repository.getVoteTickets(
                        userId = it.userId
                    )

                }.onSuccess { response ->
                    when (response.result.code) {
                        ResultCodeStatus.Success.code -> {
                            purchaseHelper.refreshTickets(response.data?.expired ?: Expired())
                            purchaseHelper.closeApiCall()
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