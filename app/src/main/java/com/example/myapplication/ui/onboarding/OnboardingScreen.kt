package com.example.myapplication.ui.onboarding
//----- 基础UI元素与布局 (Foundation & Layout) -----//
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

//----- Material Design 3 风格组件 -----//
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

//----- Compose 核心运行时与状态管理 -----//
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
// 添加这一行
import androidx.hilt.navigation.compose.hiltViewModel


//----- UI 通用修饰符、对齐、资源等 -----//
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp



//----- 协程(用于异步任务) -----//
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//----- 你自己项目中的类 -----//
import com.example.myapplication.R // 导入资源文件，用于图片等
import com.example.myapplication.ui.onboarding.data.Question
import com.example.myapplication.ui.onboarding.data.TravelBuddyProfile
//----- UI 通用修饰符、对齐、资源等 -----//



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit, // 用于导航到App主页的回调，说明完成引导
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()//只要 ViewModel 里的 uiState 有任何更新，Compose 就会自动、智能地重绘使用到 uiState 的部分。
    val questions = uiState.questions
    // Pager总页数 = 问题数 + 生成等待页1 + 结果显示和命名页2
    val pageCount = if (questions.isEmpty()) 0 else questions.size + 3//一个选择
    val pagerState = rememberPagerState { pageCount }
    val coroutineScope = rememberCoroutineScope()

    // 监听ViewModel中的完成信号，一旦为true就执行导航
    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            delay(500) // 短暂延迟后跳转
            onOnboardingComplete()
        }
    }

    Scaffold(//屏幕布局
        topBar = {
            // 只在问题页面显示进度条
            if (pagerState.currentPage < questions.size) {
                // 在这里放置你的顶部进度条和返回按钮UI
                LinearProgressIndicator(
                    progress = { (pagerState.currentPage + 1) / questions.size.toFloat() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(padding),
            userScrollEnabled = false // 禁止用户手动滑动，必须通过代码控制
        ) { pageIndex ->
            // 根据当前页码，显示不同的内容
            when {
                // 问题页面 (0 到 questions.size - 1)
                pageIndex < questions.size -> {
                    val question = questions[pageIndex]
                    QuestionPage(
                        question = question,
                        onAnswerSelected = { answerId ->
                            viewModel.onAnswerSelected(question.id, answerId)
                            // 自动翻到下一页
                            coroutineScope.launch {
                                if (pageIndex < questions.size - 1) {
                                    pagerState.animateScrollToPage(pageIndex + 1)
                                } else {
                                    // 这是最后一题，翻到“生成中”页面，并触发计算
                                    pagerState.animateScrollToPage(pageIndex + 1)
                                    viewModel.submitAndGenerateBuddy()
                                }
                            }
                        }
                    )
                }
                // 生成等待页面
                pageIndex == questions.size -> {
                    GeneratingPage()
                }
                // 搭子生成与命名页面

                pageIndex == questions.size + 1 -> {
                    ResultPage(
                        profile = uiState.generatedProfile,
                        onNavigateToNaming = { // 点击“为TA取个名字”按钮后
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pageIndex + 1)
                            }
                        }
                    )
                }
                // (新) 搭子取名页面
                pageIndex == questions.size + 2 -> {
                    NamingPage(
                        profile = uiState.generatedProfile,
                        onConfirm = { name ->
                            viewModel.onBuddyNamedAndConfirm(name)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionPage(question: Question, onAnswerSelected: (String) -> Unit) {
    // 使用 Column, Text, Button/Card 来构建你的问题和选项列表UI
    // 示例：
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(question.text, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(32.dp))
        question.options.forEach { option ->
            Button(onClick = { onAnswerSelected(option.id) }) {
                Text(option.text)
            }
        }
    }
}

@Composable
fun GeneratingPage() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // 假设你的星星图标资源是 R.drawable.ic_generating_star
            Image(
                painter = painterResource(id = R.drawable.image_33212),
                contentDescription = null
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "正在生成你的专属搭子...", // 你可以加上...的动画效果
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ResultPage(profile: TravelBuddyProfile?, onNavigateToNaming: () -> Unit) {
    // profile为null时显示加载中
    if (profile == null) {
        // 可以用同一个GeneratingPage，或者一个更简单的加载动画
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 头像
        Image(
            painter = painterResource(id = profile.imageUrl),
            contentDescription = profile.title,
            modifier = Modifier.size(180.dp) // 调整大小
        )
        Spacer(Modifier.height(24.dp))

        // 称号 (带星星✨)
        Text(
            text = "✨ ${profile.title} ✨",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))

        // 描述
        Text(
            profile.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.weight(1f)) // 将按钮推到底部

        // 按钮
        Button(
            onClick = onNavigateToNaming,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            // 使用你的主题颜色
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD966))
        ) {
            Text("为TA取个名字", color = Color.Black)
        }
    }
}
@Composable
fun NamingPage(profile: TravelBuddyProfile?, onConfirm: (String) -> Unit) {
    var name by remember { mutableStateOf("") }

    if (profile == null) {
        // 正常不会出现，因为ResultPage已经处理了null的情况
        // 但作为防御性编程，保留加载状态
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(60.dp))

        // 小圆形头像
        Image(
            painter = painterResource(id = profile.imageUrl),
            contentDescription = profile.title,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape) // 裁切成圆形
                .background(Color(0xFF63E6BE)) // 添加绿色背景
                .padding(8.dp) // 留出内边距
        )
        Spacer(Modifier.height(32.dp))

        Text(
            "快为你的新搭子取个专属名字吧！",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "这个名字将陪伴你所有的旅程",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(48.dp))

        // 输入框
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("例如: 小橘、阿旺、Luna...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        Spacer(Modifier.weight(1f)) // 占满剩余空间，把按钮推到底部

        // 完成按钮
        Button(
            onClick = { onConfirm(name) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD966)),
            // 当用户未输入名字时，按钮不可用
            enabled = name.isNotBlank()
        ) {
            Text("开始旅程", color = Color.Black)
        }
    }
}

