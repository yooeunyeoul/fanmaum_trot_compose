package com.trotfan.trot.ui.home.mypage.modify

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.trotfan.trot.R
import com.trotfan.trot.datastore.userIdStore
import com.trotfan.trot.datastore.userTokenStore
import com.trotfan.trot.ui.Route
import com.trotfan.trot.ui.components.dialog.HorizontalDialog
import com.trotfan.trot.ui.components.navigation.AppbarMLeftIcon
import com.trotfan.trot.ui.home.mypage.modify.component.ProfileImgModify
import com.trotfan.trot.ui.home.mypage.modify.component.ProfileInfo
import com.trotfan.trot.ui.theme.FanwooriTheme
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Primary500
import com.trotfan.trot.ui.utils.clickable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun ProfileModify(
    navController: NavController? = null
) {
    var logoutDialogState by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppbarMLeftIcon(title = "프로필수정", icon = R.drawable.icon_back, onIconClick = {
                navController?.popBackStack()
            })
            Spacer(modifier = Modifier.height(40.dp))
            ProfileImgModify(modifier = Modifier.align(CenterHorizontally))
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "닉네임",
                style = FanwooriTypography.subtitle1,
                color = Color.Black,
                modifier = Modifier.align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(40.dp))
            ProfileInfo()
            Column(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(bottom = 40.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "로그아웃",
                    style = FanwooriTypography.button1,
                    color = Primary500,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .clickable {
                            logoutDialogState = true
                        }
                )
                Spacer(
                    modifier = Modifier
                        .width(62.dp)
                        .height(1.dp)
                        .background(Primary500)
                        .align(CenterHorizontally)
                )
            }

            if (logoutDialogState) {
                HorizontalDialog(
                    titleText = "로그아웃을 할까요?",
                    positiveText = "로그아웃",
                    negativeText = "취소",
                    onPositive = {
                        coroutineScope.launch {
                            val gso =
                                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestEmail().build()
                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            googleSignInClient.signOut().addOnCompleteListener {
                                navController?.navigate(Route.Login.route) {
                                    logoutDialogState = false
                                }
                            }
                            context.userIdStore.updateData {
                                it.toBuilder().setUserId(0).build()
                            }
                        }
                    },
                    onDismiss = {
                        logoutDialogState = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileModifyPreview() {
    FanwooriTheme {
        ProfileModify()
    }
}