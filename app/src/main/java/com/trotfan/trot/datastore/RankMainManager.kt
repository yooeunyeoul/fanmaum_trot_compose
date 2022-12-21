package com.trotfan.trot.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RankMainManager(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val RANK_MAIN_SCROLL_TOOL_TIP = booleanPreferencesKey("RANK_MAIN_SCROLL_TOOL_TIP")
    }


    suspend fun storeScrollTooltipState(
        isShowing: Boolean
    ) {
        dataStore.edit {
            it[RANK_MAIN_SCROLL_TOOL_TIP] = isShowing
        }
    }

    val isShowingRankMainScrollToolTipFlow: Flow<Boolean> = dataStore.data.map {
        it[RANK_MAIN_SCROLL_TOOL_TIP] ?: true
    }

}

val Context.RankMainDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "rank_main_prefs"
)