package com.trotfan.trot.ui.home.mypage.setting

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.oss.licenses.OssLicensesActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.trotfan.trot.BuildConfig
import com.trotfan.trot.R
import com.trotfan.trot.datastore.AppVersionManager
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.button.BtnFilledMBlack
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.home.mypage.home.component.Line
import com.trotfan.trot.ui.home.mypage.home.component.MyPageListComponent
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickableSingle
import java.net.URLEncoder

@Composable
fun AppInfo(navController: NavController?) {
    val context = LocalContext.current
    val appVersionManager = AppVersionManager(context.AppVersionManager)
    val storeVersion by appVersionManager.getStoreVersion.collectAsState(initial = "1.0.0")
    val versionName = BuildConfig.VERSION_NAME

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
            painter = painterResource(id = R.drawable.fanmaumsymbol),
            contentDescription = null,
            modifier = Modifier.size(72.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(text = "현재버전", style = FanwooriTypography.caption2, color = Gray600)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = versionName, style = FanwooriTypography.caption2, color = Gray600)
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (storeVersion.replace(".", "")
                .toInt() > versionName.replace(".", "")
                .replace("_dev", "")
                .replace("_qa", "")
                .toInt()
        ) {
            BtnFilledMBlack(text = "업데이트") {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("market://details?id=${context.packageName}")
                context.startActivity(intent)
            }
        } else {
            Text(text = "최신 버전을 사용 중입니다.", style = FanwooriTypography.body2, color = Gray800)
        }
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clickableSingle {
                    OssLicensesMenuActivity.setActivityTitle("오프소스 라이센스")
                    context.startActivity(
                        Intent(
                            context,
                            OssLicensesMenuActivity::class.java
                        )
                    )
                }
        ) {
            androidx.compose.material.Text(
                text = "오프소스 라이센스",
                style = FanwooriTypography.body3,
                color = Gray750,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 24.dp)
                    .weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow),
                contentDescription = null,
                tint = Gray750,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 24.dp)
            )
        }
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