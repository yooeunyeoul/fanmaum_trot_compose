package com.trotfan.trot.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VoteMainManager(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val VOTE_MAIN_TOOL_TIP = booleanPreferencesKey("VOTE_MAIN_TOOL_TIP")
    }

    suspend fun storeTooltipState(
        isShowing: Boolean
    ) {
        dataStore.edit {
            it[VOTE_MAIN_TOOL_TIP] = isShowing
        }
    }

    val isShowingVoteMainToolTipFlow: Flow<Boolean> = dataStore.data.map {
        it[VOTE_MAIN_TOOL_TIP] ?: true
    }

}

val Context.VoteMainDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "vote_main_prefs"
)