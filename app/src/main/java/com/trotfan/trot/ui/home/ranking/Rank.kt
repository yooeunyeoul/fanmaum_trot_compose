package com.trotfan.trot.ui.home.ranking

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.trotfan.trot.ui.home.mypage.SampleMyPageList

@Composable
fun RankHome(
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
//    Surface(
//        color = Color.White,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Text(text = "This is RankHome", fontWeight = FontWeight.Bold)
//    }
    SampleMyPageList()
}