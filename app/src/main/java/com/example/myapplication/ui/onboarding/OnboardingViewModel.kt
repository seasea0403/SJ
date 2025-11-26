package com.example.myapplication.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R // 确保导入你的R文件
import com.example.myapplication.ui.onboarding.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// UI需要的所有数据都在这里定义
data class OnboardingUiState(
    val questions: List<Question> = emptyList(),
    val userAnswers: Map<Int, String> = emptyMap(), // 存储用户的答案 <问题ID, 答案ID>
    val isGenerating: Boolean = false, // 是否正在显示“生成中”的界面
    val generatedProfile: TravelBuddyProfile? = null, // 生成的最终结果
    val buddyName: String = "",
    val isCompleted: Boolean = false // 整个引导流程是否结束
)

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    // --- 状态管理 ---
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    // --- 1. 数据录入中心 ---

    // 评分矩阵：将你的表格逻辑原封不动搬到代码里
    private val scoringMatrix: Map<String, Map<Animal, Int>> = mapOf(
        "1-A" to mapOf(Animal.CAT to 1, Animal.ELEPHANT to 2), "1-B" to mapOf(Animal.PANDA to 1, Animal.FOX to 2), "1-C" to mapOf(Animal.DOLPHIN to 1, Animal.PANDA to 1, Animal.BIRD to 2), "1-D" to mapOf(Animal.DOLPHIN to 2, Animal.DOG to 1),
        "2-A" to mapOf(Animal.DOG to 1, Animal.ELEPHANT to 2), "2-B" to mapOf(Animal.DOLPHIN to 1, Animal.PANDA to 1, Animal.CAT to 1, Animal.FOX to 2), "2-C" to mapOf(Animal.BIRD to 2, Animal.CAT to 1),
        "3-A" to mapOf(Animal.BIRD to 1, Animal.CAT to 2, Animal.FOX to 1), "3-B" to mapOf(Animal.PANDA to 2, Animal.DOG to 1, Animal.ELEPHANT to 1), "3-C" to mapOf(Animal.DOLPHIN to 2, Animal.DOG to 2),
        "4-A" to mapOf(Animal.PANDA to 2, Animal.CAT to 1), "4-B" to mapOf(Animal.CAT to 2, Animal.FOX to 1), "4-C" to mapOf(Animal.DOLPHIN to 1, Animal.BIRD to 2),
        "5-A" to mapOf(Animal.FOX to 1, Animal.ELEPHANT to 2), "5-B" to mapOf(Animal.DOLPHIN to 1, Animal.DOG to 2), "5-C" to mapOf(Animal.DOLPHIN to 1, Animal.PANDA to 1, Animal.BIRD to 1, Animal.CAT to 1)
    )

    // 动物画像文案库：你提供的所有描述都在这里
    private val animalProfiles: Map<Animal, TravelBuddyProfile> = mapOf(
        Animal.DOLPHIN to TravelBuddyProfile(Animal.DOLPHIN, "快乐社交家", "你喜欢热闹，乐于结交新朋友，并享受通过社交解决旅途中的种种问题。对你来说，分享的快乐远大于独享。", R.drawable.avatar_dolphin,R.drawable.headimage_dolphin),
        Animal.PANDA to TravelBuddyProfile(Animal.PANDA, "温和治愈者", "你偏爱悠闲的度假方式，享受在自然风光中品尝美食的惬意。与家人朋友的温馨时光是你旅行中不可或缺的部分。", R.drawable.avatar_panda,R.drawable.headimage_panda),
        Animal.BIRD to TravelBuddyProfile(Animal.BIRD, "自由探险家", "你向往远方和壮丽的自然风光，不希望被过于详细的计划束缚。享受独立，热衷于户外挑战，天空才是你的极限。", R.drawable.avatar_bird,R.drawable.headimage_bird),
        Animal.CAT to TravelBuddyProfile(Animal.CAT, "独立漫游者", "你钟情于城市的历史文化与艺术气息，享受一个人漫步在街头巷尾的独处时光。灵活的计划让你能随时拥抱不期而遇的惊喜。", R.drawable.avatar_cat,R.drawable.headimage_cat),
        Animal.DOG to TravelBuddyProfile(Animal.DOG, "忠诚同行者", "一份可靠的计划会让你充满安全感。你享受在团队活动中与伙伴们并肩协作，一同分享旅途的欢乐与挑战。", R.drawable.avatar_dog,R.drawable.headimage_dog),
        Animal.FOX to TravelBuddyProfile(Animal.FOX, "机智寻觅者", "你是一位天生的美食家和寻宝者，总能发现那些隐藏在深处的小众美味。灵活机智的你，善于把意外变成旅途中的精彩篇章。", R.drawable.avatar_fox,R.drawable.headimage_fox),
        Animal.ELEPHANT to TravelBuddyProfile(Animal.ELEPHANT, "博学规划家", "你是一位深度的文化爱好者，依赖周密的计划来探索目的地的历史与内涵，甚至会为所有意外情况准备好备选方案。", R.drawable.avatar_elephant,R.drawable.headimage_elephant)
    )

    init {
        loadQuestions()
    }

    // --- 2. 行为处理中心 ---

    // 当用户选择一个答案
    fun onAnswerSelected(questionId: Int, answerId: String) {
        val answers = _uiState.value.userAnswers.toMutableMap()
        answers[questionId] = answerId
        _uiState.update { it.copy(userAnswers = answers) }
    }

    // 提交答案并开始计算
    fun submitAndGenerateBuddy() {
        viewModelScope.launch {
            _uiState.update { it.copy(isGenerating = true) }
            val profile = calculateResult()
            delay(2500) // 模拟计算耗时，给用户一个“生成中”的仪式感
            _uiState.update { it.copy(isGenerating = false, generatedProfile = profile) }
        }
    }

    // 用户命名并最终确认
    fun onBuddyNamedAndConfirm(name: String) {
        // 如果用户没输入名字，给一个默认的
        val finalName = name.ifBlank { _uiState.value.generatedProfile?.title ?: "我的搭子" }
        _uiState.update { it.copy(buddyName = finalName, isCompleted = true) }
    }

    // --- 3. 核心计算逻辑 ---

    private fun calculateResult(): TravelBuddyProfile {
        val finalScores = mutableMapOf<Animal, Int>()
        Animal.entries.forEach { finalScores[it] = 0 }

        _uiState.value.userAnswers.values.forEach { answerId ->
            scoringMatrix[answerId]?.forEach { (animal, score) ->
                finalScores[animal] = finalScores.getOrDefault(animal, 0) + score
            }
        }

        // 找出得分最高的动物，如果分数相同，则返回第一个。
        val winningAnimal = finalScores.maxByOrNull { it.value }?.key ?: Animal.CAT

        return animalProfiles[winningAnimal]!!
    }

    // --- 4. 数据加载 ---

    private fun loadQuestions() {
        // 在这里，根据你的UI设计稿，把问题和选项硬编码进去
        val allQuestions = listOf(
            // 问题一：核心兴趣焦点
            Question(1, "一场完美的旅行,你最期待的核心体验是?", listOf(
                AnswerOption("1-A", "穿梭于博物馆与历史古迹,感受时间的沉淀。",R.drawable.icon_house),
                AnswerOption("1-B", "深入市井小巷,用味蕾探索地道美食的秘密。",R.drawable.icon_noodles),
                AnswerOption("1-C", "远离尘嚣,在壮丽的自然风光中彻底放空自己。",R.drawable.icon_flower),
                AnswerOption("1-D", "参加热闹的节庆或派对,与来自世界各地的朋友举杯。",R.drawable.icon_wine)
            )),

            // 问题二：计划风格
            Question(2, "面对一份详细到分钟的旅行计划表,你的第一感觉是?", listOf(
                AnswerOption("2-A", "太棒了! 一切尽在掌握, 让我充满安全感。",R.drawable.icon_notes),
                AnswerOption("2-B", "有点压力, 我更喜欢只定下大致方向, 随缘探索。",R.drawable.icon_clock),
                AnswerOption("2-C", "束缚感太强, 最好的风景总是在计划之外。",R.drawable.icon_grass)
            )),

            // 问题三：社交倾向
            Question(3, "你理想中的旅行团队配置是?", listOf(
                AnswerOption("3-A", "一个人或与最亲密的伙伴,享受不被打扰的二人世界。",R.drawable.icon_candy),
                AnswerOption("3-B", "和家人或三五好友组成小团体,温馨而自在。",R.drawable.icon_sushi),
                AnswerOption("3-C", "乐于加入一个更大的团队,结识有趣的新朋友。",R.drawable.icon_dialogtext)
            )),

            // 问题四：冒险程度与活动偏好
            Question(4, "在目的地,你更倾向于如何度过一天?", listOf(
                AnswerOption("4-A", "在海边躺椅或舒适的咖啡馆里,悠闲地度过一整天。",R.drawable.icon_cup),
                AnswerOption("4-B", "用脚步丈量城市,不设目的地,在街头随心漫步。",R.drawable.icon_hat),
                AnswerOption("4-C", "挑战一项刺激的户外运动,比如徒步、潜水或登山。",R.drawable.icon_heart)
            )),

            // 问题五：旅行态度与问题解决方式
            Question(5, "当发现一个攻略上极力推荐的餐厅关门了,你会怎么做?", listOf(
                AnswerOption("5-A", "立刻拿出手机,根据之前的备选方案,找到下一家。",R.drawable.icon_photos),
                AnswerOption("5-B", "抓住路过的当地人,让他推荐一个私藏的宝藏小馆。",R.drawable.icon_diary),
                AnswerOption("5-C", "随遇而安,就在附近随便找一家看起来顺眼的进去试试。",R.drawable.icon_cloud)
            ))
        )
        _uiState.update { it.copy(questions = allQuestions) }
    }
}