package com.trotfan.trot.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteStar(
    val gender: Int,
    val id: Int,
    val image: String,
    val name: String,
    val group: Group,
)

