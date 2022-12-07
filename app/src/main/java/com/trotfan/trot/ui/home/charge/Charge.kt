package com.trotfan.trot.ui.home.charge

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.trotfan.trot.ui.home.HomeSections

@Composable
fun ChargeHome(
    onItemClick: (Long) -> Unit,
    navController: NavController,
    onNavigateClick: (HomeSections) -> Unit,
    modifier: Modifier = Modifier,
) {

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "This is ChargeHome", fontWeight = FontWeight.Bold)
        BackHandler {
            onNavigateClick.invoke(HomeSections.Vote)
        }
    }
}