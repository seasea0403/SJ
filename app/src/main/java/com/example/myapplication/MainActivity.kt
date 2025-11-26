package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.auth.LoginScreen
import com.example.myapplication.ui.navigation.AppNavigation
import com.example.myapplication.ui.onboarding.OnboardingScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                // Surface 容器，它会给子组件一个固定大小的“舞台”
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // ✅ 直接调用 LoginScreen，不再用任何东西包裹它
                    // LoginScreen 自己会负责自己的滚动
                    OnboardingScreen(onOnboardingComplete = {})
                }
            }
        }
    }
}
