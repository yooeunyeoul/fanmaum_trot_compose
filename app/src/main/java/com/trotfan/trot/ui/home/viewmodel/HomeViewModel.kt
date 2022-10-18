package com.trotfan.trot.ui.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.trotfan.trot.BuildConfig
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.model.*
import com.trotfan.trot.repository.AuthRepository
import com.trotfan.trot.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _mainPopups = MutableStateFlow<MainPopups?>(null)
    val mainPopups: StateFlow<MainPopups?>
        get() = _mainPopups

    var updateState = MutableStateFlow(false)
        private set

    var rollingState = MutableStateFlow(false)
        private set

    var feverStatus = MutableStateFlow(false)
        private set

    var autoVoteStatus = MutableStateFlow(false)
        private set

    init {
        getMainPopups()
    }

    private fun getMainPopups() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getMainPopups()
            }.onSuccess {
                _mainPopups.emit(it.data)
                it.data.let { mainPopups ->
                    mainPopups.update?.version?.let { version ->
                        if (version.replace(".", "")
                                .toInt() > BuildConfig.VERSION_NAME.replace(".", "")
                                .replace("_dev", "")
                                .toInt()
                        ) {
                            updateState.emit(true)
                        } else {
                            if (it.data.autoVote.is_available) {
                                autoVoteStatus.emit(true)
                            }

                            if (it.data.is_rewarded) {
                                feverStatus.emit(true)
                            }


                            if (it.data.layers != null) {
                                rollingState.emit(true)
                            }
                        }
                    }
                }
            }.onFailure {
                Log.d("HomeViewModel", it.message.toString())
            }
        }
    }
}