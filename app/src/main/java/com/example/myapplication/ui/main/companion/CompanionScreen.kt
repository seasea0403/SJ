package com.example.myapplication.ui.main.companion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.main.companion.data.BuddyProfile
import com.example.myapplication.ui.theme.MyApplicationTheme

// 数据类，用于定义底部功能按钮
data class ActionButtonInfo(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

/**
 * 搭子页面的主入口 Composable
 */
@Composable
fun CompanionScreen(
    viewModel: CompanionViewModel = hiltViewModel()
) {
    // 从ViewModel收集UI状态，每当状态变化，Compose会自动重组
    val uiState by viewModel.uiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            // 如果正在加载，显示一个加载圈
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.buddyProfile != null) {
            // 数据加载成功，显示主内容
            CompanionContent(
                profile = uiState.buddyProfile!!, // 此时我们确定profile不为null
                onTaskClicked = { /* TODO: 未来导航到任务页面 */ },
                onWardrobeClicked = { /* TODO: 未来打开衣柜弹窗 */ },
                onStoreClicked = { /* TODO: 未来导航到商店页面 */ },
                onInteractClicked = { /* TODO: 未来打开互动弹窗 */ },
            )
        } else {
            // 如果没有搭子信息（比如用户跳过了onboarding），显示提示
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("还没有找到你的旅行搭子哦！")
            }
        }
    }
}

/**
 * 搭子页面的主内容布局
 */
@Composable
fun CompanionContent(
    profile: BuddyProfile,
    onTaskClicked: () -> Unit,
    onWardrobeClicked: () -> Unit,
    onStoreClicked: () -> Unit,
    onInteractClicked: () -> Unit,
) {
    // 使用Column进行垂直布局，SpaceBetween将组件均匀分布在垂直方向
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround // 上中下分布
    ) {
        // 1. 顶部搭子信息卡片
        BuddyProfileSection(profile = profile)

        // 2. 中部经验值进度条
        ExpProgressSection(
            currentExp = profile.currentExp,
            expToNextLevel = profile.expToNextLevel
        )

        // 3. 底部功能按钮网格
        val actionButtons = listOf(
            ActionButtonInfo("任务", Icons.Default.TaskAlt, onTaskClicked),
            ActionButtonInfo("装扮", Icons.Default.Checkroom, onWardrobeClicked),
            ActionButtonInfo("商店", Icons.Default.Store, onStoreClicked),
            ActionButtonInfo("互动", Icons.Default.Egg, onInteractClicked)
        )
        ActionButtonsGrid(buttons = actionButtons)
    }
}

/**
 * 组件1: 搭子信息区域 (头像、名字、等级、心情)
 */
@Composable
fun BuddyProfileSection(profile: BuddyProfile) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 搭子形象
        // 【说明】这里暂时用一个默认图标代替，将来会换成 AsyncImage 加载网络图片
        Image(
            imageVector = Icons.Default.Pets,
            contentDescription = profile.name,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentScale = ContentScale.Crop
        )

        // 名字
        Text(text = profile.name, style = MaterialTheme.typography.headlineMedium)

        // 等级和称号
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Lv. ${profile.level}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = profile.title, color = MaterialTheme.colorScheme.secondary)
        }

        // 心情
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Text(
                text = "心情: ${profile.mood}",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/**
 * 组件2: 经验值进度条
 */
@Composable
fun ExpProgressSection(currentExp: Int, expToNextLevel: Int) {
    val progress = currentExp.toFloat() / expToNextLevel.toFloat()
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("经验值", style = MaterialTheme.typography.labelMedium)
            Text("$currentExp / $expToNextLevel", style = MaterialTheme.typography.labelMedium)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}

/**
 * 组件3: 功能按钮网格
 */
@Composable
fun ActionButtonsGrid(buttons: List<ActionButtonInfo>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 固定两列
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(buttons) { buttonInfo ->
            ActionButton(info = buttonInfo)
        }
    }
}

/**
 * 单个功能按钮
 */
@Composable
private fun ActionButton(info: ActionButtonInfo) {
    Card(
        modifier = Modifier.clickable(onClick = info.onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = info.icon, contentDescription = info.label, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = info.label, style = MaterialTheme.typography.titleMedium)
        }
    }
}


// --- 预览代码 ---
// 你可以在Android Studio的右侧面板看到这个界面的预览
@Preview(showBackground = true)
@Composable
fun CompanionContentPreview() {
    val previewProfile = BuddyProfile(
        name = "橘子",
        level = 5,
        title = "独立漫游者",
        points = 120,
        expToNextLevel = 200,
        currentExp = 80,
        mood = "开心",
        imageUrl = "" // 预览时不需要
    )
    MyApplicationTheme { // 使用你的App主题包裹预览
        CompanionContent(
            profile = previewProfile,
            onTaskClicked = {},
            onWardrobeClicked = {},
            onStoreClicked = {},
            onInteractClicked = {}
        )
    }
}
