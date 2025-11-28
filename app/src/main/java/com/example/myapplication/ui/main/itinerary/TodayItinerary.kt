package com.example.travelapp // 请替换成你的实际包名

import android.R.attr.icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.main.itinerary.BottomNavigationBar

import com.example.myapplication.ui.main.itinerary.data.DiscoveryItem
import com.example.myapplication.ui.main.itinerary.data.QuickAccessItem
import com.example.myapplication.ui.main.itinerary.data.TripItem
import com.example.myapplication.ui.main.itinerary.data.TripStatus
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.main.itinerary.data.MockData
import com.example.myapplication.ui.onboarding.GeneratingPage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Int // 使用Int来引用drawable资源ID
)

@Composable
fun MainScreen() {
    val navController = rememberNavController() // 用于导航功能，这里先声明

    // 底部导航栏的各项
    val items = listOf(
        BottomNavItem(
            name = stringResource(R.string.nav_trip),
            route = "trip_route",
            icon = R.drawable.nav_1 // 替换成你的行程图标
        ),
        BottomNavItem(
            name = stringResource(R.string.nav_buddy),
            route = "buddy_route",
            icon = R.drawable.nav_2 // 替换成你的搭子图标
        ),
        BottomNavItem(
            name = stringResource(R.string.nav_note),
            route = "note_route",
            icon = R.drawable.nav_3 // 替换成你的随记图标
        )
    )

    // Scaffold 是 Material Design 布局结构的基础组件
    Scaffold(
        bottomBar = { // bottomBar 参数就是用来放置底部导航栏的
            BottomNavigationBar(
                items = items,
                navController = navController,
                // Uncomment this when you implement actual navigation in NavHost
                // onItemClick = { item ->
                //     navController.navigate(item.route) {
                //         popUpTo(navController.graph.startDestinationId)
                //         launchSingleTop = true
                //     }
                // }
                // For now, we'll just print a log for demonstration
                onItemClick = { item ->
                    println("Navigating to: ${item.route}")
                }
            )
        }
    ) { paddingValues -> // paddingValues 会自动提供 Scafflod 内部内容的边距
        // 这里放置你的主屏幕内容，例如 TodayItinerary 页面
        // 记得给内容应用 paddingValues, 以免被底部导航栏遮挡
        Box(modifier = Modifier.padding(paddingValues)) {
            TodayItinerary() // 假设这是你的“今日行程”页面 Composable
            // 在实际应用中，这里会是 NavHost，根据当前路由显示不同页面
            // NavHost(navController = navController, startDestination = "trip_route") {
            //     composable("trip_route") { TodayItinerary() }
            //     composable("buddy_route") { BuddyScreen() }
            //     composable("note_route") { NoteScreen() }
            // }
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    onItemClick: (BottomNavItem) -> Unit
) {
    val currentRoute = navController.currentDestination?.route

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.name,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(text = item.name, style = MaterialTheme.typography.labelSmall)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
@Composable
fun TodayItinerary(modifier: Modifier = Modifier) {
    // 使用 LazyColumn 来实现页面的可滚动性，因为它包含长列表项
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp) // 给底部留一些空间，避免被导航栏遮挡
    ) {
        item {
            // 顶部信息区 (日期, 已连接, 快速入口)
            TopSection()

        }

        item {
            // 完整行程标题
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "完整行程表",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "地图视图",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 行程列表
        items(MockData.tripItems) { tripItem ->
            TripItemCard(tripItem)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            // 搭子发现标题
            Text(
                text = "搭子发现",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // 搭子发现卡片
        items(MockData.discoveryItems.filter { it.type == "搭子" }) { item ->
            DiscoveryCard(item)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            // 随记发现标题
            Text(
                text = "搞子的发现", // 设计稿上是“搞子的发现”，这里用这个
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // 随记发现卡片
        items(MockData.discoveryItems.filter { it.type == "随记" }) { item ->
            DiscoveryCard(item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TopSection() {
    val today = LocalDate.now().format(DateTimeFormatter.ofPattern("M月d日"))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient( // 渐变背景
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                        MaterialTheme.colorScheme.background
                    )
                ),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .padding(bottom = 24.dp)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = today,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    //painter = painterResource(), // 确保有这个图标
//                    contentDescription = "Connected",
//                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
//                    modifier = Modifier.size(20.dp)
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    text = "已连接",
//                    style = MaterialTheme.typography.labelSmall,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            }

        }
        Text(
            text = "完整行程表",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 快速入口
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            MockData.quickAccessItems.forEach { item ->
                QuickAccessItemView(item) { /* TODO: Navigate to route */ }
            }
        }
    }
}

@Composable
fun QuickAccessItemView(item: QuickAccessItem, onClick: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable { onClick(item.route) } // 让整个 Column 可点击
    ) {
        // Icon background
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant), // 背景色
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = item.iconResId),
                contentDescription = item.label,
                tint = Color.Unspecified,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = item.label, style = MaterialTheme.typography.labelMedium)
    }
}

//@Composable
//fun TripItemCard(tripItem: TripItem) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // 左侧时间轴和状态区域
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = tripItem.time,
//                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
//                color = if (tripItem.status == TripStatus.COMPLETED) Color.Gray
//                else MaterialTheme.colorScheme.onSurface
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // 使用不同状态显示对应颜色的小圆点
//            Box(
//                modifier = Modifier
//                    .size(12.dp)
//                    .clip(CircleShape)
//                    .background(
//                        when (tripItem.status) {
//                            TripStatus.COMPLETED -> Color(0xFF4CAF50) // 深绿
//                            TripStatus.IN_PROGRESS -> Color(0xFFFFC107) // 黄色
//                            TripStatus.UPCOMING -> Color(0xFFBDBDBD) // 浅灰
//                        }
//                    )
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // 添加时间轴线条 (非最后一项时显示)
//            if (MockData.tripItems.indexOf(tripItem) < MockData.tripItems.size - 1) {
//                Box(
//                    modifier = Modifier
//                        .width(2.dp)
//                        .height(40.dp)
//                        .background(Color.LightGray)
//                )
//            }
//        }
//        Spacer(modifier = Modifier.width(16.dp))
//
//        // 右侧详情区，含行程标题信息+操作按钮
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f), // 占满剩余宽度
//            shape = RoundedCornerShape(12.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = when (tripItem.status) {
//                    TripStatus.IN_PROGRESS -> Color(0xFFFFF9C4) // 浅黄色背景
//                    else -> MaterialTheme.colorScheme.surface
//                }
//            )
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.padding(12.dp)
//            ) {
//                Column {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        // 添加以状态动态处理`右色左右款+值等-------------
//

@Composable
fun TripItemCard(tripItem: TripItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧时间轴和状态区域
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = tripItem.time,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = if (tripItem.status == TripStatus.COMPLETED) Color.Gray
                else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 使用不同状态显示对应颜色的小圆点
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(
                        when (tripItem.status) {
                            TripStatus.COMPLETED -> Color(0xFF4CAF50) // 深绿
                            TripStatus.IN_PROGRESS -> Color(0xFFFFC107) // 黄色
                            TripStatus.UPCOMING -> Color(0xFFBDBDBD) // 浅灰
                        }
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 添加时间轴线条 (非最后一项时显示)
            if (MockData.tripItems.indexOf(tripItem) < MockData.tripItems.size - 1) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .background(Color.LightGray)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))

        // 右侧行程详情
        Card(
            modifier = Modifier
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) // 轻微透明
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (tripItem.status == TripStatus.COMPLETED) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Completed",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = tripItem.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (tripItem.status == TripStatus.COMPLETED) Color.Gray else LocalContentColor.current
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "📍 ${tripItem.location}",


                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = tripItem.duration,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
//                    if (tripItem.hasAction) {
//                        val iconRes = if (tripItem.status == TripStatus.COMPLETED) {
//                           // 如果已完成，显示“分享”图标
//                        } else if (tripItem.status == TripStatus.UPCOMING) {
//                            Icons.Default.Add // 如果是未开始，显示“添加”图标
//                        } else {
//                            // 进行中显示“导航”图标
//                        }
//
//                        IconButton(
//                            onClick = { /* TODO: Handle action */ },
//                            modifier = Modifier.size(24.dp)
//                        ) {
//                            if (iconRes is Int) {
//                                Icon(
//                                    painter = painterResource(id = iconRes),
//                                    contentDescription = "Action",
//                                    tint = MaterialTheme.colorScheme.primary
//                                )
//                            } else if (iconRes is ImageVector) {
//                                Icon(
//                                    imageVector = iconRes as ImageVector,
//                                    contentDescription = "Action",
//                                    tint = MaterialTheme.colorScheme.primary
//                                )
//                            }
//
//                        }
//                    }
                }
            }
        }
    }
}


@Composable
fun DiscoveryCard(item: DiscoveryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            // 类型标签
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color(0xFFFFF2D5), shape = RoundedCornerShape(4.dp)) // 黄色背景
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = item.type,
                    color = Color(0xFFF9A825), // 黄色字体
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            // 标题和描述
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            // 图片
            Image(
                painter = painterResource(id = item.imageUrl),
                contentDescription = null, // 请提供适当的描述
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}



@Preview(showBackground = true, name = "生成等待页")
@Composable
fun GeneratingPagePreview() {
    MyApplicationTheme {
        TodayItinerary()
    }
}