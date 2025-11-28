package com.example.myapplication.ui.main.companion.data

import kotlinx.serialization.Serializable

/**
 * 搭子的核心信息模型
 * 用于主页显示搭子的名称、等级、积分、心情等状态
 */
@Serializable
data class BuddyProfile(
    val name: String,
    val level: Int,
    val title: String,
    val points: Int,
    val expToNextLevel: Int,      // 升级还需要的总积分
    val currentExp: Int,          // 当前等级获得的积分
    val mood: String,
    val imageUrl: String          // 搭子的图片资源ID或URL
) {
    companion object
}

/**
 * 任务或成就的模型
 */
data class TaskItem(
    val id: String,
    val title: String,
    val description: String,
    val currentProgress: Int,
    val targetProgress: Int,
    val rewardPoints: Int,
    val isCompleted: Boolean = false
)

/**
 * 装扮衣柜中的物品模型
 */
data class WardrobeItem(
    val id: String,
    val name: String,
    val category: WardrobeCategory,
    val iconUrl: String, // 装扮的图标资源ID或URL
    val isEquipped: Boolean = false
)

/**
 * 商店中可购买的商品模型
 */
data class StoreProduct(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val iconUrl: String,
    val isOwned: Boolean = false,
    val tag: String? = null // 用于显示 "限时", "新品" 等标签
)

/**
 * 互动项目模型 (用于喂食/玩耍)
 */
data class InteractionItem(
    val id: String,
    val name: String,
    val cost: Int,
    val happinessEffect: Int, // 玩耍/喂食增加的心情值
    val iconUrl: String,
    val category: InteractionCategory
)

/**
 *  用于区分装扮类型，对应 "衣物", "头饰" 等Tab
 */
enum class WardrobeCategory {
    CLOTHING,
    HEADWEAR,

    ACCESSORY
}

/**
 * 用于区分互动项目是食物还是玩具
 */
enum class InteractionCategory {
    FOOD,
    TOY
}
