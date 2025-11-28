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
            title = "你的搭子发现：这是太和殿的脊兽",
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

// 景点页面示例数据
public val dummyAttractions = listOf(
    Attraction(
        name = "故宫博物院",
        type = "历史文化",
        description = "明清两代的皇家宫殿，世界五大宫之首",
        rating = "4.8 (125.0k)",
        distance = "0.8公里",
        duration = "3-4小时",
        price = "60元",
        tags = listOf("必游", "世界遗产", "历史"),
        status = AttractionStatus.IN_PROGRESS,
        time = "10:30",
        crowdLevel = CrowdLevel.HIGH,
        crowdPercentage = "75%",
        image = R.drawable.image_3
    ),
    Attraction(
        name = "南锣鼓巷",
        type = "历史街区",
        description = "北京最古老的街区之一，充满老北京风情",
        rating = "4.5 (67.0k)",
        distance = "2.5公里",
        duration = "2-3小时",
        price = "0元",
        tags = listOf("胡同", "文艺", "美食"),
        status = AttractionStatus.PLANNED,
        time = "15:00",
        crowdLevel = CrowdLevel.MEDIUM,
        crowdPercentage = "45%",
        image = R.drawable.image_4
    ),
    Attraction(
        name = "王府井步行街",
        type = "商业街区",
        description = "北京最繁华的商业街，小吃和购物天堂",
        rating = "4.6 (82.0k)",
        distance = "1.8公里",
        duration = "2-3小时",
        price = "0元",
        tags = listOf("购物", "夜景", "小吃"),
        status = AttractionStatus.PLANNED,
        time = "19:30",
        crowdLevel = CrowdLevel.HIGH,
        crowdPercentage = "70%",
        image = R.drawable.image_5
    ),
    Attraction(
        name = "颐和园",
        type = "园林景观",
        description = "中国现存最大的皇家园林",
        rating = "4.8 (95.0k)",
        distance = "15公里",
        duration = "3-4小时",
        price = "30元",
        tags = listOf("园林", "世界遗产", "休闲"),
        status = AttractionStatus.PLANNED,
        time = "明日9:30",
        crowdLevel = CrowdLevel.MEDIUM,
        crowdPercentage = "50%",
        image = R.drawable.image_6 // 请替换为实际的图片资源ID
    ),

    Attraction(
        name = "天坛公园",
        type = "历史文化",
        description = "明清两代皇帝祭天祈谷的场所",
        rating = "4.7 (78.0k)",
        distance = "3.5公里",
        duration = "2-3小时",
        price = "15元",
        tags = listOf("历史", "世界遗产", "建筑"),
        status = AttractionStatus.PLANNED,
        time = "明日9:30",
        crowdLevel = CrowdLevel.MEDIUM,
        crowdPercentage = "40%",
        image = R.drawable.image_7 // 请替换为实际的图片资源ID
    )

)