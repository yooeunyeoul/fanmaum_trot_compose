package com.trotfan.trot.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.trotfan.trot.UserIdValue
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object UserIdSerializer : Serializer<UserIdValue> {
    override val defaultValue: UserIdValue
        get() = UserIdValue.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserIdValue {
        return try {
            UserIdValue.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: UserIdValue, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.userIdStore: DataStore<UserIdValue> by dataStore(
    fileName = "user_id.pb",
    serializer = UserIdSerializer
)

