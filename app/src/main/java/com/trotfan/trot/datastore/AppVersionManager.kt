package com.trotfan.trot.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppVersionManager(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val APP_VERSION = stringPreferencesKey("APP_VERSION")
    }


    suspend fun setStoreVersion(
        version: String
    ) {
        dataStore.edit {
            it[APP_VERSION] = version
        }
    }

    val getStoreVersion: Flow<String> = dataStore.data.map {
        it[APP_VERSION] ?: ""
    }

}

val Context.AppVersionManager: DataStore<Preferences> by preferencesDataStore(
    name = "app_version_prefs"
)