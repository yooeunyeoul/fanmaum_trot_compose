package com.trotfan.trot.ui.home.mypage.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.R
import com.trotfan.trot.ui.home.mypage.home.MyPageViewModel
import com.trotfan.trot.ui.theme.*
import com.trotfan.trot.ui.utils.clickable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trotfan.trot.datastore.UserInfoDataStore
import com.trotfan.trot.datastore.UserInfoManager.Companion.USER_PROFILE_IMAGE
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun MyProfile(
    onClick: () -> Unit,
    viewModel: MyPageViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val userName by viewModel.userName.collectAsState()
    val starName by viewModel.starName.collectAsState()
    var userProfileImage by remember {
        mutableStateOf<String?>(null)
    }

    coroutineScope.launch {
        context.UserInfoDataStore.data.collect {
            userProfileImage = it[USER_PROFILE_IMAGE]
        }
    }

    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(104.dp)
                .border(2.dp, Gray300, CircleShape)
                .clip(CircleShape)
                .background(Gray200)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mypage_profile),
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .align(Center)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userProfileImage)
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .align(CenterVertically)
                .weight(1f)
        ) {
            Row(
                Modifier
                    .background(
                        brush = gradient01,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .height(24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.icon_star),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = starName,
                    color = Color.White,
                    style = FanwooriTypography.button2,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))

            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = userName, style = FanwooriTypography.subtitle1, color = Gray900)

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(text = "프로필수정", style = FanwooriTypography.body2, color = Gray700)
                Icon(
                    painter = painterResource(id = R.drawable.icon_pen),
                    contentDescription = null,
                    tint = Gray650,
                    modifier = Modifier
                        .align(CenterVertically)
                        .size(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MyProfilePreview() {
    FanwooriTheme {
        MyProfile({

        })
    }
}