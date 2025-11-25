package com.example.myapplication.ui.onboarding.data

// 用枚举来安全地表示七种动物，避免拼写错误
enum class Animal {
    DOLPHIN, PANDA, BIRD, CAT, DOG, FOX, ELEPHANT
}

// 最终生成的旅行搭子画像
data class TravelBuddyProfile(
    val animal: Animal,
    val title: String,         // 例如："快乐社交家"
    val description: String,   // 详细描述
    val imageUrl: Int          // 图片在drawable中的资源ID, 例如: R.drawable.avatar_cat
   // val headimage: Int          //后续会用到的头部画像
)

// 一个问题和它的所有选项
data class Question(
    val id: Int,
    val text: String,
    val options: List<AnswerOption>
)

// 一个可供选择的答案
data class AnswerOption(
    val id: String, // 全局唯一的ID, 例如 "1-A", "2-C"
    val text: String,
    val icon: Int? = null //(如果你的选项有图标，可以加上这一行)
)