package com.example.myapplication.ui.main.companion.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
 * 简化版装扮衣柜页面
 */
@Composable
fun WearScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xfff9fafb)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 顶部标题栏
            WardrobeHeader()

            // 内容区域
            WardrobeContent()

            // 底部操作按钮
            WardrobeActions()

            // 底部导航栏
            BottomNavigationBar()
        }
    }
}

@Composable
private fun WardrobeHeader() {
    Surface(
        color = Color(0xffffbf00),
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 17.dp, vertical = 21.dp)
        ) {
            // 返回按钮
            Icon(
                painter = painterResource(id = R.drawable.nav_whiteback),
                contentDescription = "返回",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopStart)
            )

            // 标题
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = 32.dp)
            ) {
                Text(
                    text = "装扮衣柜",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "完成任务获取积分，为它买件新衣服吧",
                    fontSize = 13.sp,
                    color = Color.White
                )
            }

            // 积分卡片
            PointsCard(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(y = 17.dp)
            )
        }
    }
}

@Composable
private fun PointsCard(modifier: Modifier = Modifier) {
    Surface(
        color = Color(0x33ffffff),
        shape = RoundedCornerShape(18.dp),
        border = CardDefaults.outlinedCardBorder(),
        modifier = modifier
            .width(116.dp)
            .height(68.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 11.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.com_score),
                    contentDescription = "积分",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(9.dp))

                Text(
                    text = "2450",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Text(
                text = "我的积分",
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun WardrobeContent() {
    val selectedTab = remember { mutableStateOf(0) }
    val tabs = listOf("衣服", "饰品", "鞋子")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // 实时预览
        PreviewSection()

        Spacer(modifier = Modifier.height(24.dp))

        // 选项卡
        TabSection(tabs, selectedTab.value) { selectedTab.value = it }

        Spacer(modifier = Modifier.height(24.dp))

        // 装扮物品网格
        ClothingGridSection()
    }
}

@Composable
private fun PreviewSection() {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.cloth_tshirt_cat),
                contentDescription = "装扮预览",
                modifier = Modifier.size(147.dp),
                tint = Color.Unspecified
            )

            Text(
                text = "实时预览",
                fontSize = 16.sp,
                color = Color(0xff495565),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            )
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
private fun TabItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(100.dp)
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

@Composable
private fun ClothingGridSection() {
    val clothingItems = listOf(
        ClothingItem(
            iconRes = R.drawable.cloth_coat,
            title = "长款风衣-卡其色",
            points = 90,
            moodBonus = 45,
            status = ClothingStatus.AVAILABLE
        ),
        ClothingItem(
            iconRes = R.drawable.cloth_tshirt,
            title = "爱心T恤短款",
            points = 70,
            moodBonus = 30,
            status = ClothingStatus.EQUIPPED
        ),
        ClothingItem(
            iconRes = R.drawable.cloth_skirt,
            title = "天蓝色小飞袖吊带",
            points = 150,
            moodBonus = 65,
            status = ClothingStatus.LOCKED
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(clothingItems) { item ->
            ClothingCard(item = item)
        }
    }
}

@Composable
private fun ClothingCard(item: ClothingItem) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = when (item.status) {
            ClothingStatus.EQUIPPED -> CardDefaults.outlinedCardBorder()
            else -> null
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 状态标签
            if (item.status != ClothingStatus.AVAILABLE) {
                StatusBadge(status = item.status)
            }

            // 服装图标
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.title,
                modifier = Modifier.size(80.dp),
                tint = if (item.status == ClothingStatus.LOCKED) Color(0xffcccccc) else Color.Unspecified
            )

            // 服装信息
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (item.status == ClothingStatus.LOCKED) Color(0xff99a1ae) else Color(0xff101727)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${item.points} 积分",
                        fontSize = 14.sp,
                        color = Color(0xffff6800)
                    )

                    Text(
                        text = "+${item.moodBonus} 心情",
                        fontSize = 12.sp,
                        color = Color(0xff495565)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: ClothingStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        ClothingStatus.EQUIPPED -> Triple(Color(0xff00c950), Color.White, "已装备")
        ClothingStatus.LOCKED -> Triple(Color(0xffff6800), Color.White, "未拥有")
        else -> return
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .wrapContentWidth()
            .height(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (status == ClothingStatus.EQUIPPED) {
                Icon(
                    painter = painterResource(id = R.drawable.com_bingo),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = text,
                color = textColor,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun WardrobeActions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 取消按钮
        OutlinedButton(
            onClick = { /* 取消操作 */ },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("取消")
        }

        // 确认装扮按钮
        Button(
            onClick = { /* 确认装扮操作 */ },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff00c3d0)
            )
        ) {
            Text("确认装扮")
        }
    }
}


data class ClothingItem(
    val iconRes: Int,
    val title: String,
    val points: Int,
    val moodBonus: Int,
    val status: ClothingStatus
)

enum class ClothingStatus {
    AVAILABLE,    // 可用
    EQUIPPED,     // 已装备
    LOCKED        // 未拥有
}

@Preview(showBackground = true)
@Composable
fun WardrobeScreenPreview() {
    MyApplicationTheme() {
        WearScreen()
    }
}