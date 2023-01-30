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
import com.trotfan.trot.model.FavoriteStar
import com.trotfan.trot.network.ResultCodeStatus
import com.trotfan.trot.repository.SignUpRepository
import com.trotfan.trot.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    private val repository: SignUpRepository,
    application: Application
) : BaseViewModel(application) {

    private val context = getApplication<Application>()

    fun updateUser() {
        viewModelScope.launch {
            kotlin.runCatching {
                context.userIdStore.data.collect {
                    repository.updateUser(
                        userid = it.userId,
                        agrees_terms = true,
                        token = userLocalToken.value?.token ?: ""
                    )
                }
            }.onSuccess {

            }.onFailure {

            }
        }
    }
}
