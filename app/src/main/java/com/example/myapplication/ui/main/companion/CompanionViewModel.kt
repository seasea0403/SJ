package com.example.myapplication.ui.main.companion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CompanionRepository
import com.example.myapplication.ui.main.companion.data.InteractionCategory
import com.example.myapplication.ui.main.companion.data.InteractionItem
import com.example.myapplication.ui.main.companion.data.TaskItem
import com.example.myapplication.ui.main.companion.data.WardrobeCategory
import com.example.myapplication.ui.main.companion.data.WardrobeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanionViewModel @Inject constructor(
    private val repository: CompanionRepository
) : ViewModel() {

    // _uiState 是内部可变的状态，仅在ViewModel内部使用
    private val _uiState = MutableStateFlow(CompanionUiState())

    // uiState 是外部只读的状态，暴露给UI层使用
    val uiState: StateFlow<CompanionUiState> = _uiState.asStateFlow()

    init {
        // ViewModel创建时，立即开始监听搭子信息的变化
        observeBuddyProfile()
        // 加载其他模块的模拟数据
        loadMockData()
    }

    private fun observeBuddyProfile() {
        viewModelScope.launch {
            // 从Repository获取数据流 (Flow)
            // 每当DataStore中的搭子数据变化，这里的代码就会被执行
            repository.getBuddyProfile().collect { profile ->
                _uiState.update { currentState ->
                    currentState.copy(
                        buddyProfile = profile,
                        isLoading = false // 数据收到了，加载完成
                    )
                }
            }
        }
    }

    /**
     * 当用户点击某个任务时调用的方法
     */
    fun onTaskClicked(task: TaskItem) {
        // TODO: 处理任务点击逻辑，比如跳转到任务相关的页面
        println("Task clicked: ${task.title}")
    }

    /**
     * 当用户购买商店物品时调用的方法
     */
    fun onPurchaseConfirmed(productId: String) {
        // TODO: 处理购买逻辑，扣除积分，将物品加入衣柜或背包
        println("Purchase confirmed for product: $productId")
    }

    /**
     * 加载任务、商店等模块的模拟数据。
     * 【说明】在真实项目中，这些数据也应该来自Repository。
     * 为了简化当前步骤，我们暂时在ViewModel中创建它们。
     */
    private fun loadMockData() {
        _uiState.update {
            it.copy(
                ongoingTasks = getMockOngoingTasks(),
                completedTasks = getMockCompletedTasks(),
                wardrobeItems = getMockWardrobeItems(),
                foodItems = getMockInteractionItems(InteractionCategory.FOOD),
                toyItems = getMockInteractionItems(InteractionCategory.TOY)
                // ...可以继续加载商店等其他数据
            )
        }
    }

    // --- 以下是生成模拟数据的私有方法 ---

    private fun getMockOngoingTasks(): List<TaskItem> {
        return listOf(
            TaskItem("task1", "打卡5个景点", "在旅行中记录5个不同的景点", 3, 5, 50),
            TaskItem("task2", "分享一次游记", "发布一篇图文并茂的旅行游记", 0, 1, 100),
            TaskItem("task3", "收集当地美食", "品尝并记录3种当地特色美食", 1, 3, 30)
        )
    }

    private fun getMockCompletedTasks(): List<TaskItem> {
        return listOf(
            TaskItem("task0", "完成新手引导", "初次见面，请多指教", 1, 1, 20, true)
        )
    }

    private fun getMockWardrobeItems(): Map<WardrobeCategory, List<WardrobeItem>> {
        return mapOf(
            WardrobeCategory.HEADWEAR to listOf(
                WardrobeItem("h1", "小草帽", WardrobeCategory.HEADWEAR, "icon_hat", true),
                WardrobeItem("h2", "墨镜", WardrobeCategory.HEADWEAR, "icon_sunglasses")
            ),
            WardrobeCategory.CLOTHING to listOf(
                WardrobeItem("c1", "花衬衫", WardrobeCategory.CLOTHING, "icon_shirt", true)
            )
        )
    }

    private fun getMockInteractionItems(category: InteractionCategory): List<InteractionItem> {
        return if (category == InteractionCategory.FOOD) {
            listOf(
                InteractionItem("f1", "小饼干", 10, 5, "icon_cookie", InteractionCategory.FOOD),
                InteractionItem("f2", "一杯果汁", 15, 8, "icon_juice", InteractionCategory.FOOD)
            )
        } else {
            listOf(
                InteractionItem("t1", "小皮球", 20, 10, "icon_ball", InteractionCategory.TOY)
            )
        }
    }
}
