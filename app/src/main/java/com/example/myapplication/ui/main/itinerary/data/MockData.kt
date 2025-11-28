package com.example.myapplication.ui.main.itinerary.data

import com.example.myapplication.R


object MockData {
    val quickAccessItems = listOf(
        QuickAccessItem(R.drawable.icon_notes, "景点", "attraction_list_route"),
        QuickAccessItem(R.drawable.icon_noodles, "美食", "food_list_route"),
        QuickAccessItem(R.drawable.icon_footprint, "步数", "steps_route")
    )

    val tripItems = listOf(
        TripItem("09:00", TripStatus.COMPLETED, "天安门广场", "北京市东城区长安街1号", "1小时", true, true),
        TripItem("10:20", TripStatus.IN_PROGRESS, "故宫博物院", "北京市东城区景山前街4号", "3小时", false, true),
        TripItem("12:30", TripStatus.UPCOMING, "老北京炸酱面", "北京市东城区方砖厂胡同", "1小时", false, false),
        TripItem("15:00", TripStatus.UPCOMING, "南锣鼓巷", "北京市东城区南锣鼓巷", "2小时", false, true),
        TripItem("17:30", TripStatus.UPCOMING, "北京烤鸭", "北京市东城区全聚德（前门店）", "1.5小时", false, false),
        TripItem("19:00", TripStatus.UPCOMING, "王府井步行街", "北京市东城区王府井大街", "2小时", false, false)
    )

    val discoveryItems = listOf(
        DiscoveryItem(
            type = "搭子",
            title = "你的搭子发现：这是太和殿的容易",
            description = "一共有10个哦！它们代表了皇权的至高无上...",
            imageUrl = R.drawable.image_1  // 替换为实际图片URL
        ),
        DiscoveryItem(
            type = "随记",
            title = "搭子建议：绝佳拍摄角度",
            description = "这个构图超绝，从这个角度拍一张与角楼的合影吧！",
            imageUrl = R.drawable.image_2 // 替换为实际图片URL
        )
    )
}