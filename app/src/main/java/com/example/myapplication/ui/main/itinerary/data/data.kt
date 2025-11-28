package com.example.myapplication.ui.main.itinerary.data

// 行程项的状态
enum class TripStatus {
    COMPLETED, // 已完成 (打勾)
    IN_PROGRESS, // 进行中 (黄色圆点)
    UPCOMING // 未开始 (灰色圆点)
}

// 单个行程项的数据结构
data class TripItem(
    val time: String,
    val status: TripStatus,
    val title: String,
    val location: String,
    val duration: String,
    val isCompleted: Boolean, // 标记是否已完成
    val hasAction: Boolean = false // 是否有操作按钮
)

// 今日发现的通用数据结构
data class DiscoveryItem(
    val type: String, // "搭子" 或 "随记"
    val title: String,
    val description: String,
    val imageUrl: Int
)

// 快速入口数据结构
data class QuickAccessItem(
    val iconResId: Int, // 图标资源ID
    val label: String,
    val route: String // 点击后的路由
)