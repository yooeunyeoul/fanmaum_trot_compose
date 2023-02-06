package com.trotfan.trot.ui.components.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray200
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Gray800
import kotlinx.coroutines.launch

@Composable
fun GrayChipTab(
    chipWidth: Dp = 88.dp,
    selectedIndex: Int,
    tabSelect: (Int) -> Unit,
    tabList: List<String> = listOf("on", "off"),
) {
    val coroutineScope = rememberCoroutineScope()

    val gutterMargin = 6.dp

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Column(modifier = Modifier.background(color = Color.White)) {

            TabRow(
                modifier = Modifier.width(chipWidth * 2 + gutterMargin),
                backgroundColor = Color.White,
                selectedTabIndex = selectedIndex,
                // Override the indicator, using the provided pagerTabIndicatorOffset modifier
                indicator = {

                },
                divider = {

                }
            ) {
                tabList.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier
                            .padding(
                                start = if (index == 0) 0.dp else 3.dp,
                                end = if (index == 0) 3.dp else 0.dp
                            )
                            .background(
                                color = if (selectedIndex == index) Gray800 else Color.White,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .border(
                                width = if (index == selectedIndex) 0.dp else 1.dp,
                                color = Gray200,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        text = {

                            Text(
                                text = title,
                                style = FanwooriTypography.button1,
                                color = if (selectedIndex == index) Color.White else Gray700,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                            )
                        },
                        selected = selectedIndex == index,
                        onClick = {
                            coroutineScope.launch {
//                                selectedIndex = index
                                tabSelect.invoke(index)
                            }
                        },
                    )
                }
            }

        }

    }


}

@Preview
@Composable
fun PreviewGrayChipTab() {
    GrayChipTab(selectedIndex = 0, tabSelect = {})
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}
