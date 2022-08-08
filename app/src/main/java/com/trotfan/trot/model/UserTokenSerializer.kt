package com.trotfan.trot.model

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.trotfan.trot.UserTokenValue
import java.io.InputStream
import java.io.OutputStream

object UserTokenSerializer : Serializer<UserTokenValue> {
    override val defaultValue: UserTokenValue
        get() = UserTokenValue.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserTokenValue {
        return try {
            UserTokenValue.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: UserTokenValue, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.userTokenStore: DataStore<UserTokenValue> by dataStore(
    fileName = "user_token.pb",
    serializer = UserTokenSerializer
)

