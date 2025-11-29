package com.example.myapplication.ui.main.memories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.main.itinerary.main.BottomNavigationBar
/**
 * 旅行应用主页面 - 包含旅行日报和旅行回忆录两个Tab
 */
@Composable
fun CodiaMainView() {
    // 修复：使用 remember 和 mutableStateOf 来管理状态
    var selectedTab by remember { mutableStateOf(1) } // 0: 旅行日报, 1: 旅行回忆录

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xfff9fafb)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 顶部背景区域 - 根据选中的Tab动态切换背景
            TripHeader(
                title = "北京秋日之旅",
                dateRange = "2025年11月15日 - 11月17日",
                backgroundImageRes = if (selectedTab == 0) R.drawable.image_14 else R.drawable.image_10,
                backButtonRes = R.drawable.nav_whiteback
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Tab切换
                TripTabs(
                    selectedTab = selectedTab,
                    onTabSelected = { newTab -> selectedTab = newTab }
                )

                // 根据选中的Tab显示不同内容
                when (selectedTab) {
                    0 -> TripDiaryContent()
                    1 -> TravelMemoriesContent()
                }
            }

            // 底部导航栏
            BottomNavigationBar()
        }
    }
}

/**
 * 顶部背景区域
 */
@Composable
private fun TripHeader(
    title: String,
    dateRange: String,
    backgroundImageRes: Int,
    backButtonRes: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(255.dp)
    ) {
        // 背景图片
        Image(
            painter = painterResource(id = backgroundImageRes),
            contentDescription = "旅行背景图",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 返回按钮
        Image(
            painter = painterResource(id = backButtonRes),
            contentDescription = "返回",
            modifier = Modifier
                .size(40.dp)
                .offset(x = 16.dp, y = 48.dp)
        )

        // 标题和日期
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Text(
                text = dateRange,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

/**
 * 旅行回忆录内容
 */
@Composable
private fun TravelMemoriesContent() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        item {
            // 回忆录卡片
            MemoryCard()
        }
    }
}

/**
 * 回忆录卡片
 */
@Composable
private fun MemoryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // 回忆录封面
            MemoryCover()

            // 回忆录信息
            MemoryInfo()

            // 统计数据
            MemoryStats()

            // 操作按钮
            MemoryActions()
        }
    }
}

/**
 * 回忆录封面
 */
@Composable
private fun MemoryCover() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(184.dp)
    ) {
        // 背景图片
        Image(
            painter = painterResource(id = R.drawable.image_10),
            contentDescription = "回忆录封面",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 播放按钮
        Image(
            painter = painterResource(id = R.drawable.me_start),
            contentDescription = "播放视频",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.Center)
        )

        // 标题信息
        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            color = Color(0x33ffffff) // 半透明白色
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = "北京秋日之旅 · 回忆录",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = "3天精彩瞬间 · 3分30秒",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

/**
 * 回忆录信息
 */
@Composable
private fun MemoryInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_photos),
                contentDescription = "相册图标",
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "自动生成的旅行回忆",
                style = MaterialTheme.typography.titleSmall,
                color = Color(0xff101727)
            )
        }

        Text(
            text = "根据你的旅行照片、足迹和搭子的陪伴，自动生成专属回忆视频",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xff495565),
            lineHeight = 20.sp
        )
    }
}

/**
 * 统计数据
 */
@Composable
private fun MemoryStats() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // 照片数量
        StatItem(
            value = "125",
            label = "照片",
            modifier = Modifier.weight(1f)
        )

        // 足迹数量
        StatItem(
            value = "8",
            label = "足迹",
            modifier = Modifier.weight(1f)
        )

        // 视频时长
        StatItem(
            value = "3:30",
            label = "时长",
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * 统计项目
 */
@Composable
private fun StatItem(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xff101727)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xff697282)
        )
    }
}

/**
 * 回忆录操作按钮
 */
@Composable
private fun MemoryActions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 编辑视频按钮
        Button(
            onClick = { /* 编辑视频操作 */ },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffffcc00)
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.me_edit),
                contentDescription = "编辑",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "编辑视频",
                color = Color.White
            )
        }

        // 分享按钮
        IconButton(
            onClick = { /* 分享操作 */ },
            modifier = Modifier.size(44.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.me_share),
                contentDescription = "分享",
                modifier = Modifier.size(24.dp)
            )
        }

        // 更多操作按钮
        IconButton(
            onClick = { /* 更多操作 */ },
            modifier = Modifier.size(44.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.me_download),
                contentDescription = "更多",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * 内容区域
 */
@Composable
private fun TripDiaryContent() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(3) { index ->
            when (index) {
                0 -> TripDayCard(
                    day = "Day 1",
                    subtitle = "北京初印象",
                    date = "11月15日",
                    steps = "15000",
                    locations = listOf("天安门广场", "故宫博物院", "景山公园"),
                    comment = "今天的你活力满满！走了15000步，太棒了！",
                    dayImageRes1 = R.drawable.image_15,
                    dayImageRes2 = R.drawable.image_16,
                    dayImageRes3= R.drawable.image_17,// 每日图片
                    locationIconRes = R.drawable.me_location, // 地点图标
                    commentIconRes = R.drawable.icon_heart, // 评论图标
                    editIconRes = R.drawable.me_edit, // 编辑图标
                    shareIconRes = R.drawable.me_share // 分享图标
                )
                1 -> TripDayCard(
                    day = "Day 2",
                    subtitle = "长城之旅",
                    date = "11月16日",
                    steps = "18500",
                    locations = listOf("八达岭长城", "明十三陵"),
                    comment = "登顶长城的你太帅了！给你点赞~",
                    dayImageRes1 = R.drawable.image_16,
                    dayImageRes2 = R.drawable.image_17,
                    dayImageRes3= R.drawable.image_15,// 每日图片
                    locationIconRes = R.drawable.me_location, // 地点图标
                    commentIconRes = R.drawable.icon_flower, // 评论图标
                    editIconRes = R.drawable.me_edit, // 编辑图标
                    shareIconRes = R.drawable.me_share
                )// 分享图标
                2 -> TripDayCard(
                    day = "Day 3",
                    subtitle = "美食探索",
                    date = "11月17日",
                    steps = "12000",
                    locations = listOf("王府井小吃街", "南锣鼓巷", "簋街"),
                    comment = "吃货模式全开！品尝了10种美食，满足！",
                    dayImageRes1 = R.drawable.image_18,
                    dayImageRes2 = R.drawable.image_19,
                    dayImageRes3= R.drawable.image_20,// 每日图片
                    locationIconRes = R.drawable.me_location, // 地点图标
                    commentIconRes = R.drawable.icon_noodles, // 评论图标
                    editIconRes = R.drawable.me_edit, // 编辑图标
                    shareIconRes = R.drawable.me_share
                )// 分享图标

            }
        }
    }
}

/**
 * Tab切换组件
 */
@Composable
private fun TripTabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(36.dp),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp,
        color = Color.White
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // 旅行日报Tab
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(0) },
                shape = RoundedCornerShape(14.dp),
                color = if (selectedTab == 0) Color(0xffffcc00) else Color.Transparent
            ) {
                Text(
                    text = "旅行日报",
                    modifier = Modifier.padding(vertical = 4.dp),
                    textAlign = TextAlign.Center,
                    color = if (selectedTab == 0) Color.White else Color(0xff0a0a0a)
                )
            }

            // 旅行回忆录Tab
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(1) },
                shape = RoundedCornerShape(14.dp),
                color = if (selectedTab == 1) Color(0xffffcc00) else Color.Transparent
            ) {
                Text(
                    text = "旅行回忆录",
                    modifier = Modifier.padding(vertical = 4.dp),
                    textAlign = TextAlign.Center,
                    color = if (selectedTab == 1) Color.White else Color(0xff0a0a0a)
                )
            }
        }
    }
}

/**
 * 单日旅行卡片
 */
@Composable
private fun TripDayCard(
    day: String,
    subtitle: String,
    date: String,
    steps: String,
    locations: List<String>,
    comment: String,
    dayImageRes1: Int,
    dayImageRes2: Int,
    dayImageRes3: Int,
    locationIconRes: Int,
    commentIconRes: Int,
    editIconRes: Int,
    shareIconRes: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // 头部信息
            DayHeader(day, subtitle, date, steps)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 第一张图片
                Image(
                    painter = painterResource(id = dayImageRes1),
                    contentDescription = "$day 的旅行图片1",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                )

                // 第二张图片
                Image(
                    painter = painterResource(id = dayImageRes2),
                    contentDescription = "$day 的旅行图片2",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                )

                // 第三张图片
                Image(
                    painter = painterResource(id = dayImageRes3),
                    contentDescription = "$day 的旅行图片3",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            // 地点标签
            LocationTags(locations, locationIconRes)

            // 评论区域
            CommentSection(comment, commentIconRes)

            // 操作按钮
            ActionButtons(editIconRes, shareIconRes)
        }
    }
}

/**
 * 日记头部信息
 */
@Composable
private fun DayHeader(day: String, subtitle: String, date: String, steps: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "$day · $subtitle",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xff101727)
            )
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff697282)
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "步数",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff697282)
            )
            Text(
                text = steps,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xffffbf00)
            )
        }
    }
}

/**
 * 地点标签
 */
@Composable
private fun LocationTags(locations: List<String>, locationIconRes: Int) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        locations.forEach { location ->
            Surface(
                shape = RoundedCornerShape(50),
                color = Color(0xffeff6ff)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = locationIconRes),
                        contentDescription = "地点图标",
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = location,
                        color = Color(0xff1347e5),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

/**
 * 评论区域
 */
@Composable
private fun CommentSection(comment: String, commentIconRes: Int) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xfffff7ed)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = commentIconRes),
                contentDescription = "评论图标",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = comment,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff354152)
            )
        }
    }
}

/**
 * 操作按钮
 */
@Composable
private fun ActionButtons(editIconRes: Int, shareIconRes: Int) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 编辑按钮
        OutlinedButton(
            onClick = { /* 编辑操作 */ },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp)
        ) {
            Image(
                painter = painterResource(id = editIconRes),
                contentDescription = "编辑",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("编辑")
        }

        // 分享按钮
        OutlinedButton(
            onClick = { /* 分享操作 */ },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp)
        ) {
            Image(
                painter = painterResource(id = shareIconRes),
                contentDescription = "分享",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("分享")
        }
    }
}

/**
 * 底部导航栏
 */
@Composable
private fun BottomNavigationBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 行程按钮
            BottomNavItem("行程", Color(0xff99a1ae))

            // 搭子按钮
            BottomNavItem("搭子", Color(0xff99a1ae))

            // 回忆按钮（选中状态）
            BottomNavItem("回忆", Color(0xff00c3d0))
        }
    }
}

/**
 * 底部导航项
 */
@Composable
private fun BottomNavItem(text: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // 图标占位
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray.copy(alpha = 0.2f))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CodiaMainViewPreview() {
    MyApplicationTheme() {
        CodiaMainView()
    }
}