package com.example.myapplication.ui.main.companion

import com.example.myapplication.ui.main.companion.data.BuddyProfile
import com.example.myapplication.ui.main.companion.data.InteractionItem
import com.example.myapplication.ui.main.companion.data.StoreProduct
import com.example.myapplication.ui.main.companion.data.TaskItem
import com.example.myapplication.ui.main.companion.data.WardrobeCategory
import com.example.myapplication.ui.main.companion.data.WardrobeItem

/**
 * 搭子主页面的完整UI状态
 * 包含了这个功能模块需要的所有数据
 */
data class CompanionUiState(
    // 主要信息
    val buddyProfile: BuddyProfile? = null,
    val isLoading: Boolean = true,
    val error: String? = null,

    // 任务与成就列表
    val ongoingTasks: List<TaskItem> = emptyList(),
    val completedTasks: List<TaskItem> = emptyList(),

    // IP装扮 (衣柜)
    // 使用Map，Key是分类，Value是该分类下的物品列表
    val wardrobeItems: Map<WardrobeCategory, List<WardrobeItem>> = emptyMap(),

    // 兑换商店
    val storeProducts: List<StoreProduct> = emptyList(),

    // 互动弹窗所需数据
    val foodItems: List<InteractionItem> = emptyList(),
    val toyItems: List<InteractionItem> = emptyList(),
)
