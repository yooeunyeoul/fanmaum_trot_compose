package com.trotfan.trot.extensions

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trotfan.trot.datastore.FavoriteStarDataStore
import com.trotfan.trot.datastore.UserInfoManager
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import com.trotfan.trot.ui.home.vote.viewmodel.VoteHomeViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface CommonObserve {
//    val gender: StateFlow<Gender>
//        get() = _gender

    //    private val _gender: MutableStateFlow<Gender>
    //        get() = MutableStateFlow(Gender.WOMEN)

    abstract val gender :MutableStateFlow<Gender>

    fun observeGender(userInfoManager: UserInfoManager, ) {

        if (this is ViewModel) {
            viewModelScope.launch {
                userInfoManager.favoriteStarGenderFlow.collectLatest {
                    Log.e("이건 컴언입니다",it.toString())
                    gender.emit(it ?: Gender.WOMEN)

                }
            }
        }
    }
}