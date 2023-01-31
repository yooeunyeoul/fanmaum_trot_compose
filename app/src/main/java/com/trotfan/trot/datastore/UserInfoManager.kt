package com.trotfan.trot.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.trotfan.trot.model.ProfileImage
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
        val USER_IDP = intPreferencesKey("USER_IDP")
        val USER_MAIL = stringPreferencesKey("USER_MAIL")
        val USER_PROFILE_IMAGE = stringPreferencesKey("USER_PROFILE_IMAGE")
    }

    suspend fun storeUserInfo(
        favoriteStarId: Int,
        favoriteGender: Int,
        favoriteStarName: String,
        favoriteStarImage: String,
        userName: String,
        userIdp: Int,
        userMail: String,
        userProfileImage: String
    ) {
        dataStore.edit {
            it[FAVORITE_STAR_ID] = favoriteStarId
            it[FAVORITE_STAR_GENDER] = favoriteGender
            it[FAVORITE_STAR_NAME] = favoriteStarName
            it[FAVORITE_STAR_IMAGE] = favoriteStarImage
            it[USER_NAME] = userName
            it[USER_IDP] = userIdp
            it[USER_MAIL] = userMail
            it[USER_PROFILE_IMAGE] = userProfileImage
        }
    }

    suspend fun setUserProfileImage(
        userProfileImage: String
    ) {
        dataStore.edit {
            it[USER_PROFILE_IMAGE] = userProfileImage
        }
    }

    val favoriteStarIdFlow: Flow<Int?> = dataStore.data.map {
        it[FAVORITE_STAR_ID]
    }
    val favoriteStarGenderFlow: Flow<Gender?> = dataStore.data.map {
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
    val userIdpFlow: Flow<Int?> = dataStore.data.map {
        it[USER_IDP]
    }

    val userMailFlow: Flow<String?> = dataStore.data.map {
        it[USER_MAIL]
    }

    val userProfileImageFlow: Flow<String?> = dataStore.data.map {
        it[USER_PROFILE_IMAGE]
    }

}

val Context.UserInfoDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_info_prefs"
)