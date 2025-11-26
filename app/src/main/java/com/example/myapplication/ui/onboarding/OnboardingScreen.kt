package com.example.myapplication.ui.onboarding
//----- 基础UI元素与布局 (Foundation & Layout) -----//
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

//----- Material Design 3 风格组件 -----//
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

//----- Compose 核心运行时与状态管理 -----//
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel


//----- UI 通用修饰符、对齐、资源等 -----//
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//----- 协程(用于异步任务) -----//
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//----- 你自己项目中的类 -----//
import com.example.myapplication.R // 导入资源文件，用于图片等
import com.example.myapplication.ui.onboarding.data.AnswerOption
import com.example.myapplication.ui.onboarding.data.Question
import com.example.myapplication.ui.onboarding.data.TravelBuddyProfile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val questions = uiState.questions
    val pageCount = if (questions.isEmpty()) 0 else questions.size + 3
    val pagerState = rememberPagerState { pageCount }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            delay(500)
            onOnboardingComplete()
        }
    }

    Scaffold(
        // CHANGED: 修改了顶栏的逻辑，使其更符合你的新设计
        topBar = {
            // 只在问题页面显示返回按钮和进度条
            if (pagerState.currentPage < questions.size) {
                OnboardingTopBar(
                    onBackClicked = {
                        coroutineScope.launch {
                            if (pagerState.currentPage > 0) {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    },
                    currentStep = pagerState.currentPage + 1,
                    totalSteps = questions.size
                )
            }
        },
        // ADDED: 给整个屏幕一个柔和的背景色，更接近你的截图效果
        containerColor = Color(0xFFFCFCF9)
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(padding),
            userScrollEnabled = false
        ) { pageIndex ->
            when {
                pageIndex < questions.size -> {
                    val question = questions[pageIndex]
                    QuestionPage(
                        question = question,
                        onAnswerSelected = { answerId ->
                            viewModel.onAnswerSelected(question.id, answerId)
                            coroutineScope.launch {
                                // 短暂延迟让用户看到点击动画
                                delay(300)
                                if (pageIndex < questions.size - 1) {
                                    pagerState.animateScrollToPage(pageIndex + 1)
                                } else {
                                    pagerState.animateScrollToPage(pageIndex + 1)
                                    viewModel.submitAndGenerateBuddy()
                                }
                            }
                        }
                    )
                }
                pageIndex == questions.size -> {
                    GeneratingPage()

                    LaunchedEffect(Unit) {
                        // 同时启动两个“任务”
                        val timerJob = launch { delay(3000L) } // 任务1: 保证至少显示3秒
                        val dataJob = launch {
                            // 任务2: 等待 profile 数据准备好
                            // snapshotFlow 会将 Compose State 转换成一个 Flow
                            // .first 会一直等待，直到满足条件 { it != null }
                            snapshotFlow { uiState.generatedProfile }
                                .first { profile -> profile != null }
                        }

                        // 等待上面两个任务全部完成
                        joinAll(timerJob, dataJob)

                        // 两个条件都满足后，才执行导航
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pageIndex + 1)
                        }
                    }
                }

                pageIndex == questions.size + 1 -> ResultPage(
                    profile = uiState.generatedProfile,
                    onNavigateToNaming = {
                        coroutineScope.launch { pagerState.animateScrollToPage(pageIndex + 1) }
                    }
                )
                pageIndex == questions.size + 2 -> NamingPage(
                    profile = uiState.generatedProfile,
                    onConfirm = { name -> viewModel.onBuddyNamedAndConfirm(name) }
                )
            }
        }
    }
}

// NEW: 新的 TopBar 组件，整合了返回按钮和动画进度条
@Composable
fun OnboardingTopBar(
    onBackClicked: () -> Unit,
    currentStep: Int,
    totalSteps: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp) // 给状态栏留出一些空间
    ) {
        // 返回按钮
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "返回上一题"
            )
        }
        // 带动画的进度条
        AnimatedProgressIndicator(
            current = currentStep,
            total = totalSteps,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

// NEW: 带动画的进度条组件
@Composable
fun AnimatedProgressIndicator(
    current: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    val progressTarget = current.toFloat() / total
    val animatedProgress by animateFloatAsState(
        targetValue = progressTarget,
        animationSpec = tween(durationMillis = 500),
        label = "ProgressAnimation"
    )

    Column(modifier = modifier) {
        LinearProgressIndicator(
            progress = { animatedProgress }, // CHANGED: 适应 LinearProgressIndicator 的 lambda 写法
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.primary, // 你可以自定义颜色
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$current / $total",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}


// CHANGED: 调整了 QuestionPage 的布局和间距，使其更美观
@Composable
fun QuestionPage(question: Question, onAnswerSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp,vertical = 30.dp), // 统一的水平边距
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(60.dp)) // 使用 Spacer 控制垂直间距

        Text(
            text = question.text,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(65.dp)) // 题目和选项之间的间距

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp), // 选项之间的标准间距
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(question.options) { option ->
                // CHANGED: 使用新的带动画的卡片
                AnimatedAnswerOptionCard(option = option, onClick = { onAnswerSelected(option.id) })
            }
        }
    }
}

// CHANGED: 这是升级后的 AnswerOptionCard，现在带有交互动画
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedAnswerOptionCard(option: AnswerOption, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.03f else 1.0f,
        label = "CardScale"
    )
    val containerColor by animateColorAsState(
        targetValue = if (isPressed) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surface,
        label = "CardColor"
    )

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .scale(scale),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- NEW: 图标显示逻辑 ---
            // 只有当icon不为null时，才会显示图标区域
            option.icon?.let { iconRes ->
                // 1. 创建一个圆形的背景
                Box(
                    modifier = Modifier
                        .size(40.dp) // 圆形背景的大小
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant), // 背景颜色
                    contentAlignment = Alignment.Center
                ) {
                    // 2. 在背景里放置图标
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null, // 因为旁边的文字已经解释了选项，这里设为null
                        modifier = Modifier.size(24.dp), // 图标本身的大小
                        tint = MaterialTheme.colorScheme.onSurfaceVariant // 图标的颜色
                    )
                }
                // 3. 在图标和文字之间增加间距
                Spacer(Modifier.width(16.dp))
            }
            // --- END OF NEW SECTION ---

            Text(option.text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

// -- 后续页面 (GeneratingPage, ResultPage, NamingPage) 保持不变 --
// ... (你原来的 GeneratingPage, ResultPage, NamingPage 代码)
@Composable
fun GeneratingPage() {
    // --- 1. 图片旋转动画 ---
    // a. 创建一个无限过渡动画
    val infiniteTransition = rememberInfiniteTransition(label = "GeneratingPageTransition")

    // b. 定义一个在0到360度之间循环的浮点数动画值
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing)
        ),
        label = "ImageRotation"
    )

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.generate_wait), // 确保你的图片资源名正确
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp) // 给图片一个固定大小
                    .rotate(rotation) // c. 应用旋转动画
            )
            Spacer(Modifier.height(32.dp))

            // --- 2. 使用新的跳动文字组件 ---
            JumpingText(
                text = "正在生成你的专属搭子...",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

/**
 * 一个让文字逐字跳动的 Composable
 * @param text 要显示的文本
 * @param style 文本样式
 * @param jumpHeight 跳动的高度
 * @param delayBetweenChars 每个字符动画开始的延迟
 */
@Composable
fun JumpingText(
    text: String,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyLarge,
    jumpHeight: Float = 10f,
    delayBetweenChars: Long = 80L
) {
    // 为每个字符创建一个 Animatable，用于控制其垂直偏移
    val animatables = remember(text) {
        text.map { Animatable(0f) }
    }

    // 使用 LaunchedEffect 在组件进入屏幕时启动动画协程
    LaunchedEffect(text) {
        // 创建一个无限循环
        while (true) {
            // 遍历所有字符的 Animatable
            animatables.forEachIndexed { index, animatable ->
                // 为每个字符启动一个单独的协程来执行动画
                launch {
                    // 1. 根据字符索引计算延迟
                    delay(index * delayBetweenChars)

                    // 2. 向上跳动
                    animatable.animateTo(
                        targetValue = -jumpHeight,
                        animationSpec = tween(durationMillis = 200)
                    )

                    // 3. 回到原位
                    animatable.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 300)
                    )
                }
            }
            // 等待一轮动画和短暂的停顿结束，然后开始下一轮
            delay(text.length * delayBetweenChars + 400)
        }
    }

    // 使用 Row 将每个字符水平排列
    Row {
        text.forEachIndexed { index, char ->
            // 对每个字符应用其对应的偏移量
            Text(
                text = char.toString(),
                style = style,
                modifier = Modifier.offset(y = animatables[index].value.dp)
            )
        }
    }
}


@Composable
fun ResultPage(profile: TravelBuddyProfile?, onNavigateToNaming: () -> Unit) {
    // 1. 加载状态：当 profile 为 null 时显示一个加载圈，这部分逻辑保持不变
    if (profile == null) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFFCFCF9)), // 加上背景色，避免闪烁
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // 2. 主页面布局：使用一个 Column 来垂直排列所有元素
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFCF9)) // 设置和图片一致的米白色背景
            .padding(horizontal = 32.dp),   // 为左右两边提供一些边距
        horizontalAlignment = Alignment.CenterHorizontally, // 所有子项水平居中
        verticalArrangement = Arrangement.Center // 将所有内容作为一个整体在垂直方向上居中
    ) {
        // --- 内容从这里开始，按图片顺序排列 ---

        Box(
            // contentAlignment 使得内部的元素默认居中对齐
            contentAlignment = Alignment.Center,
            // 给Box一个合适的尺寸，以容纳两个图片
            modifier = Modifier.width(400.dp)
        ) {
            // 1. 背景绿底 (先绘制，在最下层)
            // 假设你的绿色背景图片资源名为 R.drawable.green_background_shape
            Image(
                painter = painterResource(id = R.drawable.green_background),
                contentDescription = null, // 装饰性图片，无需描述
                modifier = Modifier
                    .size(300.dp) // 给角色本身一个尺寸
                    .offset(y = (50).dp) // 向Y轴负方向（向上）移动16dp
            )

            // 2. 角色图片 (后绘制，在最上层)
            //这里使用 profile.imageUrl 来获取角色图片
            Image(
                painter = painterResource(id = profile.imageUrl),
                contentDescription = profile.title,
                // 对角色图片进行微调，让它稍微向上偏移，看起来更协调
                modifier = Modifier
                    .size(300.dp) // 给角色本身一个尺寸
                    .offset(y = (-22).dp) // 向Y轴负方向（向上）移动16dp
            )
        }
        // --- END OF NEW SECTION ---

        Spacer(Modifier.height(5.dp))

        // 4. 标题文字
        Text(
            text = "✨ ${profile.title} ✨",
            style = MaterialTheme.typography.headlineSmall, // 使用合适的字体样式
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        // 5. 描述文字
        Text(
            text = profile.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center, // 确保文字居中对齐
            color = MaterialTheme.colorScheme.onSurfaceVariant // 使用一个柔和点的文字颜色
        )

        Spacer(Modifier.height(48.dp)) // 在描述和按钮之间增加更多间距

        // 6. “为TA取个名字” 按钮
        Button(
            onClick = onNavigateToNaming,
            modifier = Modifier
                .fillMaxWidth() // 按钮宽度占满父容器（受Column的padding限制）
                .height(56.dp),
            shape = RoundedCornerShape(50), // 50%的圆角会形成胶囊形状
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD966) // 你截图中精确的黄色
            )
        ) {
            Text(
                text = "为TA取个名字",
                color = Color.Black, // 文字颜色为黑色
                fontWeight = FontWeight.Bold
            )
        }
    }
}
// In: ui/onboarding/OnboardingScreen.kt

@Composable
fun NamingPage(profile: TravelBuddyProfile?, onConfirm: (String) -> Unit) {
    var name by remember { mutableStateOf("") }

    if (profile == null) {
        // 加载状态保持不变
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // 1. 整体布局：使用更接近设计的背景色，并调整内边距
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF0)) // 使用米黄色背景
            .padding(vertical = 140.dp, horizontal = 32.dp),  // 水平方向的内边距
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(80.dp))

        // 2. 头像部分：使用Box实现绿色圆形背景和头像的层叠效果
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .background(color = Color(0xFF34D399), shape = CircleShape) // 绿色圆形背景
        ) {
            Image(
                // 确保使用正确的头像资源ID
                painter = painterResource(id = profile.headUrl),
                contentDescription = profile.title,
                modifier = Modifier.size(90.dp) // 头像图片比背景略小，形成边框
            )
        }

        Spacer(Modifier.height(32.dp))

        // 3. 文本部分：字体加粗，更醒目
        Text(
            "快为你的新搭子取个专属名字吧！",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "这个名字将陪伴你所有的旅程",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(48.dp))

        // 4. 输入框：用Surface + TextField替换OutlinedTextField，实现卡片效果
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 4.dp // 添加轻微的阴影
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                // 使用placeholder而不是label
                placeholder = {
                    Text(
                        "例如: 小橘、阿旺、Luna...",
                        modifier = Modifier.fillMaxWidth(), // 让placeholder也居中
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true,
                // 将输入文本居中
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                // 自定义颜色，使其透明，无下划线
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }

        Spacer(Modifier.height(24.dp))// 这个Spacer会把下面的按钮推到底部

        // 5. 按钮：添加底部外边距，让它和屏幕边缘保持距离
        Button(
            onClick = { onConfirm(name) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEFBF35)),
            enabled = name.isNotBlank()
        ) {
            Text("开始旅程", color = Color.Black)
        }
    }
}

