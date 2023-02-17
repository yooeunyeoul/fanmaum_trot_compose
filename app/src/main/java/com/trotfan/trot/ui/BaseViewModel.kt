package com.trotfan.trot.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.protobuf.Api
import com.trotfan.trot.UserId
import com.trotfan.trot.UserTokenValue
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.di.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class BaseViewModel constructor(
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()
    var userLocalToken = MutableStateFlow<UserTokenValue?>(null)
    var userLocalId = MutableStateFlow<Long?>(null)

    init {
        viewModelScope.launch {
            context.userTokenStore.data.collect {
                if (it.token.isNullOrEmpty().not()) {
                    userLocalToken.emit(it)
                }
            }
            context.userIdStore.data.collect { it ->
                it.userId.let { id ->
                    userLocalId.emit(id)
                }
            }
        }
    }
}