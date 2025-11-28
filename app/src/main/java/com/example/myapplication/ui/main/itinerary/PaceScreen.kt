package com.example.myapplication.ui.main.itinerary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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

/**
 * 简化的步数统计页面
 */
@Composable
fun SimplifiedStepsView() {
    val selectedTimeRange = remember { mutableStateOf("今日") }
    val timeRanges = listOf("今日", "本周", "本月")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff9fafb))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // 步数统计区域
            item {
                StepsStatisticsSection()
            }

            // 数据卡片区域
            item {
                DataCardsSection()
            }

            // 排名卡片
            item {
                RankingCard()
            }

            // 时间筛选
            item {
                TimeFilterSection(
                    timeRanges = timeRanges,
                    selectedRange = selectedTimeRange.value,
                    onRangeSelected = { selectedTimeRange.value = it }
                )
            }

            // 成就区域
            item {
                AchievementsSection()
            }

            // 健康提示
            item {
                HealthTipsCard()
            }
        }

        // 底部导航栏
        BottomNavigationBar()
    }
}

/**
 * 步数统计区域
 */
@Composable
private fun StepsStatisticsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff00c3d0)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 返回按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = { /* 返回逻辑 */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.nav_blackback),
                        contentDescription = "返回",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 步数显示
            Box(
                modifier = Modifier.size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hea_loop), // 替换为你的图标资源
                    contentDescription = "图标描述", // 可访问性描述
                    modifier = Modifier
                        .fillMaxSize() // 填充整个Box
                        .padding(8.dp), // 添加内边距，让图标小一点
                    contentScale = ContentScale.Fit // 保持比例适应
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "8,234",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "步",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 进度信息
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "今日目标 10,000 步",
                    color = Color.White,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 进度条
                LinearProgressIndicator(
                    progress = 0.82f,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(8.dp),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "82%",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

/**
 * 数据卡片区域
 */
@Composable
private fun DataCardsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DataCard(
            title = "距离",
            value = "5.8",
            unit = "公里",
            color = Color(0xff00c3d0)
        )

        DataCard(
            title = "卡路里",
            value = "287",
            unit = "千卡",
            color = Color(0xff00c3d0)
        )

        DataCard(
            title = "活动",
            value = "52",
            unit = "分钟",
            color = Color(0xff00c3d0)
        )
    }
}

/**
 * 数据卡片组件
 */
@Composable
private fun DataCard(
    title: String,
    value: String,
    unit: String,
    color: Color
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(110.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.6f)
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = color,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = unit,
                color = color,
                fontSize = 12.sp
            )
        }
    }
}

/**
 * 排名卡片
 */
@Composable
private fun RankingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 排名图标
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.LightGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hea_com),
                        contentDescription = "排名",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column {
                    Text(
                        text = "今日排名",
                        color = Color(0xff101727),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "超越了 68% 的用户",
                        color = Color(0xff495565),
                        fontSize = 14.sp
                    )
                }
            }

            Text(
                text = "#12",
                color = Color(0xff00c3d0),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * 时间筛选区域
 */
@Composable
private fun TimeFilterSection(
    timeRanges: List<String>,
    selectedRange: String,
    onRangeSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            timeRanges.forEach { range ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            color = if (selectedRange == range) Color(0xffffcc00) else Color.Transparent,
                            shape = when (range) {
                                timeRanges.first() -> RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp)
                                timeRanges.last() -> RoundedCornerShape(topEnd = 14.dp, bottomEnd = 14.dp)
                                else -> RoundedCornerShape(0.dp)
                            }
                        )
                        .clickable { onRangeSelected(range) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = range,
                        color = if (selectedRange == range) Color.White else Color(0xff495565),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

/**
 * 成就区域
 */
@Composable
private fun AchievementsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // 标题
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hea_award),
                    contentDescription = "成就",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "我的成就",
                    color = Color(0xff101727),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 成就网格
            val achievements = listOf(
                Achievement("首次达标", R.drawable.icon_heart, true, 100),
                Achievement("连续7天", R.drawable.icon_grass, true, 100),
                Achievement("万步达人", R.drawable.icon_footprint, true, 100),
                Achievement("登山健将", R.drawable.icon_mountain, false, 65),
                Achievement("徒步冠军", R.drawable.icon_champion, false, 30)
            )

            // 每行显示3个成就
            achievements.chunked(3).forEach { rowAchievements ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowAchievements.forEach { achievement ->
                        AchievementCard(achievement = achievement)
                    }
                    // 如果一行不足3个，用空盒子填充
                    repeat(3 - rowAchievements.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

/**
 * 成就数据类
 */
data class Achievement(
    val title: String,
    val icon: Int,
    val unlocked: Boolean,
    val progress: Int // 0-100
)

/**
 * 成就卡片组件
 */
@Composable
private fun AchievementCard(achievement: Achievement) {
    Card(
        modifier = Modifier
            .width(87.dp)
            .height(127.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (achievement.unlocked) Color.White else Color(0xfff9fafb)
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 图标
            Image(
                painter = painterResource(id = achievement.icon),
                contentDescription = achievement.title,
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 标题
            Text(
                text = achievement.title,
                color = if (achievement.unlocked) Color(0xff101727) else Color(0xff697282),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (achievement.unlocked) {
                // 已解锁状态
                Box(
                    modifier = Modifier
                        .background(Color(0xff67dad0), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "已解锁",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            } else {
                // 未解锁状态显示进度
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator(
                        progress = achievement.progress / 100f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        color = Color(0xff00c3d0),
                        trackColor = Color(0xffe5e7eb)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${achievement.progress}%",
                        color = Color(0xff697282),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

/**
 * 健康提示卡片
 */
@Composable
private fun HealthTipsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 提示图标
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.LightGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hea_up),
                    contentDescription = "健康提示",
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "健康小贴士",
                    color = Color(0xff101727),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "保持每天10000步的运动量，可以有效改善心肺功能，增强体质。建议分散在一天中进行，避免一次性完成。",
                    color = Color(0xff495565),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SimplifiedStepsViewPreview() {
    MyApplicationTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SimplifiedStepsView()
        }
    }
}