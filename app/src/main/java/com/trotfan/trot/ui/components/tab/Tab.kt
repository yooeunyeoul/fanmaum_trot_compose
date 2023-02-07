package com.trotfan.trot.ui.components.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.*

@Composable
fun CustomTabLayout(
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
    tabs: List<String>
) {
    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.background(Color.White)) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(3.dp)
                        .clip(RoundedCornerShape(1.5.dp))
                        .background(Primary300)
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { tabIndex, tab ->
                Tab(
                    selected = tabIndex == selectedTabIndex,
                    onClick = {
                        onTabClick(tabIndex)
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White,
                    text = {
                        Text(
                            text = tab,
                            color = if (tabIndex == selectedTabIndex) Primary900 else Gray700,
                            style = if (tabIndex == selectedTabIndex) FanwooriTypography.button1 else FanwooriTypography.body3
                        )
                    },
                    modifier = Modifier.background(Color.White)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray200)
        )
    }

}