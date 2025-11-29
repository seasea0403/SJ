package com.example.myapplication.ui.main.companion.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
 * 简洁版搭子主页面
 */
@Composable
fun CompanionScreen() {
    // 弹窗状态管理
    val bottomSheetState = rememberBottomSheetState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            // 顶部用户信息
            UserInfoSection()

            Spacer(modifier = Modifier.height(32.dp))

            // 宠物状态卡片
            PetStatusCard(
                onFeedClick = { bottomSheetState.showBottomSheet(BottomSheetType.FEEDING) },
                onPlayClick = { bottomSheetState.showBottomSheet(BottomSheetType.PLAYING) },
                onChatClick = { bottomSheetState.showBottomSheet(BottomSheetType.CHATTING) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 功能菜单
            FeatureMenuSection()

            Spacer(modifier = Modifier.height(32.dp))

            // 底部导航
            BottomNavigationBar()
        }

        // 底部弹窗
        BottomSheetContainer(state = bottomSheetState)
    }
}
enum class BottomSheetType {
    FEEDING, PLAYING, CHATTING, NONE
}

// 弹窗状态管理类
class BottomSheetState {
    var currentSheet by mutableStateOf(BottomSheetType.NONE)

    fun showBottomSheet(type: BottomSheetType) {
        currentSheet = type
    }

    fun hideBottomSheet() {
        currentSheet = BottomSheetType.NONE
    }
}

@Composable
fun rememberBottomSheetState(): BottomSheetState {
    return remember { BottomSheetState() }
}

// 弹窗容器
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetContainer(state: BottomSheetState) {
    if (state.currentSheet != BottomSheetType.NONE) {
        ModalBottomSheet(
            onDismissRequest = { state.hideBottomSheet() },
            sheetState = rememberModalBottomSheetState(),
            containerColor = Color.White
        ) {
            when (state.currentSheet) {
                BottomSheetType.FEEDING -> FeedingBottomSheet()
                BottomSheetType.PLAYING -> PlayingBottomSheet(
                    onGameSelected = { state.hideBottomSheet() }
                )
                BottomSheetType.CHATTING -> ChattingBottomSheet()
                BottomSheetType.NONE -> { /* 不会执行到这里 */ }
            }
        }
    }
}

// 喂食弹窗
@Composable
private fun FeedingBottomSheet() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 标题
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("🍜", fontSize = 32.sp)
            Text(
                text = "喂食",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xff101727)
            )
        }

        Text(
            text = "选择食物喂给橘子",
            fontSize = 16.sp,
            color = Color(0xff697282)
        )

        // 食物列表
        val foodItems = listOf(
            FoodItem("猫粮", "营养均衡", 10, "🍚"),
            FoodItem("小鱼干", "橘子的最爱", 15, "🐟"),
            FoodItem("营养膏", "补充维生素", 25, "💊"),
            FoodItem("猫罐头", "美味大餐", 30, "🥫")
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(foodItems) { food ->
                FoodItemCard(food = food)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 取消按钮
        TextButton(
            onClick = { /* 关闭弹窗由容器处理 */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("取消", color = Color(0xff697282))
        }
    }
}

// 玩耍弹窗 - 使用之前重构的游戏选择页面
@Composable
private fun PlayingBottomSheet(onGameSelected: () -> Unit) {
    val games = remember {
        listOf(
            Game("猜拳", "简单的石头剪刀布", 15, "✊", "-3 活力 +12 心情"),
            Game("飞盘游戏", "在草地上玩飞盘", 25, "🛸", "-5 活力 +20 心情"),
            Game("蹦床时间", "一起在蹦床上跳跃", 20, "🛏️", "-3 活力 +15 心情"),
            Game("水球大战", "清凉的水球战斗", 30, "💦", "-3 活力 +25 心情")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 标题
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("🎮", fontSize = 32.sp)
            Text(
                text = "选择游戏",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xff101727)
            )
        }

        Text(
            text = "选择一个游戏和橘子一起玩",
            fontSize = 16.sp,
            color = Color(0xff697282)
        )

        // 游戏网格
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(games) { game ->
                GameItemCard(
                    game = game,
                    onPlayClick = onGameSelected
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 取消按钮
        TextButton(
            onClick = { /* 关闭弹窗由容器处理 */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("取消", color = Color(0xff697282))
        }
    }
}

// 聊天弹窗
@Composable
private fun ChattingBottomSheet() {
    val chatMessages = remember {
        listOf(
            ChatMessage("你今天过得怎么样？", true),
            ChatMessage("要不要一起玩游戏？", true),
            ChatMessage("我有点饿了...", true),
            ChatMessage("外面的天气真好！", true)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 标题
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("💬", fontSize = 32.sp)
            Text(
                text = "和橘子聊天",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xff101727)
            )
        }

        Text(
            text = "选择话题和橘子聊天",
            fontSize = 16.sp,
            color = Color(0xff697282)
        )

        // 聊天话题列表
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(chatMessages) { message ->
                ChatTopicCard(message = message)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 取消按钮
        TextButton(
            onClick = { /* 关闭弹窗由容器处理 */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("取消", color = Color(0xff697282))
        }
    }
}

// 数据类和组件
data class FoodItem(val name: String, val description: String, val points: Int, val icon: String)
data class Game(val name: String, val description: String, val points: Int, val icon: String, val effect: String)
data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
private fun FoodItemCard(food: FoodItem) {
    Card(
        onClick = { /* 喂食逻辑 */ },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xfff8fafc))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(food.icon, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = food.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xff101727)
                    )
                    Text(
                        text = food.description,
                        fontSize = 12.sp,
                        color = Color(0xff697282)
                    )
                }
            }

            Text(
                text = "${food.points}积分",
                fontSize = 14.sp,
                color = Color(0xff00c3d0),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun GameItemCard(game: Game, onPlayClick: () -> Unit) {
    Card(
        onClick = {
            // 开始游戏逻辑
            onPlayClick()
        },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xfff8fafc))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(game.icon, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = game.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xff101727)
                        )
                        Text(
                            text = game.description,
                            fontSize = 12.sp,
                            color = Color(0xff697282)
                        )
                    }
                }

                Text(
                    text = "${game.points}积分",
                    fontSize = 14.sp,
                    color = Color(0xff00c3d0),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = game.effect,
                fontSize = 12.sp,
                color = Color(0xff495565)
            )
        }
    }
}

@Composable
private fun ChatTopicCard(message: ChatMessage) {
    Card(
        onClick = { /* 发送消息逻辑 */ },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (message.isUser) Color(0xffdcfcf6) else Color(0xfff8fafc)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!message.isUser) {
                Text("🐱", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(12.dp))
            }

            Text(
                text = message.text,
                fontSize = 14.sp,
                color = Color(0xff101727),
                modifier = Modifier.weight(1f)
            )

            if (message.isUser) {
                Spacer(modifier = Modifier.width(12.dp))
                Text("👤", fontSize = 20.sp)
            }
        }
    }
}

@Composable
private fun UserInfoSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "橘子",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xff101727)
            )
            Text(
                text = "Lv.12 独立漫游者",
                fontSize = 16.sp,
                color = Color(0xff495565)
            )
        }

        // 积分卡片
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 2.dp,
            border = CardDefaults.outlinedCardBorder()
        ) {
            Column(
                modifier = Modifier
                    .width(90.dp)
                    .height(56.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.com_score),
                        contentDescription = "积分",
                        tint = Color(0xff00c3d0),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "2450",
                        fontSize = 16.sp,
                        color = Color(0xff00c3d0)
                    )
                }
                Text(
                    text = "积分",
                    fontSize = 12.sp,
                    color = Color(0xff697282)
                )
            }
        }
    }
}

@Composable
private fun PetStatusCard(
    onFeedClick: () -> Unit,
    onPlayClick: () -> Unit,
    onChatClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(550.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // 心情标签
            Surface(
                color = Color(0xffffe084),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .align(Alignment.End)
                    .width(114.dp)
                    .height(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "心情：有点小开心",
                        color = Color(0xffba6600),
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 宠物图片
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cute_cat),
                    contentDescription = "宠物",
                    modifier = Modifier.size(180.dp),
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 状态进度条
            StatusProgressBar(
                label = "活力",
                value = 85,
                maxValue = 250,
                color = Color(0xff00c3d0)
            )

            Spacer(modifier = Modifier.height(16.dp))

            StatusProgressBar(
                label = "心情",
                value = 180,
                maxValue = 250,
                color = Color(0xffffcc00)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 操作按钮 - 现在接收点击回调
            PetActionsRow(
                onFeedClick = onFeedClick,
                onPlayClick = onPlayClick,
                onChatClick = onChatClick
            )
        }
    }
}

@Composable
private fun StatusProgressBar(label: String, value: Int, maxValue: Int, color: Color) {
    Column {
        Text(
            text = "$label ：$value/$maxValue",
            fontSize = 12.sp,
            color = Color(0xff697282),
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(4.dp))
//        LinearProgressIndicator(
//            progress = value.toFloat() / maxValue,
//            color = color,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(8.dp),
//            trackColor = Color.LightGray
//        )
    }
}

@Composable
private fun PetActionsRow(
    onFeedClick: () -> Unit,
    onPlayClick: () -> Unit,
    onChatClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PetActionButton(
            iconRes = R.drawable.icon_noodles,
            text = "喂食",
            backgroundColor = Color(0xffdcfcf6),
            onClick = onFeedClick
        )
        PetActionButton(
            iconRes = R.drawable.icon_balloon,
            text = "玩耍",
            backgroundColor = Color(0xffdcfcf6),
            onClick = onPlayClick
        )
        PetActionButton(
            iconRes = R.drawable.icon_dialogtext,
            text = "聊天",
            backgroundColor = Color(0xffdcfcf6),
            onClick = onChatClick
        )
    }
}
// 修改 PetActionButton 以接收点击回调
@Composable
private fun PetActionButton(
    iconRes: Int,
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(80.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(48.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                color = Color(0xff354152)
            )
        }
    }
}


@Composable
private fun FeatureMenuSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FeatureMenuItem(
            iconRes = R.drawable.hea_com,
            title = "任务与成就",
            subtitle = "3 个任务进行中",
            badgeCount = 3
        )
        FeatureMenuItem(
            iconRes = R.drawable.com_cloth,
            title = "装扮衣柜",
            subtitle = "8 件装扮可用",
            showArrow = true
        )
        FeatureMenuItem(
            iconRes = R.drawable.com_shop,
            title = "积分商店",
            subtitle = "兑换专属装扮和道具",
            backgroundColor = Color(0xffffcc00),
            textColor = Color.White,
            subtitleColor = Color(0xffffecd4),
            showArrow = true
        )
    }
}

@Composable
private fun FeatureMenuItem(
    iconRes: Int,
    title: String,
    subtitle: String,
    backgroundColor: Color = Color.White,
    textColor: Color = Color(0xff101727),
    subtitleColor: Color = Color(0xff697282),
    badgeCount: Int? = null,
    showArrow: Boolean = false
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = title,
                    modifier = Modifier.size(56.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        color = subtitleColor
                    )
                }
            }

            if (badgeCount != null) {
                Badge(
                    containerColor = Color(0xfffb2c36),
                    modifier = Modifier.size(24.dp)
                ) {
                    Text(
                        text = badgeCount.toString(),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            } else if (showArrow) {
                Text(
                    text = "→",
                    fontSize = 16.sp,
                    color = if (backgroundColor == Color(0xffffcc00)) Color.White else Color(0xff00c3d0)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CodiaMainViewPreview1() {
    MyApplicationTheme() {
        CompanionScreen()
    }
}