package com.trotfan.trot.ui.home.vote.guide

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FullscreenDialog(
    modifier: Modifier = Modifier,
    title: String,
    images: List<Int>,
    onDismiss: () -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var loadingState by remember {
        mutableStateOf(false)
    }


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
                    .padding(innerPadding),
                state = listState
            ) {
                items(images.size) { index ->
                    Column {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(images[index])
                                .crossfade(true).build(),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth(),
                            contentDescription = null,
                            onSuccess = {
                                if (index == 0 && loadingState.not()) {
                                    coroutineScope.launch {
                                        listState.scrollToItem(0)
                                        loadingState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}