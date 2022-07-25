package com.trotfan.trot.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.R


// Set of Material typography styles to start with
val pretendard = FontFamily(
    Font(R.font.pretendard_black, FontWeight.Black),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extra_bold, FontWeight.ExtraBold),
    Font(R.font.pretendard_extra_light, FontWeight.ExtraLight),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold)
)


@Preview
@Composable
fun PreviewKotlinWolrdFont() {
    KotlinWorldFont(itemClicked = {})
}

@Composable
fun KotlinWorldFont(
    itemClicked: (TextUnit) -> Unit,
    fontSize: TextUnit = 20.sp
) {
    val fontSizeState = remember {
        mutableStateOf(fontSize)
    }
    LazyColumn(
        Modifier
            .padding(
                start = 24.dp,
                end = 24.dp
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Spacer(modifier = Modifier.height(37.dp))
            Text(
                color = Color(0xFF57615B),
                text = "내 스타\n1명을 선택해주세요",
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                color = Color(0xFF57615B),
                text = "내 스타 선택은 최초 1회만 가능해요.",
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(23.dp))
            Text(
                color = Color(0xFF57615B),
                text = "로그인하고 팬우리 서비스를\n" +
                        "자유롭게 이용해보세요. 폰트15",
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                color = Color(0xFF57615B),
                text = "로그인하고 팬우리 서비스를\n" +
                        "자유롭게 이용해보세요. 폰트16",
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                color = Color(0xFF57615B),
                text = "로그인하고 팬우리 서비스를\n" +
                        "자유롭게 이용해보세요. 폰트17",
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                color = Color(0xFF57615B),
                text = "로그인하고 팬우리 서비스를\n" +
                        "자유롭게 이용해보세요. 폰트18",
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(44.dp))
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .fillParentMaxWidth(),
                onClick = { fontSizeState.value = 30.sp }) {
                Text(
                    text = "폰트사이즈 15",
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .fillParentMaxWidth(),
                onClick = { fontSizeState.value = 30.sp }) {
                Text(
                    text = "폰트사이즈 16",
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .fillParentMaxWidth(),
                onClick = { fontSizeState.value = 30.sp }) {
                Text(
                    text = "폰트사이즈 17",
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .fillParentMaxWidth(),
                onClick = { fontSizeState.value = 30.sp }) {
                Text(
                    text = "폰트사이즈 18",
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),
                backgroundColor = Color(0xFFF3F6F4),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        color = Color(0xFF373E3B),
                        text = "폰트사이즈 17",
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                }

            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                color = Color(0xFF57615B),
                text = "내 스타 선택은 최초 1회만 가능해요.",
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(60.dp))

        }


    }
}


