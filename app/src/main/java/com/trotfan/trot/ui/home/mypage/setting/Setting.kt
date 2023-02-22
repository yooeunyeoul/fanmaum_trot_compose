package com.trotfan.trot.ui.home.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trotfan.trot.BuildConfig
import com.trotfan.trot.R
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickableSingle

@Composable
fun Setting(
    navController: NavController? = null
) {
    Surface(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            AppbarMLeftIcon(title = "설정", icon = R.drawable.icon_back, onIconClick = {
                navController?.popBackStack()
            })
            SettingComponent(text = "계정 정보") {
                navController?.navigate(Route.SettingAccount.route)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 24.dp, end = 24.dp)
                    .background(Gray100)
            )
            SettingComponent(text = "앱 알림 설정") {
                navController?.navigate(Route.SettingPush.route)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 24.dp, end = 24.dp)
                    .background(Gray100)
            )

            val versionName = BuildConfig.VERSION_NAME
            SettingComponent(text = "앱 정보", version = versionName) {
                navController?.navigate(Route.AppInfo.route)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 24.dp, end = 24.dp)
                    .background(Gray100)
            )
        }
    }
}


@Composable
fun SettingComponent(text: String, version: String? = null, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickableSingle {
                onClick()
            }
    ) {
        Text(
            text = text,
            style = FanwooriTypography.body3,
            color = Gray800,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 24.dp)
                .weight(1f)
        )

        version?.let {
            Text(
                text = "현재 버전 $it",
                style = FanwooriTypography.body2,
                color = Primary500,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp)
                    .weight(1f),
                textAlign = TextAlign.End
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.icon_arrow),
            contentDescription = null,
            tint = Gray700,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 24.dp)
        )
    }
}


@Preview
@Composable
fun SettingPreview() {
    FanwooriTheme {
        Setting()
    }
}