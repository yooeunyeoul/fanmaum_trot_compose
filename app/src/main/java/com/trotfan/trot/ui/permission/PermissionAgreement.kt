package com.trotfan.trot.ui.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.trotfan.trot.R
import com.trotfan.trot.datastore.PermissionAgreeStore
import com.trotfan.trot.datastore.PermissionAgreementManager
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.home.HomeSections
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.trotfan.trot.ui.theme.*
import kotlinx.coroutines.launch
import com.trotfan.trot.ui.signup.viewmodel.StarSelectViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

@Composable
fun PermissionAgreement(
    navController: NavController? = null,
    terms: Boolean? = null,
    viewModel: StarSelectViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
//
//    val dd = coroutineScope.launch {
//        val permissionAgreementManager =
//            PermissionAgreementManager(context.PermissionAgreeStore)
//        coroutineScope.launch {
//            permissionAgreementManager.permissionCheck(true)
//        }
//
//        if (terms == true) {
//            navController?.navigate(HomeSections.Vote.route) {
//                popUpTo(Route.PermissionAgreement.route) {
//                    inclusive = true
//                }
//            }
//        } else {
//            navController?.navigate(Route.TermsAgreement.route) {
//                popUpTo(Route.PermissionAgreement.route) {
//                    inclusive = true
//                }
//            }
//        }
//    }
//    val launcher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        dd.start()
//
//    }

    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(64.dp))
                Text(text = "앱 접근 권한 안내", style = FanwooriTypography.h1, color = Gray900)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "팬우리 서비스 사용을 위해\n" +
                            "동의가 필요한 내용을 확인해주세요",
                    style = FanwooriTypography.caption1,
                    color = Gray700
                )
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = "선택권한",
                    style = FanwooriTypography.caption1,
                    color = Gray700
                )
                Spacer(modifier = Modifier.height(16.dp))
                PermissionItem(
                    title = "알림",
                    content = "푸시 알림/메세지 수신",
                    icon = R.drawable.icon_bell
                )
                Spacer(modifier = Modifier.height(48.dp))
                PermissionExplanation(text = "선택 접근 권한을 동의하지 않아도 앱 서비스 이용이 가능합니다.")
                Spacer(modifier = Modifier.height(8.dp))
                PermissionExplanation(text = "철회 방법 : 설정 > 애플리케이션 > 팬마음 > 권한 > 접근권한 동의 또는 철회 선택")
            }

            BtnFilledLPrimary(
                text = "확인",
                modifier = Modifier
                    .align(BottomCenter)
                    .padding(bottom = 32.dp)
            ) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    askNotificationPermission(
//                        context,
//                        launcher,
//                        dd
//                    )
//                } else {
//                    dd.start()
//                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
fun askNotificationPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    dd: Job
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            dd.start()
        } else {
            // Directly ask for the permission
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}


@Composable
fun PermissionItem(title: String, content: String, icon: Int) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Gray100)
                .align(CenterVertically)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Gray800,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = title, style = FanwooriTypography.body3, color = Gray800)
            Text(text = content, style = FanwooriTypography.caption2, color = Gray650)
        }
    }
}

@Composable
fun PermissionExplanation(text: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "-",
            style = FanwooriTypography.caption2,
            color = Gray700,
            modifier = Modifier.width(18.dp),
            textAlign = TextAlign.Center
        )
        Text(text = text, style = FanwooriTypography.caption2, color = Gray700)
    }
}

@Preview
@Composable
fun PermissionAgreementPreview() {
    FanwooriTheme {
        PermissionAgreement()
    }
}