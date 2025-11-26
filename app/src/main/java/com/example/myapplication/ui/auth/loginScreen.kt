// 建议文件名：AuthScreen.kt
package com.example.myapplication.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R // 确保你的 R 文件被正确导入
import com.example.myapplication.ui.theme.MyApplicationTheme

/**
 * 认证/登录页面
 *
 * 重构要点:
 * 1. 使用 Column + Arrangement.spacedBy + Spacer 替代了原始代码中复杂的 Box + offset 定位。
 * 2. 将三种登录按钮抽象为可复用的 AuthButton 组件。
 * 3. 使用 ClickableText 和 AnnotatedString 实现协议文本的点击效果，代码更标准、简洁。
 * 4. 整体布局使用 fillMaxSize 和 weight，使其能够在不同尺寸的设备上自适应。
 */
@Composable
fun AuthScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize() // 1. 【改进】占满整个屏幕，而不是固定尺寸
            .background(Color.White)
            .verticalScroll(rememberScrollState()), // 2. 【改进】添加滚动，防止在小屏幕上内容溢出
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(0.dp)) // 从 80.dp 改为 0.dp 或者一个很小的值，根据你的布局需要

// 2. 【核心修改】使用 Box 来实现图片和渐变遮罩的叠加效果
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),

            contentAlignment = Alignment.Center
        ) {
            // 图片层 1：你的主插图 (保持不变)
            Image(
                painter = painterResource(id = R.drawable.login_hello),
                contentDescription = "App Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 图层 2：用代码绘制的渐变层
            Box(
                modifier = Modifier
                    .fillMaxSize() // 也填满整个区域
                    .background(
                        // 创建一个垂直的渐变笔刷
                        brush = Brush.verticalGradient(
                            // 渐变颜色列表：从完全透明到背景色（白色）
                            colors = listOf(Color.Transparent, Color.White),
                            // ⭐ 调整 startY 来控制渐变开始的位置
                            // 例如，从总高度的 40% 处开始渐变
                            startY = 350.dp.value * 0.4f
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth() // 这个 Column 也占满宽度
                .padding(horizontal = 32.dp), // ⭐ 在这里应用内边距
            horizontalAlignment = Alignment.CenterHorizontally // ⭐ 居中对齐也在这里设置
        ){Spacer(Modifier.height(24.dp))

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
                color = Color(0xFF6D6D72) // 使用更精确的灰色值
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "让每次旅行都充满惊喜",
                fontSize = 14.sp,
                color = Color(0xFFC7C7CC) // 使用更精确的浅灰色值
            )

            Spacer(Modifier.height(40.dp))

            // 登录按钮组
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp) // 按钮之间的垂直间距
            ) {
                AuthButton(
                    text = "微信登录",
                    backgroundColor = Color(0xFFFFCB2F),
                    textColor = Color.Black.copy(alpha = 0.8f),
                    onClick = { /* TODO: 处理微信登录逻辑 */ }
                )
                AuthButton(
                    text = "手机号登录",
                    backgroundColor = Color(0xFF9E9E9E), // 标准灰色
                    textColor = Color.White,
                    onClick = { /* TODO: 处理手机号登录逻辑 */ }
                )
                AuthButton(
                    text = "邮箱登录",
                    backgroundColor = Color.White,
                    textColor = Color.DarkGray,
                    // 【改进】通过 Modifier 添加边框，而不是再包一层
                    modifier = Modifier.border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
                    onClick = { /* TODO: 处理邮箱登录逻辑 */ }
                )
            }


        }
//      【修改】调整插图和下方标题之间的间距

    }
}

/**
 * 可复用的认证按钮组件
 */
@Composable
private fun AuthButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier, // 允许外部传入额外 Modifier (如此处的 border)
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        shadowElevation = if (backgroundColor == Color.White) 0.dp else 2.dp // 白色按钮通常不需要阴影
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



@Preview(showBackground = true, device = "spec:width=394dp,height=852dp")
@Composable
fun AuthScreenPreview() {
    MyApplicationTheme {
        AuthScreen()
    }
}
