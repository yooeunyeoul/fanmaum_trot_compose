package com.trotfan.trot.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PermissionAgreementManager(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val PERMISSION_AGREEMENT = booleanPreferencesKey("PERMISSION_AGREEMENT")
    }


    suspend fun permissionCheck(
        isShowing: Boolean
    ) {
        dataStore.edit {
            it[PERMISSION_AGREEMENT] = isShowing
        }
    }

    val isPermissionCheckFlow: Flow<Boolean> = dataStore.data.map {
        it[PERMISSION_AGREEMENT] ?: false
    }

}

val Context.PermissionAgreeStore: DataStore<Preferences> by preferencesDataStore(
    name = "permission_agree_prefs"
)