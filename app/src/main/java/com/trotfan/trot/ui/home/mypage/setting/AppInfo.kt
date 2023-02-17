package com.trotfan.trot.ui.home.mypage.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.home.mypage.home.component.Line
import com.trotfan.trot.ui.home.mypage.home.component.MyPageListComponent
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray600
import com.trotfan.trot.ui.theme.Gray800

@Composable
fun AppInfo(navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppbarMLeftIcon(title = "앱정보", icon = R.drawable.icon_back, onIconClick = {
            navController?.popBackStack()
        })

        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.kakao_symbol),
            contentDescription = null,
            modifier = Modifier.size(72.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Text(text = "현재버전", style = FanwooriTypography.caption2, color = Gray600)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "1.0.0", style = FanwooriTypography.caption2, color = Gray600)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "최신 버전을 사용 중입니다.", style = FanwooriTypography.body2, color = Gray800)
        Spacer(modifier = Modifier.height(32.dp))
        MyPageListComponent(
            text = "이용약관",
            url = "https://terms.fanmaum.com/",
            navController = navController
        )
        Line()
        MyPageListComponent(
            text = "개인정보 처리방침",
            url = "https://privacy.fanmaum.com/",
            navController = navController
        )
        Line()
        MyPageListComponent(
            text = "오픈소스 라이센스",
            url = "https://privacy.fanmaum.com/",
            navController = navController
        )
        Line()
    }
}

@Preview
@Composable
fun AppInfoPreview() {
    FanwooriTheme {
        AppInfo(navController = null)
    }
}