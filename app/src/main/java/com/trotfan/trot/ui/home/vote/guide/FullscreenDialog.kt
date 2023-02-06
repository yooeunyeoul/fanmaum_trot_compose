package com.trotfan.trot.ui.home.vote.guide

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
fun FullscreenDialog(
    modifier: Modifier = Modifier,
    title: String,
    image: Int,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = title, icon = R.drawable.icon_close
                ) { onDismiss() }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
            ) {
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image)
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