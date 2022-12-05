package com.trotfan.trot.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteStarManager(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val FAVORITE_STAR_ID = intPreferencesKey("FAVORITE_STAR_ID")
        val FAVORITE_STAR_GENDER = intPreferencesKey("FAVORITE_STAR_GENDER")
        val FAVORITE_STAR_NAME = stringPreferencesKey("FAVORITE_STAR_NAME")
        val FAVORITE_STAR_IMAGE = stringPreferencesKey("FAVORITE_STAR_IMAGE")
    }

    suspend fun storeFavoriteStar(
        id: Int, gender: Int, name: String, image: String
    ) {
        dataStore.edit {
            it[FAVORITE_STAR_ID] = id
            it[FAVORITE_STAR_GENDER] = gender
            it[FAVORITE_STAR_NAME] = name
            it[FAVORITE_STAR_IMAGE] = image
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

}

val Context.FavoriteStarDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "favorite_star_prefs"
)