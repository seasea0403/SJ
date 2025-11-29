package com.example.myapplication.ui.main.companion.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.main.itinerary.main.BottomNavigationBar

/**
 * 简化版任务成就页面
 */
@Composable
fun AchievementScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xfff9fafb)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 顶部标题栏
            HeaderSection()

            // 内容区域
            ContentSection()

            // 底部导航栏
            BottomNavigationBar()
        }
    }
}

@Composable
private fun HeaderSection() {
    Surface(
        color = Color(0xffffbf00),
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 17.dp, top = 21.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.nav_whiteback),
                contentDescription = "返回",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = "任务与成就",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "完成任务获取积分，解锁专属成就",
                fontSize = 13.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ContentSection() {
    val selectedTab = remember { mutableStateOf(0) }
    val tabs = listOf("进行中", "已完成", "成就墙")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // 选项卡
        TabSection(tabs, selectedTab.value) { selectedTab.value = it }

        Spacer(modifier = Modifier.height(24.dp))

        // 根据选中的选项卡显示不同内容
        when (selectedTab.value) {
            0 -> InProgressTasksSection()
            1 -> CompletedTasksSection()
            2 -> AchievementWallSection()
        }
    }
}

@Composable
private fun TabSection(tabs: List<String>, selectedIndex: Int, onTabSelected: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tabs.forEachIndexed { index, tab ->
                TabItem(
                    text = tab,
                    isSelected = index == selectedIndex,
                    onClick = { onTabSelected(index) }
                )
            }
        }
    }
}

@Composable
private fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(110.dp)
            .height(35.dp)
            .padding(4.dp)
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) Color(0xff00c3d0) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color(0xff0a0a0a),
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

// 进行中任务部分
@Composable
private fun InProgressTasksSection() {
    val tasks = listOf(
        TaskItem(
            iconRes = R.drawable.nav_takephoto,
            title = "美食摄影师",
            description = "在旅途中拍摄5张美食照片",
            progress = 3,
            total = 5,
            points = 20,
            isCompleted = false
        ),
        TaskItem(
            iconRes = R.drawable.nav_pace,
            title = "步行达人",
            description = "今日步行超过1万步",
            progress = 8200,
            total = 10000,
            points = 15,
            isCompleted = false
        ),
        TaskItem(
            iconRes = R.drawable.nav_1,
            title = "探索家",
            description = "打卡3个新景点",
            progress = 2,
            total = 3,
            points = 30,
            isCompleted = false
        )
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(tasks) { task ->
            TaskCard(task = task)
        }
    }
}

// 已完成任务部分
@Composable
private fun CompletedTasksSection() {
    val completedTasks = listOf(
        CompletedTaskItem(
            iconRes = R.drawable.com_bingo,
            title = "早起的鸟儿",
            description = "早上8点前开始行程",
            completedTime = "完成于 今天",
            points = 10
        ),
        CompletedTaskItem(
            iconRes = R.drawable.com_bingo,
            title = "社交达人",
            description = "分享1次旅行动态",
            completedTime = "完成于 昨天",
            points = 15
        )
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(completedTasks) { task ->
            CompletedTaskCard(task = task)
        }
    }
}

@Composable
private fun CompletedTaskCard(task: CompletedTaskItem) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.Top
        ) {
            // 任务图标
            Icon(
                painter = painterResource(id = task.iconRes),
                contentDescription = task.title,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 任务信息
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff101727)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = task.description,
                    fontSize = 14.sp,
                    color = Color(0xff697282)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 完成时间和积分
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = task.completedTime,
                        fontSize = 14.sp,
                        color = Color(0xff99a1ae)
                    )

                    // 已完成积分徽章
                    Surface(
                        color = Color(0xffdcfce7),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "已获得 +${task.points} 积分",
                            color = Color(0xff008235),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

// 成就墙部分
@Composable
private fun AchievementWallSection() {
    val achievements = listOf(
        AchievementWallItem(
            iconRes = R.drawable.icon_earth,
            title = "首次出境游",
            description = "完成第一次出境旅行",
            unlockDate = "2024-08-15",
            isUnlocked = true
        ),
        AchievementWallItem(
            iconRes = R.drawable.icon_friedprawn,
            title = "美食猎人",
            description = "品尝100种不同美食",
            unlockDate = "2025-10-20",
            isUnlocked = true
        ),
        AchievementWallItem(
            iconRes = R.drawable.icon_graycramer,
            title = "摄影大师",
            description = "拍摄500张旅行照片",
            progress = 387,
            total = 500,
            isUnlocked = false
        ),
        AchievementWallItem(
            iconRes = R.drawable.icon_footprint,
            title = "徒步勇士",
            description = "累计步行100公里",
            progress = 73,
            total = 100,
            isUnlocked = false
        ),
        AchievementWallItem(
            iconRes = R.drawable.icon_mountain,
            title = "五岳征服者",
            description = "登顶中国五岳",
            progress = 2,
            total = 5,
            isUnlocked = false
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(achievements) { achievement ->
            AchievementWallCard(achievement = achievement)
        }
    }
}

@Composable
private fun AchievementWallCard(achievement: AchievementWallItem) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(174.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 成就图标
            Icon(
                painter = painterResource(id = achievement.iconRes),
                contentDescription = achievement.title,
                modifier = Modifier.size(48.dp),
                tint = if (achievement.isUnlocked) Color.Unspecified else Color(0xffcccccc)
            )

            // 成就信息
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = achievement.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (achievement.isUnlocked) Color(0xff101727) else Color(0xff99a1ae)
                )

                Text(
                    text = achievement.description,
                    fontSize = 12.sp,
                    color = Color(0xff697282)
                )
            }

            // 底部状态区域
            if (achievement.isUnlocked) {
                // 已解锁：显示解锁日期
                Surface(
                    color = Color(0xffffecd4),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = achievement.unlockDate ?: "",
                        color = Color(0xffc93400),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            } else {
                // 未解锁：显示进度
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    LinearProgressIndicator(
                        progress = achievement.progress?.toFloat()?.div(achievement.total ?: 1) ?: 0f,
                        color = Color(0xffffbf00),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp),
                        trackColor = Color(0xfff0f0f0)
                    )

                    Text(
                        text = "${achievement.progress}/${achievement.total}",
                        fontSize = 12.sp,
                        color = Color(0xff697282)
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskCard(task: TaskItem) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // 任务图标
                Icon(
                    painter = painterResource(id = task.iconRes),
                    contentDescription = task.title,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // 任务信息
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = task.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff101727)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = task.description,
                        fontSize = 14.sp,
                        color = Color(0xff697282)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 进度和积分
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${task.progress} / ${task.total}",
                            fontSize = 14.sp,
                            color = Color(0xff495565)
                        )

                        PointsBadge(points = task.points)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 进度条
                    LinearProgressIndicator(
                        progress = task.progress.toFloat() / task.total,
                        color = Color(0xffffbf00),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        trackColor = Color(0xfff0f0f0)
                    )
                }
            }
        }
    }
}

@Composable
private fun PointsBadge(points: Int) {
    Surface(
        color = Color(0xffffecd4),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "+$points 积分",
            color = Color(0xffc93400),
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

data class TaskItem(
    val iconRes: Int,
    val title: String,
    val description: String,
    val progress: Int,
    val total: Int,
    val points: Int,
    val isCompleted: Boolean
)

data class CompletedTaskItem(
    val iconRes: Int,
    val title: String,
    val description: String,
    val completedTime: String,
    val points: Int
)

data class AchievementWallItem(
    val iconRes: Int,
    val title: String,
    val description: String,
    val unlockDate: String? = null,  // 仅用于已解锁成就
    val progress: Int? = null,       // 仅用于未解锁成就
    val total: Int? = null,          // 仅用于未解锁成就
    val isUnlocked: Boolean
)

@Preview(showBackground = true)
@Composable
fun AchievementScreenPreview() {
    MyApplicationTheme {
        AchievementScreen()
    }
}