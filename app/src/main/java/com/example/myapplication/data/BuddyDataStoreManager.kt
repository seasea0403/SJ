package com.example.myapplication.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.myapplication.ui.main.companion.data.BuddyProfile
import kotlinx.serialization.json.Json
import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.serializer

// 1. 定义DataStore实例。`buddy_profile.json` 将是保存在设备上的文件名。
val Context.buddyDataStore: DataStore<BuddyProfile> by dataStore(
    fileName = "buddy_profile.json",
    serializer = BuddyProfileSerializer
)

// 2. 创建一个序列化器，告诉DataStore如何读写 BuddyProfile 对象
object BuddyProfileSerializer : Serializer<BuddyProfile> {
    // 当没有数据时，返回一个默认值。
    // 为了区分 "没有数据" 和 "一个空对象"，我们最好让它可空
    // 但为了简单起见，我们先用一个默认的空Profile
    override val defaultValue: BuddyProfile = BuddyProfile(
        name = "",
        level = 1,
        title = "",
        points = 0,
        expToNextLevel = 100,
        currentExp = 0,
        mood = "平静",
        imageUrl = ""
    )

    override suspend fun readFrom(input: InputStream): BuddyProfile {
        return try {
            Json.decodeFromString(
                BuddyProfile.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            // 如果解析失败（比如文件损坏或为空），返回默认值
            defaultValue
        }
    }

    override suspend fun writeTo(t: BuddyProfile, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(BuddyProfile.serializer(), t).encodeToByteArray()
            )
        }
    }
}
