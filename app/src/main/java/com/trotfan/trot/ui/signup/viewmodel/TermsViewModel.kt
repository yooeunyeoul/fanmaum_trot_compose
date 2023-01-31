package com.trotfan.trot.ui.signup.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.repository.SignUpRepository
import com.trotfan.trot.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
