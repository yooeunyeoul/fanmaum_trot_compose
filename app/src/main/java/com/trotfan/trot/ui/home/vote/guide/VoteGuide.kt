package com.trotfan.trot.ui.home.vote.guide

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VoteGuide(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val scrollState = rememberScrollState()
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = "팬마음 투표안내", icon = R.drawable.icon_close
                ) { onDismiss() }
            }
        ) { innerPadding ->
//            Column(
//                modifier = modifier.padding(innerPadding)
//                    .verticalScroll(scrollState)
//            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data("https://image.xportsnews.com/contents/images/upload/article/2022/0313/1647169234362908.jpg")
//                        .crossfade(true).build(),
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxWidth(),
//                    contentDescription = null
//                )
//            }
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
            ) {
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://image.xportsnews.com/contents/images/upload/article/2022/0313/1647169234362908.jpg")
                            .crossfade(true).build(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = null
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/iz/2021/09/10/vCLCFJbUFOdS637668330000252438.jpg")
                            .crossfade(true).build(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = null
                    )
                }
            }
        }
    }
}