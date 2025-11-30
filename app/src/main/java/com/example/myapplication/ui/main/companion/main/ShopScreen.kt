package com.example.myapplication.ui.main.companion.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.main.itinerary.main.BottomNavigationBar
/**
 * 重构后的兑换商店页面
 */
@Composable
fun ShopScreen(onBack: () -> Unit) {
    val categories = listOf("全部", "服装", "道具", "特殊")
    val selectedCategory = remember { mutableStateOf("全部") }

    val products = remember {
        listOf(
            Product(
                id = 1,
                name = "西部牛仔风帽子",
                description = "经典西部牛仔造型，提升野性气质",
                points = 50,
                status = ProductStatus.AVAILABLE,
                moodBonus = 35,
                isHot = true,
                category = "服装",
                iconResId = R.drawable.cloth_hat
            ),
            Product(
                id = 2,
                name = "猫眼款式太阳镜",
                description = "别致形状墨镜，多种颜色可选择",
                points = 40,
                status = ProductStatus.AVAILABLE,
                moodBonus = 30,
                category = "服装",
                iconResId = R.drawable.cloth_glasses
            ),
            Product(
                id = 3,
                name = "拼色双肩背包",
                description = "时尚又实用的旅行伴侣",
                points = 80,
                status = ProductStatus.AVAILABLE,
                moodBonus = 35,
                energyBonus = 20,
                category = "服装",
                iconResId = R.drawable.cloth_bag
            ),
            Product(
                id = 4,
                name = "相机道具",
                description = "记录美好瞬间",
                points = 60,
                status = ProductStatus.AVAILABLE,
                moodBonus = 40,
                category = "道具",
                iconResId = R.drawable.icon_cameraprops,
            ),
            Product(
                id = 5,
                name = "旅行日记本",
                description = "记录每一次冒险",
                points = 55,
                status = ProductStatus.REDEEMED,
                category = "道具",
                iconResId = R.drawable.cloth_notebook,
            ),
            Product(
                id = 6,
                name = "限定勋章",
                description = "2025冬季限定纪念勋章",
                points = 200,
                status = ProductStatus.AVAILABLE,
                isHot = true,
                category = "特殊",
                iconResId = R.drawable.icon_smallchampion,
            ),
            Product(
                id = 7,
                name = "地图指南针",
                description = "永远不迷路的秘密武器",
                points = 45,
                status = ProductStatus.REDEEMED,
                category = "道具",
                iconResId = R.drawable.cloth_clock,
        )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff9fafb))
    ) {
        // 顶部横幅
        WardrobeHeader(onBack={})

        // 分类标签
        CategoryTabs(
            categories = categories,
            selectedCategory = selectedCategory.value,
            onCategorySelected = { selectedCategory.value = it }
        )

        // 商品列表
        ProductList(
            products = products.filter {
                selectedCategory.value == "全部" || it.category == selectedCategory.value
            },
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
private fun WardrobeHeader(onBack: () -> Unit) {
    Surface(
        color = Color(0xffffbf00),
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
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
                    .size(12.dp)
                    .clickable { onBack()}
            )

            // 标题
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = 32.dp)
            ) {
                Text(
                    text = "兑换商店",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "用积分兑换专属装扮和道具",
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
private fun CategoryTabs(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            CategoryTab(
                text = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
private fun CategoryTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xfffff7ed) else Color.White
    val borderColor = if (isSelected) Color(0xffff8803) else Color(0xffe5e7eb)
    val textColor = if (isSelected) Color(0xffc93400) else Color(0xff495565)

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .height(43.dp)
            .width(80.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        //border = ButtonDefaults.outlinedButtonBorder.copy(color = borderColor),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product)
        }
    }
}

@Composable
private fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // 热门标签 - 放在右上角
            if (product.isHot) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = 8.dp) // 稍微偏移，使其部分超出卡片边界
                ) {
                    HotTag()
                }
            }

            // 已兑换标签 - 放在右上角（如果商品已兑换）
            if (product.status == ProductStatus.REDEEMED) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = 8.dp)
                ) {
                    RedeemedTag()
                }
            }

            // 主要内容
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 商品图片区域
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xfff3f4f6)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = product.iconResId),
                        contentDescription = "商品图片",
                        modifier = Modifier.size(180.dp),
                        tint = Color.Unspecified
                    )
                }

                // 商品信息
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // 商品名称（移除原来的热门标签行）
                    Text(
                        text = product.name,
                        color = Color(0xff101727),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    // 商品描述
                    Text(
                        text = product.description,
                        color = Color(0xff697282),
                        fontSize = 11.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    // 属性加成
                    if (product.moodBonus > 0 || product.energyBonus > 0) {
                        val bonusText = buildString {
                            if (product.energyBonus > 0) append("+${product.energyBonus}活力 ")
                            if (product.moodBonus > 0) append("+${product.moodBonus}心情")
                        }
                        Text(
                            text = bonusText.trim(),
                            color = Color(0xff495565),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // 积分和状态
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        PointsBadge(points = product.points)

                        StatusText(status = product.status)
                    }
                }

                // 兑换按钮
                ExchangeButton(status = product.status) {
                    // 兑换操作
                }
            }
        }
    }
}

@Composable
private fun HotTag() {
    Surface(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(8.dp), // 使用正常的圆角，而不是极大的值
        color = Color(0xff00c3d0),
        shadowElevation = 2.dp // 添加阴影使其更突出
    ) {
        Text(
            text = "热门商品",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun RedeemedTag(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(26271900.dp),
        color = Color(0xff00c950)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("✓", color = Color.White, fontSize = 12.sp)
            Text("已兑换", color = Color.White, fontSize = 12.sp)
        }
    }
}

private fun Modifier.align(topEnd: Alignment) {}

@Composable
private fun PointsBadge(points: Int) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xffffecd4)
    ) {
        Text(
            text = "$points 积分",
            color = Color(0xffc93400),
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun StatusText(status: ProductStatus) {
    val (text, color) = when (status) {
        ProductStatus.AVAILABLE -> "可兑换" to Color(0xff00a63d)
        ProductStatus.REDEEMED -> "已兑换" to Color(0xff697282)
    }

    Text(
        text = text,
        color = color,
        fontSize = 12.sp
    )
}

@Composable
private fun ExchangeButton(
    status: ProductStatus,
    onClick: () -> Unit
) {
    val icon = when (status) {
        ProductStatus.AVAILABLE -> "🛒"
        ProductStatus.REDEEMED -> "✓"
    }

    IconButton(
        onClick = onClick,
        modifier = Modifier.size(36.dp)
    ) {
        Text(icon, fontSize = 20.sp)
    }
}

@Composable
private fun BottomNavigationBar() {
    val items = listOf("行程", "搭子", "回忆")
    val selectedItem = remember { mutableStateOf("搭子") }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 29.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                BottomNavItem(
                    text = item,
                    isSelected = item == selectedItem.value,
                    onClick = { selectedItem.value = item }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) Color(0xff00c3d0) else Color(0xff99a1ae)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // 图标占位符
        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("●", color = color, fontSize = 24.sp)
        }

        Text(
            text = text,
            color = color,
            fontSize = 12.sp
        )
    }
}

// 数据类
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val points: Int,
    val status: ProductStatus,
    val moodBonus: Int = 0,
    val energyBonus: Int = 0,
    val isHot: Boolean = false,
    val category: String,
    val iconResId: Int, // 添加图标资源ID
)

enum class ProductStatus {
    AVAILABLE, REDEEMED
}

@Preview(showBackground = true)
@Composable
fun CodiaMainViewPreview() {
    MyApplicationTheme(){
        ShopScreen(onBack = {})
    }
}