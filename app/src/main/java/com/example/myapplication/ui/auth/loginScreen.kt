package com.example.myapplication.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.common.advancedShadow
import com.example.myapplication.ui.theme.MyApplicationTheme

/**
 * Created by codia-figma
 */
@Composable
fun CodiaMainView() {
    // Column-3311:2-Travel Companion App
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(0xffffffff))
            .size(394.dp, 852.dp)
            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.000030517578125.dp),
    ) {
        // Box-3311:3-Container
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(345.85.dp, 514.971.dp),
        ) {
            // Column-3311:7-标题
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 7.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 0.dp, y = 143.968.dp)
                    .size(345.85.dp, 103.975.dp),
            ) {
                // Box-3311:8-Heading 1
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp)
                        .height(40.005.dp),
                ) {
                    // Text-3311:9-TravelMate
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .wrapContentSize()
                            .offset(x = 75.847.dp, y = -2.132.dp),
                        text = "TravelMate",
                        color = Color(0xff000000),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                // Box-3311:10-Paragraph
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp)
                        .height(24.003.dp),
                ) {
                    // Text-3311:11-你的专属旅行搭子
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .wrapContentSize()
                            .offset(x = 108.93.dp, y = -1.434.dp),
                        text = "你的专属旅行搭子",
                        color = Color(0x993c3c43),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                // Box-3311:12-Paragraph
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp)
                        .height(19.99.dp),
                ) {
                    // Text-3311:13-让每次旅行都充满惊喜
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .wrapContentSize()
                            .offset(x = 102.923.dp, y = -1.217.dp),
                        text = "让每次旅行都充满惊喜",
                        color = Color(0xffbfbfbf),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            // Column-3311:14-注册
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 13.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 0.dp, y = 295.936.dp)
                    .size(345.85.dp, 171.053.dp),
            ) {
                // Box-3311:15-Button
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp)
                        .advancedShadow(color = Color(0x19000000), alpha = 0.10000000149011612f, cornersRadius = 16.dp, shadowBlurRadius = 4.dp, offsetX = 0.dp, offsetY = 2.dp)
                        .background(Color(0xffffcc00), RoundedCornerShape(16.dp))
                        .height(47.981.dp),
                ) {
                    // Box-3311:16-Welcome
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(x = 0.002999999999985903.dp, y = 13.996.dp)
                            .size(60.006.dp, 19.99.dp),
                    ) {
                        // Text-3311:17-微信登录
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentSize()
                                .offset(x = 0.dp, y = -1.217.dp),
                            text = "微信登录",
                            color = Color(0x993c3c43),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                // Box-3311:18-Button
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp)
                        .advancedShadow(color = Color(0x19000000), alpha = 0.10000000149011612f, cornersRadius = 16.dp, shadowBlurRadius = 4.dp, offsetX = 0.dp, offsetY = 2.dp)
                        .background(Color(0xff999999), RoundedCornerShape(16.dp))
                        .height(47.981.dp),
                ) {
                    // Box-3311:19-Welcome
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(x = 0.23449999999996862.dp, y = 13.995.dp)
                            .size(90.469.dp, 19.99.dp),
                    ) {
                        // Text-3311:20-Apple ID 登录
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentSize()
                                .offset(x = 2.dp, y = -1.217.dp),
                            text = "Apple ID 登录",
                            color = Color(0xffffffff),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                // Box-3344:269-Button
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp)
                        .advancedShadow(color = Color(0x19000000), alpha = 0.10000000149011612f, cornersRadius = 16.dp, shadowBlurRadius = 4.dp, offsetX = 0.dp, offsetY = 2.dp)
                        .background(Color(0xffffffff), RoundedCornerShape(16.dp))
                        .height(47.981.dp),
                ) {
                    // Box-3344:270-Welcome
                    Box(
                        contentAlignment = Alignment.TopStart,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(x = 0.23449999999996862.dp, y = 13.995.dp)
                            .size(100.dp, 19.99.dp),
                    ) {
                        // Text-3344:271-手机 / 邮箱注册
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .wrapContentSize()
                                .offset(x = -3.dp, y = -1.217.dp),
                            text = "手机 / 邮箱注册",
                            color = Color(0x993c3c43),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // ⭐ 在这里给 Row 自身添加一个上边距, 把它往下推
                    .padding(top = 490.dp), // 调整这个值
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 文字 1: 静态文本
                Text(
                    text = "继续即表示您同意我们的 ", // 结尾加个空格，视觉效果更好
                    color = Color(0xff99a1ae),
                    fontSize = 12.sp
                )

                // 文字 2: 用户协议 (可点击)
                Text(
                    modifier = Modifier.clickable {
                        // TODO: 在这里写入点击“用户协议”后要执行的操作，例如跳转页面
                        println("用户协议被点击了！")
                    },
                    text = "用户协议",
                    color = Color(0xffff6800), // 橘色，提示用户可以点击
                    fontSize = 12.sp
                )

                // 文字 3: 静态文本
                Text(
                    text = " 和 ", // 前后都加空格，拉开间距
                    color = Color(0xff99a1ae),
                    fontSize = 12.sp
                )

                // 文字 4: 隐私政策 (可点击)
                Text(
                    modifier = Modifier.clickable {
                        // TODO: 在这里写入点击“隐私政策”后要执行的操作
                        println("隐私政策被点击了！")
                    },
                    text = "隐私政策",
                    color = Color(0xffff6800),
                    fontSize = 12.sp
                )
            }

            // Image-3321:2-app启动icon 1
            Image(
                painter = painterResource(id = R.drawable.image_33212),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 121.925.dp, y = 5.485.dp)
                    .advancedShadow(color = Color(0x3f919191), alpha = 0.25f, cornersRadius = 8.dp, shadowBlurRadius = 10.dp, offsetX = 1.dp, offsetY = 1.dp)
                    .size(101.dp, 101.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CodiaMainViewPreview() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val scrollState = rememberScrollState()
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                CodiaMainView()
            }
        }
    }
}
