package com.example.myapplication.ui.main.itinerary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.main.itinerary.BottomNavigationBar
/**
 * 简化的景点详情页
 */
@Composable
fun SimplifiedAttractionDetail() {
    val selectedTab = remember { mutableStateOf(0) }
    val tabs = listOf("介绍", "亮点", "提示")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff9fafb))
    ) {
        // 顶部图片区域
        HeaderImageSection()

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // 基本信息区域
            item {
                BasicInfoSection()
            }

            // 人流量预测区域
            item {
                CrowdPredictionSection()
            }

            // 实用信息卡片
            item {
                UtilityCardsSection()
            }

            // 标签页区域
            item {
                TabSection(
                    tabs = tabs,
                    selectedTab = selectedTab.value,
                    onTabSelected = { selectedTab.value = it }
                )

                // 内容区域
                when (selectedTab.value) {
                    0 -> IntroductionContent()
                    1 -> HighlightsContent()
                    2 -> TipsContent()
                }
            }
        }

        // 底部导航栏
        BottomNavigationBar()
    }
}

/**
 * 顶部图片区域
 */
@Composable
private fun HeaderImageSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        // 背景图片
        Image(
            painter = painterResource(id = R.drawable.image_8), // 替换为实际图片
            contentDescription = "故宫博物院",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 顶部操作栏
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 返回按钮
            IconButton(
                onClick = { /* 返回逻辑 */ },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nav_blackback),
                    contentDescription = "返回",
                    modifier = Modifier.size(24.dp)
                )
            }

            // 分享按钮
            IconButton(
                onClick = { /* 分享逻辑 */ },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nav_share),
                    contentDescription = "分享",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // 底部标签
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            StatusBadge(
                text = "景点",
                backgroundColor = Color(0xff00c3d0)
            )
        }
    }
}

/**
 * 基本信息区域
 */
@Composable
private fun BasicInfoSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // 景点名称
        Text(
            text = "故宫博物院",
            color = Color(0xff101727),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 评分信息
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.nav_star),
                contentDescription = "评分",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "4.8",
                color = Color(0xff101727),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "(126,840条评论)",
                color = Color(0xff697282),
                fontSize = 14.sp
            )
        }

        // 地址和距离
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.nav_1),
                contentDescription = "位置",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "北京市东城区景山前街4号",
                color = Color(0xff495565),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "· 2.3公里",
                color = Color(0xffff6800),
                fontSize = 14.sp
            )
        }
    }
}

/**
 * 人流量预测区域
 */
@Composable
private fun CrowdPredictionSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xfffff8f0)
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // 标题和状态
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.att_peo),
                        contentDescription = "人流量",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "当前人流量",
                        color = Color(0xff101727),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                StatusBadge(
                    text = "拥挤",
                    backgroundColor = Color(0xffffe2e2),
                    textColor = Color(0xffe7000b)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 最佳游览时间
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "💡 最佳游览时间：",
                    color = Color(0xff495565),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "10:00-11:00",
                    color = Color(0xfff44900),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 人流量预测标题
            Text(
                text = "今日人流量预测",
                color = Color(0xff495565),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // 人流量图表（简化版）
            CrowdChart()
        }
    }
}

/**
 * 人流量图表（简化版）
 */
@Composable
private fun CrowdChart() {
    val timeSlots = listOf(
        "09:00" to 80,
        "10:00" to 45,
        "11:00" to 70,
        "12:00" to 85,
        "13:00" to 90,
        "14:00" to 65,
        "15:00" to 50,
        "16:00" to 40
    )

    Column {
        // 图表柱状图
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            timeSlots.forEach { (_, heightPercentage) ->
                Box(
                    modifier = Modifier
                        .width(28.dp)
                        .height((heightPercentage * 0.6).dp)
                        .background(
                            color = if (heightPercentage > 70) Color(0xffff6b6b)
                            else if (heightPercentage > 40) Color(0xffffa726)
                            else Color(0xff4caf50),
                            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 时间标签
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            timeSlots.forEach { (time, _) ->
                Text(
                    text = time,
                    color = Color(0xff697282),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(28.dp)
                )
            }
        }
    }
}

/**
 * 实用信息卡片区域
 */
@Composable
private fun UtilityCardsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        UtilityCard(
            icon = R.drawable.att_clock,
            title = "开放时间",
            content = "08:30 - 17:00"
        )

        UtilityCard(
            icon = R.drawable.att_dollar,
            title = "门票价格",
            content = "60元/人"
        )

        UtilityCard(
            icon = R.drawable.icon_notes,
            title = "建议游览",
            content = "3-4小时"
        )
    }
}

/**
 * 实用信息卡片
 */
@Composable
private fun UtilityCard(icon: Int, title: String, content: String) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                color = Color(0xff697282),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = content,
                color = Color(0xff101727),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 标签页区域
 */
@Composable
private fun TabSection(
    tabs: List<String>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
        ) {
            tabs.forEachIndexed { index, tab ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            color = if (selectedTab == index) Color(0xfff0fdf4) else Color.Transparent,
                            shape = when (index) {
                                0 -> RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp)
                                tabs.size - 1 -> RoundedCornerShape(topEnd = 14.dp, bottomEnd = 14.dp)
                                else -> RoundedCornerShape(0.dp)
                            }
                        )
                        .clickable { onTabSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab,
                        color = if (selectedTab == index) Color(0xff00c3d0) else Color(0xff0a0a0a),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

/**
 * 介绍内容
 */
@Composable
private fun IntroductionContent() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "故宫博物院建立于1925年，是在明清两代皇宫——紫禁城的基础上建立起来的中国综合性博物馆。故宫占地面积72万平方米，建筑面积约15万平方米，有大小宫殿七十多座，房屋九千余间。",
                color = Color(0xff495565),
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 示例图片
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                // 这里应该显示实际图片
                Image(
                    painter = painterResource(id = R.drawable.image_9), // 替换为你的图标资源
                    contentDescription = "故宫图片", // 可访问性描述
                    modifier = Modifier
                        .fillMaxSize(), // 填充整个Box
                        //.padding(8.dp), // 添加内边距，让图标小一点
                    contentScale = ContentScale.Fit // 保持比例适应
                )
            }
        }
    }
}

/**
 * 亮点内容
 */
@Composable
private fun HighlightsContent() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "景点亮点内容区域",
                color = Color(0xff495565),
                fontSize = 16.sp
            )
            // 这里添加具体的亮点内容
        }
    }
}

/**
 * 提示内容
 */
@Composable
private fun TipsContent() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "游览提示内容区域",
                color = Color(0xff495565),
                fontSize = 16.sp
            )
            // 这里添加具体的提示内容
        }
    }
}

/**
 * 状态标签组件
 */
@Composable
private fun StatusBadge(
    text: String,
    backgroundColor: Color,
    textColor: Color = Color.White
) {
    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp
        )
    }
}



@Preview(showBackground = true)
@Composable
fun SimplifiedAttractionDetailPreview() {
    MyApplicationTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SimplifiedAttractionDetail()
        }
    }
}