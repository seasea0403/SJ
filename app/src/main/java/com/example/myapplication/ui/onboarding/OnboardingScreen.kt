package com.example.myapplication.ui.onboarding
//----- 基础UI元素与布局 (Foundation & Layout) -----//
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState

//----- Material Design 3 风格组件 -----//
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
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
// ... (其他 imports)
import androidx.compose.ui.tooling.preview.Preview // 导入 Preview 注解
import com.example.myapplication.ui.theme.MyApplicationTheme // 导入你的App主题
// ...


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit, // 用于导航到App主页的回调
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val questions = uiState.questions
    // Pager总页数 = 问题数 + 生成等待页 + 结果命名页
    val pageCount = if (questions.isEmpty()) 0 else questions.size + 2
    val pagerState = rememberPagerState { pageCount }
    val coroutineScope = rememberCoroutineScope()

    // 监听ViewModel中的完成信号，一旦为true就执行导航
    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            delay(500) // 短暂延迟后跳转
            onOnboardingComplete()
        }
    }

    Scaffold(
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
                    ResultAndNamePage(
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
    // 根据你的“生成等待”页面设计，可以放一个动画 (Lottie) 或者简单的文本
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // CircularProgressIndicator()
            Text("正在生成你的专属搭子...", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun ResultAndNamePage(profile: TravelBuddyProfile?, onConfirm: (String) -> Unit) {
    var name by remember { mutableStateOf("") }

    // 在profile为null时（即刚进入页面，计算还未完成），可以显示加载动画
    if (profile == null) {
        GeneratingPage()
        return
    }

    // 根据你的“搭子生成”和“搭子取名”页面设计UI
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = profile.imageUrl), contentDescription = profile.title)
        Text(profile.title, style = MaterialTheme.typography.titleLarge)
        Text(profile.description, textAlign = TextAlign.Center)
        Spacer(Modifier.height(40.dp))

        Text("快为你的新搭子取个专属名字吧！")
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("例如: 小橘, Luna...") })
        Spacer(Modifier.height(16.dp))

        Button(onClick = { onConfirm(name) }) {
            Text("完成")
        }
    }
}

