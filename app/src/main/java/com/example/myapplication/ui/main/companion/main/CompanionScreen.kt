package com.example.myapplication.ui.main.companion.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
            PetStatusCard()

            Spacer(modifier = Modifier.height(32.dp))

            // 功能菜单
            FeatureMenuSection()

            Spacer(modifier = Modifier.height(32.dp))

            // 底部导航
            BottomNavigationBar()
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
private fun PetStatusCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
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
                Text(
                    text = "心情：有点小开心",
                    color = Color(0xffba6600),
                    fontSize = 11.sp,
//                    modifier = Modifier.align(Alignment.Center as Alignment.Horizontal)待修复
                )
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

            // 操作按钮
            PetActionsRow()
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
private fun PetActionsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PetActionButton(
            iconRes = R.drawable.icon_noodles,
            text = "喂食",
            backgroundColor = Color(0xffdcfcf6)
        )
        PetActionButton(
            iconRes = R.drawable.icon_balloon,
            text = "玩耍",
            backgroundColor = Color(0xffdcfcf6)
        )
        PetActionButton(
            iconRes = R.drawable.icon_dialogtext,
            text = "聊天",
            backgroundColor = Color(0xffdcfcf6)
        )
    }
}

@Composable
private fun PetActionButton(iconRes: Int, text: String, backgroundColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
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