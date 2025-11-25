package com.example.myapplication.ui.onboarding
import com.example.myapplication.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.onboarding.data.Animal
import com.example.myapplication.ui.onboarding.data.AnswerOption
import com.example.myapplication.ui.onboarding.data.Question
import com.example.myapplication.ui.onboarding.data.TravelBuddyProfile
import com.example.myapplication.ui.theme.MyApplicationTheme // 替换成你的主题路径

// --- 步骤 1: 准备用于预览的假数据 (Mock Data) ---

// 假的“旅行搭子”画像数据
val mockProfile = TravelBuddyProfile(
    animal = Animal.CAT,
    title = "独立漫游者",
    description = "充满好奇心，享受独处，钟情于城市文化艺术的优雅漫游者",
    imageUrl = R.drawable.avatar_cat // 替换成你的猫咪图片资源ID
)

// 假的问题数据
val mockQuestion = Question(
    id = 5,
    text = "当发现一个攻略上极力推荐的餐厅关门了，你会怎么做？",
    options = listOf(
        AnswerOption(
            "opt1",
            "立刻拿出手机，根据之前的备选方案，找到下一家",
            R.drawable.avatar_cat
        ),
        AnswerOption("opt2", "抓住路过的当地人，让他推荐一个私藏的宝藏小馆", R.drawable.avatar_cat),
        AnswerOption(
            "opt3",
            "随遇而安，就在附近随便找一家看起来顺眼的进去试试",
            R.drawable.avatar_cat
        )
    )
)

// --- 步骤 2: 为每个页面创建 @Preview 函数 ---

@Preview(showBackground = true, name = "问题页")
@Composable
fun QuestionPagePreview() {
    // 记得用你的App主题包裹起来，这样颜色、字体才会正确
    MyApplicationTheme {
        QuestionPage(
            question = mockQuestion,
            onAnswerSelected = {} // 在预览中，点击事件留空即可
        )
    }
}

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
            onNavigateToNaming = {} // 在预览中，点击事件留空
        )
    }
}

@Preview(showBackground = true, name = "结果页 - 加载中")
@Composable
fun ResultPageLoadingPreview() {
    // 这个预览可以测试当profile数据还没返回时的样子
    MyApplicationTheme {
        ResultPage(
            profile = null, // 传入 null
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
            onConfirm = {} // 在预览中，点击事件留空
        )
    }
}

@Preview(showBackground = true, name = "取名页 - 未输入名字")
@Composable
fun NamingPageEmptyInputPreview() {
    // 这个预览可以测试当用户还没输入名字时，按钮是否正确地被禁用了
    MyApplicationTheme {
        NamingPage(
            profile = mockProfile,
            onConfirm = {}
        )
        // 因为名字状态在 NamingPage 内部管理，所以这个预览和上面的看起来一样
        // 但你可以通过修改 NamingPage 让初始名字可控，以便于预览不同状态
    }
}
