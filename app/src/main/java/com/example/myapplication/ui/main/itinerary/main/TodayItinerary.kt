package com.example.travelapp // 请替换成你的实际包名

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

import com.example.myapplication.ui.main.itinerary.data.DiscoveryItem
import com.example.myapplication.ui.main.itinerary.data.QuickAccessItem
import com.example.myapplication.ui.main.itinerary.data.TripItem
import com.example.myapplication.ui.main.itinerary.data.TripStatus
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.main.itinerary.data.MockData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// 定义快速访问项的路由
object QuickAccessRoutes {
    const val ATTRACTION = "attraction"
    const val FOOD = "food"
    const val PACE = "pace"
}

@Composable
fun ItineraryScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            // 传递 navController 给 TopSection
            TopSection(navController = navController)
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
                text = "搭子的发现",
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
fun TopSection(navController: NavController) {
    val today = LocalDate.now().format(DateTimeFormatter.ofPattern("M月d日"))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
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
        }
        Text(
            text = "完整行程表",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 快速入口 - 添加导航功能
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            MockData.quickAccessItems.forEach { item ->
                QuickAccessItemView(
                    item = item,
                    onClick = { route ->
                        // 根据不同的路由跳转到相应的页面
                        when (route) {
                            QuickAccessRoutes.ATTRACTION -> {
                                navController.navigate("attraction")
                            }
                            QuickAccessRoutes.FOOD -> {
                                navController.navigate("food")
                            }
                            QuickAccessRoutes.PACE -> {
                                navController.navigate("pace")
                            }
                            else -> {
                                // 其他路由处理
                                navController.navigate(route)
                            }
                        }
                    }
                )
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
            .clickable { onClick(item.route) }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
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

// 其他函数保持不变...
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

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(
                        when (tripItem.status) {
                            TripStatus.COMPLETED -> Color(0xFF4CAF50)
                            TripStatus.IN_PROGRESS -> Color(0xFFFFC107)
                            TripStatus.UPCOMING -> Color(0xFFBDBDBD)
                        }
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))

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

        Card(
            modifier = Modifier
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
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
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color(0xFFFFF2D5), shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = item.type,
                    color = Color(0xFFF9A825),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
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
            Image(
                painter = painterResource(id = item.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

// 创建目标页面的占位实现
@Composable
fun AttractionScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("景点页面", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun FoodScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("美食页面", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun PaceScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("步数页面", style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview(showBackground = true, name = "生成等待页")
@Composable
fun GeneratingPagePreview() {
    MyApplicationTheme {
        ItineraryScreen(navController = rememberNavController())
    }
}