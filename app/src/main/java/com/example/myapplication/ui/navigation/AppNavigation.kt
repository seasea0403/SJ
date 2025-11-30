package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.auth.LoginScreen
import com.example.myapplication.ui.onboarding.OnboardingScreen
import com.example.myapplication.R
import com.example.myapplication.ui.main.memories.TripScreen
import com.example.myapplication.ui.main.companion.main.CompanionScreen
import com.example.travelapp.ItineraryScreen

// 定义主应用的路由
object Routes {
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"
    const val MAIN = "main"
}

// 定义主应用底部导航的路由
sealed class BottomNavRoute(val route: String, val title: String, val iconRes: Int) {
    object Itinerary : BottomNavRoute("itinerary", "行程", R.drawable.nav_1)
    object Buddies : BottomNavRoute("buddies", "搭子", R.drawable.nav_2)
    object Memories : BottomNavRoute("memories", "回忆", R.drawable.nav_3)
}

val bottomNavRoutes = listOf(
    BottomNavRoute.Itinerary,
    BottomNavRoute.Buddies,
    BottomNavRoute.Memories
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val startDestination = Routes.LOGIN

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 登录页
        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Routes.ONBOARDING) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // 引导流程
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onOnboardingComplete = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // 主应用页面 - 包含底部导航
        composable(Routes.MAIN) {
            MainScreen(navController = navController)
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            MainBottomNavigation(navController = bottomNavController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainNavHost(navController = bottomNavController)
        }
    }
}

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavRoute.Itinerary.route
    ) {
        // 行程页面
        composable(BottomNavRoute.Itinerary.route) {
            ItineraryScreen(navController = navController)
        }

        // 搭子页面
        composable(BottomNavRoute.Buddies.route) {
            CompanionScreen()
        }

        // 回忆页面 - 使用您之前创建的 CodiaMainView
        composable(BottomNavRoute.Memories.route) {
            TripScreen()
        }
    }
}

/**
 * 主应用底部导航栏
 */
@Composable
fun MainBottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.height(80.dp)
    ) {
        bottomNavRoutes.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        // 弹出到起始目的地，避免在底部导航项之间建立回退栈
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // 避免同一目的地的多个副本
                        launchSingleTop = true
                        // 恢复状态
                        restoreState = true
                    }
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // 使用您提供的图标资源
                        Icon(
                            painter = painterResource(id = screen.iconRes),
                            contentDescription = screen.title,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = screen.title,
                            fontSize = 12.sp,
                            color = if (selected) Color(0xff00c3d0) else Color(0xff99a1ae)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xff00c3d0),
                    unselectedIconColor = Color(0xff99a1ae),
                    indicatorColor = Color.White
                )
            )
        }
    }
}