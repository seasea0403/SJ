package com.example.myapplication.ui.onboarding // 确保包名正确

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.onboarding.data.Animal
import com.example.myapplication.ui.onboarding.data.AnswerOption
import com.example.myapplication.ui.onboarding.data.Question
import com.example.myapplication.ui.onboarding.data.TravelBuddyProfile
import com.example.myapplication.ui.theme.MyApplicationTheme // 确保主题路径正确

// --- 步骤 1: 准备用于预览的假数据 (Mock Data) - 这部分保持不变 ---

val mockProfile = TravelBuddyProfile(
    animal = Animal.CAT,
    title = "独立漫游者",
    description = "充满好奇心，享受独处，钟情于城市文化艺术的优雅漫游者",
    imageUrl = R.drawable.avatar_cat,
    headUrl = R.drawable.headimage_cat
)

val mockQuestion = Question(
    id = 2,
    text = "面对一份详细到分钟的旅行计划表, 你的第一感觉是?",
    options = listOf(
        AnswerOption("2-A", "太棒了! 一切尽在掌握, 让我充满安全感。",R.drawable.q1),
        AnswerOption("2-B", "有点压力, 我更喜欢只定下大致方向, 随缘探索。",R.drawable.q2),
        AnswerOption("2-C", "束缚感太强, 最好的风景总是在计划之外。",R.drawable.q3)
    )
)

// --- 步骤 2: 修正并增强你的 @Preview 函数 ---

// CHANGED: QuestionPagePreview 已更新
// 因为 QuestionPage 现在是整体屏幕的一部分，包含了 TopBar，所以预览也需要模拟这个结构
@Preview(showBackground = true, name = "问题页完整预览")
@Composable
fun QuestionPageFullPreview() {
    MyApplicationTheme {
        // 模拟真实屏幕的布局结构
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFCFCF9)) // 使用我们在 Scaffold 中设置的背景色
        ) {
            // 1. 预览我们的新 TopBar
            OnboardingTopBar(
                onBackClicked = {},
                currentStep = 2, // 假设当前是第 2 题
                totalSteps = 5  // 总共 5 题
            )
            // 2. 预览问题页面内容
            QuestionPage(
                question = mockQuestion,
                onAnswerSelected = {}
            )
        }
    }
}

// NEW: 为我们新建的动画组件添加独立的预览

@Preview(showBackground = true, name = "动画进度条")
@Composable
fun AnimatedProgressIndicatorPreview() {
    MyApplicationTheme {
        AnimatedProgressIndicator(
            current = 3,
            total = 5,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "动画选项卡")
@Composable
fun AnimatedAnswerOptionCardPreview() {
    MyApplicationTheme {
        // 为了让预览的背景色更明显，可以加一个 padding
        Column(modifier = Modifier.padding(16.dp)) {
            AnimatedAnswerOptionCard(
                option = mockQuestion.options.first(),
                onClick = {}
            )
        }
    }
}

// --- 以下的预览基本不需要改动 ---

@Preview(showBackground = true, name = "生成等待页")
@Composable
fun GeneratingPagePreview() {
    MyApplicationTheme {
        GeneratingPage()
    }
}

@Preview(showBackground = true, name = "搭子生成结果页")
@Composable
fun ResultPagePreview() {
    MyApplicationTheme {
        ResultPage(
            profile = mockProfile,
            onNavigateToNaming = {}
        )
    }
}

@Preview(showBackground = true, name = "结果页 - 加载中")
@Composable
fun ResultPageLoadingPreview() {
    MyApplicationTheme {
        ResultPage(
            profile = null,
            onNavigateToNaming = {}
        )
    }
}

@Preview(showBackground = true, name = "搭子取名页")
@Composable
fun NamingPagePreview() {
    MyApplicationTheme {
        NamingPage(
            profile = mockProfile,
            onConfirm = {}
        )
    }
}
