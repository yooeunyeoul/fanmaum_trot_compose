package com.trotfan.trot.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.trotfan.trot.DateManager
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object DateManagerSerializer : Serializer<DateManager> {
    override val defaultValue: DateManager
        get() = DateManager.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): DateManager {
        return try {
            DateManager.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: DateManager, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.dateManager: DataStore<DateManager> by dataStore(
    fileName = "date_manager.pb",
    serializer = DateManagerSerializer
)

