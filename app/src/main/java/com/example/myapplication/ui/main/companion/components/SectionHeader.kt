package com.example.myapplication.ui.main.companion.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SectionHeader(title: String, onSeeAllClick: (() -> Unit)? = null) { // 参数设为可选，更灵活
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge, // 用大一点的标题
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
        if (onSeeAllClick != null) {
            TextButton(onClick = onSeeAllClick) {
                Text("查看全部")
            }
        }
    }
}
