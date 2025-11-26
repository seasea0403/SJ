package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.auth.LoginScreen
import com.example.myapplication.ui.onboarding.OnboardingScreen

@Composable
fun AppNavigation() {
    // 1. 创建导航控制器，它负责所有导航操作
    val navController = rememberNavController()

    // ---- 这里可以加入判断初始状态的逻辑 ----
    // 假设我们有一个 MainViewModel 来判断初始路由
    // val viewModel: MainViewModel = hiltViewModel()
    // val startDestination = viewModel.startDestination.collectAsState().value

    // 为简化，我们先假设初始总是从登录开始
    val startDestination = Routes.LOGIN

    // 2. NavHost是所有可导航页面的容器
    NavHost(
        navController = navController,
        startDestination = startDestination // 决定APP启动时显示哪个页面
    ) {
        // 3. 定义每一个页面 (Composable)

        // 登录页
        composable(Routes.LOGIN) {
            LoginScreen(
                // ✅ 修改后的代码
                onNavigateToOnboarding = {
                    // 登录成功后，跳转到问卷流程，并清空登录页的返回栈
                    navController.navigate(Routes.ONBOARDING) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // 问卷、匹配、命名流程 (整个引导流程)
        composable(Routes.ONBOARDING) {
            // 这里的 OnboardingScreen 内部可能还包含自己的导航或 Pager
            OnboardingScreen(
                onOnboardingComplete = {
                    // 所有引导流程完成后，跳转到主应用界面
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // 主应用页面 (包含底部导航)
        composable(Routes.MAIN) {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    TODO("Not yet implemented")
}

