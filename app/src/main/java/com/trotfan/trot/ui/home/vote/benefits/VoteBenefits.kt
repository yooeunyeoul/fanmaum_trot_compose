package com.trotfan.trot.ui.home.vote.benefits

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.navigation.CustomTopAppBar
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray200
import com.trotfan.trot.ui.theme.Gray700
import com.trotfan.trot.ui.theme.Gray750

@Composable
fun VoteBenefits(
    modifier: Modifier,
    navController: NavController
) {

    var state by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CustomTopAppBar(title = "우승혜택", icon = R.drawable.icon_back) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            RankButton(count = 3, state) {
                state = it
            }
        }
    }
}

@Composable
fun RankButton(
    count: Int,
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        LazyRow(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(start = 16.dp)
        ) {

            items(count) { index ->

                if (index == selectedIndex) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Gray750)
                            .width(64.dp)
                            .height(40.dp)
                            .clickable {
                                onClick(index)
                            }
                    ) {
                        Text(
                            text = "${index + 1}위",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            style = FanwooriTypography.button1,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Gray200, RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .width(64.dp)
                            .height(40.dp)
                            .clickable {
                                onClick(index)
                            }
                    ) {
                        Text(
                            text = "${index + 1}위",
                            color = Gray700,
                            textAlign = TextAlign.Center,
                            style = FanwooriTypography.button1,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }


                if (index != count - 1) {
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdnetphoto.appphotocard.com/vote_thumbnail/183/FIRST_PRIZE_IMG_ko_KR_20221005153408.png")
                .crossfade(true).build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun VoteBenefitsPreview() {
    VoteBenefits(modifier = Modifier, navController = rememberNavController())
}