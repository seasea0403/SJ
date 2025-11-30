package com.example.myapplication.ui.main

import androidx.compose.runtime.Composable
import android.R.attr.icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.travelapp.ItineraryScreen

//@Composable
//fun MainScreen(navController: NavController) {
//    // 内部导航控制器，用于底部导航栏的页面切换
//    val innerNavController = rememberNavController()
//
//    // 底部导航栏的各项
//    val items = listOf(
//        BottomNavItem(
//            name = stringResource(R.string.nav_trip),
//            route = "trip_route",
//            icon = R.drawable.nav_1 // 替换成你的行程图标
//        ),
//        BottomNavItem(
//            name = stringResource(R.string.nav_buddy),
//            route = "buddy_route",
//            icon = R.drawable.nav_2 // 替换成你的搭子图标
//        ),
//        BottomNavItem(
//            name = stringResource(R.string.nav_note),
//            route = "note_route",
//            icon = R.drawable.nav_3 // 替换成你的随记图标
//        )
//    )
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(
//                items = items,
//                navController = innerNavController,
//                onItemClick = { item ->
//                    innerNavController.navigate(item.route) {
//                        // 清除返回栈，避免重复点击时栈中有多个相同页面
//                        popUpTo(innerNavController.graph.startDestinationId)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Box(modifier = Modifier.padding(paddingValues)) {
//            // 内部导航图，用于底部导航栏的页面切换
//            NavHost(
//                navController = innerNavController,
//                startDestination = "trip_route"
//            ) {
//                composable("trip_route") {
//                    ItineraryScreen()
//                }
////                composable("buddy_route") {
////                    // 创建搭子页面
////                    CompanionScreen()
////                }
//                composable("note_route") {
//                    // 创建随记页面
//                    NoteScreen()
//                }
//            }
//        }
//    }
//}


// 简单的随记页面占位符
@Composable
fun NoteScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("随记页面 - 开发中")
    }
}