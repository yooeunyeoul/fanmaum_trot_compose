package com.trotfan.trot.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.trotfan.trot.ui.home.vote.viewmodel.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserTicketManager(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val EXPIRED_UNLIMITED = longPreferencesKey("EXPIRED_UNLIMITED")
        val EXPIRED_TODAY = longPreferencesKey("EXPIRED_TO_DAY")
    }

    suspend fun storeUserTicket(
        expiredUnlimited: Long,
        expiredToday: Long
    ) {
        dataStore.edit {
            it[EXPIRED_TODAY] = expiredToday
            it[EXPIRED_UNLIMITED] = expiredUnlimited
        }
    }

    val expiredUnlimited: Flow<Long?> = dataStore.data.map {
        it[EXPIRED_UNLIMITED]
    }
    val expiredToday: Flow<Long?> = dataStore.data.map {
        it[EXPIRED_TODAY]
    }

}

val Context.UserTicketStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_ticket_prefs"
)