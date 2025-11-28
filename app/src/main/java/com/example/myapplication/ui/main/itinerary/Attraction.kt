package com.example.myapplication.ui.main.itinerary

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.onboarding.GeneratingPage
import com.example.myapplication.ui.theme.MyApplicationTheme

/**
 * 简化的行程主页面
 */
@Composable
fun SimplifiedItineraryView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff9fafb))
    ) {
        // 统计信息卡片
        StatisticsSection()

        // 行程列表
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(dummyAttractions) { attraction ->
                AttractionCard(attraction = attraction)
            }
        }

        // 底部导航栏
        BottomNavigationBar()
    }
}

/**
 * 统计信息区域
 */
@Composable
private fun StatisticsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(
            count = "6",
            label = "总景点",
            backgroundColor = Color(0xffffffff),
            textColor = Color(0xff101727)
        )
        StatItem(
            count = "5",
            label = "已规划",
            backgroundColor = Color(0xffeffeff),
            textColor = Color(0xff00b7c3)
        )
        StatItem(
            count = "1",
            label = "已完成",
            backgroundColor = Color(0xfff0fdf4),
            textColor = Color(0xff00a63d)
        )
    }
}

/**
 * 统计项组件
 */
@Composable
private fun StatItem(
    count: String,
    label: String,
    backgroundColor: Color,
    textColor: Color
) {
    Column(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(14.dp))
            .border(1.dp, Color(0xfff2f4f6), RoundedCornerShape(14.dp))
            .padding(16.dp)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = count,
            color = textColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = textColor,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * 景点卡片组件
 */
@Composable
private fun AttractionCard(attraction: Attraction) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // 图片区域
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray) // 这里应该用实际图片
            ) {
                // 状态标签
                StatusBadge(
                    status = attraction.status,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopStart)
                )

                // 时间标签
                TimeBadge(
                    time = attraction.time,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopEnd)
                )

                // 景点信息
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = attraction.name,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = attraction.type,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )
                }
            }

            // 详细信息区域
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // 描述
                Text(
                    text = attraction.description,
                    color = Color(0xff495565),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // 基本信息行
                InfoRow(attraction = attraction)

                // 标签区域
                TagRow(tags = attraction.tags)

                // 人流信息
                CrowdInfo(
                    level = attraction.crowdLevel,
                    percentage = attraction.crowdPercentage
                )

                // 按钮区域
                ActionButtons()
            }
        }
    }
}

/**
 * 状态标签
 */
@Composable
private fun StatusBadge(status: AttractionStatus, modifier: Modifier = Modifier) {
    val backgroundColor = when (status) {
        AttractionStatus.IN_PROGRESS -> Color(0xfbff8d28)
        AttractionStatus.PLANNED -> Color(0xff00c3d0)
        AttractionStatus.COMPLETED -> Color(0xff00a63d)
    }

    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.displayName,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

/**
 * 时间标签
 */
@Composable
private fun TimeBadge(time: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(100.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // 这里可以添加时间图标
        Text(
            text = time,
            color = Color(0xff101727),
            fontSize = 14.sp
        )
    }
}

/**
 * 信息行
 */
@Composable
private fun InfoRow(attraction: Attraction) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 评分
        InfoItem(
            value = attraction.rating,
            label = "评分",
            showLabel = false
        )

        // 距离
        InfoItem(
            value = attraction.distance,
            label = "距离"
        )

        // 预计时间
        InfoItem(
            value = attraction.duration,
            label = "预计时间"
        )

        // 价格
        InfoItem(
            value = attraction.price,
            label = "价格"
        )
    }
}

/**
 * 信息项
 */
@Composable
private fun InfoItem(value: String, label: String, showLabel: Boolean = true) {
    Column {
        Text(
            text = value,
            color = Color(0xff101727),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        if (showLabel) {
            Text(
                text = label,
                color = Color(0xff99a1ae),
                fontSize = 12.sp
            )
        }
    }
}

/**
 * 标签行
 */
@Composable
private fun TagRow(tags: List<String>) {
    Row(
        modifier = Modifier.padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            Box(
                modifier = Modifier
                    .background(Color(0xfff3f4f6), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = tag,
                    color = Color(0xff354152),
                    fontSize = 12.sp
                )
            }
        }
    }
}

/**
 * 人流信息
 */
@Composable
private fun CrowdInfo(level: CrowdLevel, percentage: String) {
    val backgroundColor = when (level) {
        CrowdLevel.HIGH -> Color(0xffffe2e2)
        CrowdLevel.MEDIUM -> Color(0xfffef9c2)
        CrowdLevel.LOW -> Color(0xfff0fdf4)
    }

    val textColor = when (level) {
        CrowdLevel.HIGH -> Color(0xffe7000b)
        CrowdLevel.MEDIUM -> Color(0xffd08700)
        CrowdLevel.LOW -> Color(0xff00a63d)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "当前人流：${level.displayName}",
            color = textColor,
            fontSize = 14.sp
        )

        Text(
            text = percentage,
            color = textColor,
            fontSize = 12.sp
        )
    }
}

/**
 * 操作按钮
 */
@Composable
private fun ActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 查看详情按钮
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xff0a0a0a)
            ),
            shape = RoundedCornerShape(14.dp),
            border = ButtonDefaults.outlinedButtonBorder
        ) {
            Text("查看详情")
        }

        // 导航按钮
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff00c3d0)
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("导航")
        }
    }
}

/**
 * 底部导航栏
 */
@Composable
private fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavItem("行程", isSelected = true)
        BottomNavItem("搭子", isSelected = false)
        BottomNavItem("回忆", isSelected = false)
    }
}

/**
 * 底部导航项
 */
@Composable
private fun BottomNavItem(text: String, isSelected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 这里应该使用图标
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = text,
            color = if (isSelected) Color(0xff00c3d0) else Color(0xff99a1ae),
            fontSize = 12.sp
        )
    }
}

// 数据模型
data class Attraction(
    val name: String,
    val type: String,
    val description: String,
    val rating: String,
    val distance: String,
    val duration: String,
    val price: String,
    val tags: List<String>,
    val status: AttractionStatus,
    val time: String,
    val crowdLevel: CrowdLevel,
    val crowdPercentage: String
)

enum class AttractionStatus(val displayName: String) {
    IN_PROGRESS("进行中"),
    PLANNED("计划中"),
    COMPLETED("已完成")
}

enum class CrowdLevel(val displayName: String) {
    HIGH("拥挤"),
    MEDIUM("适中"),
    LOW("空闲")
}

// 示例数据
private val dummyAttractions = listOf(
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
        crowdPercentage = "75%"
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
        crowdPercentage = "45%"
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
        crowdPercentage = "70%"
    )
)

@Preview(showBackground = true)
@Composable
fun SimplifiedItineraryViewPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SimplifiedItineraryView()
        }
    }
}

