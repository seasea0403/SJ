package com.example.myapplication.ui.main.itinerary.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.main.itinerary.data.Attraction
import com.example.myapplication.ui.main.itinerary.data.AttractionStatus
import com.example.myapplication.ui.main.itinerary.data.CrowdLevel
import com.example.myapplication.ui.main.itinerary.data.dummyAttractions
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.R
/**
 * 简化的行程主页面
 */
@Composable
fun AttractionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff9fafb))
    ) {
        SimplifiedAttractionsHeader()
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

@Composable
fun SimplifiedAttractionsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xfff9fafb))
            .padding(horizontal = 24.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 标题区域
        HeaderSection()

        // 搜索区域
        SearchSection()

        // 筛选标签区域
        FilterSection()
    }
}

/**
 * 标题区域
 */
@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 返回按钮占位
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.nav_back), // 替换为你的图标资源
                contentDescription = "图标描述", // 可访问性描述
                modifier = Modifier
                    .fillMaxSize() // 填充整个Box
                    .padding(8.dp), // 添加内边距，让图标小一点
                contentScale = ContentScale.Fit // 保持比例适应
            )
        }

            Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "景点列表",
                color = Color(0xff00c3d0),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "5个计划 · 1个已完成",
                color = Color(0xff00c3d0),
                fontSize = 14.sp
            )
        }
    }
}

/**
 * 搜索区域
 */
@Composable
private fun SearchSection() {
    val searchText = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
    ) {
        // 搜索框背景


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 搜索图标占位
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color.White, RoundedCornerShape(4.dp))
            ){
                Image(
                    painter = painterResource(id = R.drawable.nav_search), // 替换为你的图标资源
                    contentDescription = "图标描述", // 可访问性描述
                    modifier = Modifier
                        .fillMaxSize() // 填充整个Box
                        .padding(1.dp), // 添加内边距，让图标小一点
                    contentScale = ContentScale.Fit // 保持比例适应
                )
            }

            BasicTextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    color = Color(0xff717182),
                    fontSize = 16.sp
                ),
                decorationBox = { innerTextField ->
                    if (searchText.value.isEmpty()) {
                        Text(
                            text = "搜索景点...",
                            color = Color(0xff717182),
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

/**
 * 筛选标签区域
 */
@Composable
private fun FilterSection() {
    val selectedFilter = remember { mutableStateOf("全部") }
    val filters = listOf(
        "全部" to 6,
        "已规划" to 5,
        "附近" to 4
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        filters.forEach { (filter, count) ->
            FilterChip(
                text = "$filter ($count)",
                isSelected = selectedFilter.value == filter,
                onClick = { selectedFilter.value = filter }
            )
        }
    }
}

/**
 * 筛选标签组件
 */
@Composable
private fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        Color(0xff00c3d0)
    } else {
        Color.White.copy(alpha = 0.6f)
    }

    val textColor = if (isSelected) {
        Color.White
    } else {
        Color(0xff00c3d0)
    }

    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SimplifiedAttractionsHeaderPreview() {
    MyApplicationTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SimplifiedAttractionsHeader()
        }
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
            .padding(horizontal = 16.dp),
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
            ) {
                Image(
                    painter = painterResource(id = attraction.image), // 使用 painterResource 加载图片
                    contentDescription = "景点图片", // 可访问性描述
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // 图片缩放方式
                )

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
fun BottomNavigationBar() {
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


@Preview(showBackground = true)
@Composable
fun SimplifiedItineraryViewPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AttractionScreen()
        }
    }
}

