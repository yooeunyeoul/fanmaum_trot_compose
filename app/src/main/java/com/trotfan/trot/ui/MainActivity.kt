package com.trotfan.trot.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.trotfan.trot.ui.signup.SearchStarScreen
import com.trotfan.trot.ui.theme.FanwooriTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FanwooriTheme {
                SearchStarScreen()
            }
        }
    }
}
