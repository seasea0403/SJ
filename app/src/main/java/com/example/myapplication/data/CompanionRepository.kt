package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.ui.main.companion.data.BuddyProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

/**
 * 搭子数据的唯一真相来源，使用 Jetpack DataStore 进行持久化存储。
 *  @param context 用来获取DataStore实例
 */
class CompanionRepository(private val context: Context) {

    /**
     * 获取搭子的信息流。UI层可以持续观察这个Flow。
     * 每当 buddy_profile.json 文件被修改，这个Flow就会发射最新的数据。
     */
    fun getBuddyProfile(): Flow<BuddyProfile> {
        return context.buddyDataStore.data
            .catch { exception ->
                // 如果读取出错（例如IO异常），我们可以发射一个默认值或处理错误
                if (exception is IOException) {
                    emit(BuddyProfileSerializer.defaultValue)
                } else {
                    throw exception
                }
            }
    }

    /**
     * 保存（创建或更新）搭子的信息。
     * 这个方法是 suspend 函数，因为它背后调用了 DataStore 的异步API。
     */
    suspend fun saveBuddyProfile(profile: BuddyProfile) {
        context.buddyDataStore.updateData {
            // updateData 提供了一个事务性的安全更新
            // 这里直接返回新的profile对象即可
            profile
        }
    }
}
