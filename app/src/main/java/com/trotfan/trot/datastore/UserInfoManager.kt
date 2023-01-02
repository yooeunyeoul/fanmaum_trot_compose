package com.trotfan.trot.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserInfoManager(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val FAVORITE_STAR_ID = intPreferencesKey("FAVORITE_STAR_ID")
        val FAVORITE_STAR_GENDER = intPreferencesKey("FAVORITE_STAR_GENDER")
        val FAVORITE_STAR_NAME = stringPreferencesKey("FAVORITE_STAR_NAME")
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val FAVORITE_STAR_IMAGE = stringPreferencesKey("FAVORITE_STAR_IMAGE")
    }

    suspend fun storeUserInfo(
        favoriteStarId: Int,
        favoriteGender: Int,
        favoriteStarName: String,
        favoriteStarImage: String,
        userName: String
    ) {
        dataStore.edit {
            it[FAVORITE_STAR_ID] = favoriteStarId
            it[FAVORITE_STAR_GENDER] = favoriteGender
            it[FAVORITE_STAR_NAME] = favoriteStarName
            it[FAVORITE_STAR_IMAGE] = favoriteStarImage
            it[USER_NAME] = userName
        }
    }

    val favoriteStarIdFlow: Flow<Int?> = dataStore.data.map {
        it[FAVORITE_STAR_ID]
    }
    val favoriteStarGenderFlow: Flow<Gender?> = dataStore.data.map {
        Log.e("it[FAVORITE_STAR_GENDER]",it[FAVORITE_STAR_GENDER].toString())
        if (it[FAVORITE_STAR_GENDER] == 1) Gender.MEN else Gender.WOMEN
    }
    val favoriteStarNameFlow: Flow<String?> = dataStore.data.map {
        it[FAVORITE_STAR_NAME]
    }
    val favoriteStarImageFlow: Flow<String?> = dataStore.data.map {
        it[FAVORITE_STAR_IMAGE]
    }
    val userNameFlow: Flow<String?> = dataStore.data.map {
        it[USER_NAME]
    }

}

val Context.FavoriteStarDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "favorite_star_prefs"
)