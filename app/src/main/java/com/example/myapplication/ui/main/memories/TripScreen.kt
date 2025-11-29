package com.example.myapplication.ui.main.memories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.main.itinerary.main.BottomNavigationBar
/**
 * 简洁版行程页面
 */
@Composable
fun TripScreen() {
    val trips = listOf(
        Trip(
            id = 1,
            title = "北京秋日之旅",
            location = "北京",
            dateRange = "11月15日 - 11月17日",
            duration = "3天",
            photoCount = 125,
            year = "2025",
            imageRes = R.drawable.image_10
        ),
        Trip(
            id = 2,
            title = "上海美食探索",
            location = "上海",
            dateRange = "10月10日 - 10月13日",
            duration = "4天",
            photoCount = 98,
            year = "2025",
            imageRes = R.drawable.image_11
        ),
        Trip(
            id = 3,
            title = "杭州西湖漫步",
            location = "杭州",
            dateRange = "9月5日 - 9月6日",
            duration = "2天",
            photoCount = 76,
            year = "2025",
            imageRes = R.drawable.image_12
        ),
        Trip(
            id = 4,
            title = "成都熊猫基地",
            location = "成都",
            dateRange = "12月20日 - 12月24日",
            duration = "5天",
            year = "2024",
            photoCount = 156,
            imageRes = R.drawable.image_13
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff9fafb))
    ) {
        // 顶部横幅
        TopBanner()

        // 统计卡片
        StatsSection()

        Spacer(modifier = Modifier.height(16.dp))

        // 开始新旅程按钮
        StartNewTripButton()

        Spacer(modifier = Modifier.height(16.dp))

        // 行程列表
        TripList(trips = trips)

        // 底部导航
        BottomNavigationBar()
    }
}

@Composable
private fun TopBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(Color(0xffffbf00))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp)
        ) {
            Text(
                text = "我的旅程",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "珍藏每一段美好时光",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun StatsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            count = "4",
            label = "旅程",
            iconRes = R.drawable.me_location
        )
        StatCard(
            count = "455",
            label = "照片",
            iconRes = R.drawable.me_photo
        )
        StatCard(
            count = "14",
            label = "天数",
            iconRes = R.drawable.me_note
        )
    }
}

@Composable
private fun StatCard(
    count: String,
    label: String,
    iconRes: Int
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(117.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = count,
                color = Color(0xff101727),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = label,
                color = Color(0xff697282),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun StartNewTripButton() {
    Card(
        onClick = { /* 开始新旅程 */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xffffcc00))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.me_add),
                contentDescription = "开始新旅程",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "开始新旅程",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun TripList(trips: List<Trip>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .width(100.dp)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(trips) { trip ->
            TripCard(trip = trip)
        }
    }
}

@Composable
private fun TripCard(trip: Trip) {
    Card(
        onClick = { /* 查看行程详情 */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(273.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 行程图片
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(192.dp)
            ) {
                Image(
                    painter = painterResource(id = trip.imageRes),
                    contentDescription = trip.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // 图片上的信息
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // 年份和时长标签
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InfoChip(text = trip.year)
                        InfoChip(text = "${trip.duration}")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 行程标题
                    Text(
                        text = trip.title,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // 行程详情
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(81.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 地点和照片信息
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LocationInfo(location = trip.location)
                        PhotoInfo(photoCount = trip.photoCount)
                    }

                    // 箭头
                    Text(
                        text = "→",
                        color = Color(0xffffbf00),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // 日期范围
                Text(
                    text = trip.dateRange,
                    color = Color(0xff99a1ae),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun InfoChip(text: String) {
    Surface(
        shape = RoundedCornerShape(26271900.dp), // 胶囊形状
        color = Color.White.copy(alpha = 0.9f)
    ) {
        Text(
            text = text,
            color = Color(0xff101727),
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun LocationInfo(location: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.me_location),
            contentDescription = "地点",
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = location,
            color = Color(0xff697282),
            fontSize = 14.sp
        )
    }
}

@Composable
private fun PhotoInfo(photoCount: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.me_photo),
            contentDescription = "照片",
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = "$photoCount 张",
            color = Color(0xff697282),
            fontSize = 14.sp
        )
    }
}

@Composable
private fun BottomNavigationBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp),
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 29.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                iconRes = R.drawable.nav_1,
                text = "行程",
                isSelected = false
            )
            BottomNavItem(
                iconRes = R.drawable.nav_1,
                text = "搭子",
                isSelected = false
            )
            BottomNavItem(
                iconRes = R.drawable.nav_1,
                text = "回忆",
                isSelected = true
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    iconRes: Int,
    text: String,
    isSelected: Boolean
) {
    val color = if (isSelected) Color(0xff00c3d0) else Color(0xff99a1ae)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = text,
            color = color,
            fontSize = 12.sp
        )
    }
}

// 数据类
data class Trip(
    val id: Int,
    val title: String,
    val location: String,
    val dateRange: String,
    val duration: String,
    val photoCount: Int,
    val year: String,
    val imageRes: Int
)

@Preview(showBackground = true)
@Composable
fun CodiaMainViewPreview2() {
    MyApplicationTheme() {
        TripScreen()
    }
}