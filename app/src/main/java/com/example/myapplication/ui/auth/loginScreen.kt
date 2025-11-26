// 建议文件名：AuthScreen.kt
package com.example.myapplication.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R // 确保你的 R 文件被正确导入


/**
 * 认证/登录页面
 */
@Composable
fun LoginScreen(onNavigateToOnboarding: () -> Unit) {
    Column( // 👈 外层 Column
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()) // 它负责滚动
            .padding(vertical = 0.dp), // 👈 内层 Column 的内边距
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 图片 Box 部分，它不需要左右内边距，所以放在最外面
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_hello),
                contentDescription = "App Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White),
                        startY = 350.dp.value * 0.4f
                    ))
            )
        }

        Spacer(Modifier.height(24.dp))

        // 👇👇👇 关键修改：【去掉】了这里的内层 Column
        // 把它的内容直接暴露在 外层Column 中

        // 标题和副标题
        Text(
            text = "TravelMate",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "你的专属旅行搭子",
            fontSize = 17.sp,
            color = Color(0xFF6D6D72)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "让每次旅行都充满惊喜",
            fontSize = 14.sp,
            color = Color(0xFFC7C7CC)
        )

        Spacer(Modifier.height(40.dp))

        // 登录按钮组，它们需要统一的内边距，所以用一个 Column 包裹它们
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp), // ✅ 把内边距放在这里
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AuthButton(
                text = "微信登录",
                backgroundColor = Color(0xFFFFCB2F),
                textColor = Color.Black.copy(alpha = 0.8f),
                onClick = onNavigateToOnboarding
            )
            AuthButton(
                text = "手机号登录",
                backgroundColor = Color(0xFF9E9E9E),
                textColor = Color.White,
                onClick = onNavigateToOnboarding
            )
            AuthButton(
                text = "邮箱登录",
                backgroundColor = Color.White,
                textColor = Color.DarkGray,
                modifier = Modifier.border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
                onClick = onNavigateToOnboarding
            )
        }

        // 增加一些底部空间，防止按钮紧贴屏幕底部
        Spacer(Modifier.height(32.dp))
    }
}

// AuthButton 组件保持不变...
@Composable
private fun AuthButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        shadowElevation = if (backgroundColor == Color.White) 0.dp else 2.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}

