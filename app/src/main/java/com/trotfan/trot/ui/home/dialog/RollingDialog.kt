package com.trotfan.trot.ui.home.dialog

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.trotfan.trot.datastore.dateManager
import com.trotfan.trot.model.Layer
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.utils.clickable
import io.ktor.util.date.*
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalPagerApi::class)
@Composable
fun RollingDialog(
    layers: List<Layer>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        val rollingLayer by remember { mutableStateOf(layers) }
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        val state = rememberPagerState()

        LaunchedEffect(state) {
            state.scrollToPage(layers.size * 5)
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                HorizontalPager(
                    count = layers.size * 10,
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(468.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) { page ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(rollingLayer[page % rollingLayer.size].image)
                                .crossfade(true).build(),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth(),
                            contentDescription = null
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp)
                ) {
                    DotsIndicator(
                        totalDots = layers.size,
                        selectedIndex = state.currentPage % layers.size,
                        selectedColor = Color.White,
                        unSelectedColor = Color.LightGray,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = CenterVertically
            ) {
                Box(
                    modifier = modifier
                        .weight(1f)
                        .height(48.dp)
                        .clickable {
                            coroutineScope.launch {
                                setDate(context = context)
                            }
                            onDismiss()
                        }
                ) {
                    Text(
                        text = "오늘 하루 보지 않기",
                        color = Color.White,
                        style = FanwooriTypography.button2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Center)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(12.dp)
                        .background(Color.White)
                        .align(CenterVertically)
                )
                Box(modifier = modifier
                    .weight(1f)
                    .height(48.dp)
                    .clickable { onDismiss() }) {
                    Text(
                        text = "닫기",
                        color = Color.White,
                        style = FanwooriTypography.button2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Center)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun setDate(context: Context) {
    context.dateManager.updateData {
        val date = LocalDate.now().toString()
        it.toBuilder().setRollingDate(date).build()
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
            }
        }
    }
}