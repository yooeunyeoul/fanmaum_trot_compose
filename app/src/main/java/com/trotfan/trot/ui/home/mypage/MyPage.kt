package com.trotfan.trot.ui.home.mypage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun MyPageHome(
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        SampleMyPageList()
    }
}