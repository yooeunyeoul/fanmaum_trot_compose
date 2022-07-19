package com.trotfan.trot.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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



@Preview(showBackground = true, heightDp = 500)
@Composable
fun KotlinWorldFont() {
    LazyColumn(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "푸리탠다드_불랙", fontFamily = pretendard, fontWeight = FontWeight.Black)
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "푸리탠다드_벌드", fontFamily = pretendard, fontWeight = FontWeight.Bold)
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "푸리탠다드 엑스트라_벌드", fontFamily = pretendard, fontWeight = FontWeight.ExtraBold)
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "푸리탠다드_액스트라_라이트", fontFamily = pretendard, fontWeight = FontWeight.ExtraLight)
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "푸리탠다드_라이트", fontFamily = pretendard, fontWeight = FontWeight.Light)
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "푸리탠다드_미디엄", fontFamily = pretendard, fontWeight = FontWeight.Normal)
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "푸리탠다드_레귤라", fontFamily = pretendard, fontWeight = FontWeight.SemiBold)
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "퓨리탠다드_쎄미_벌드", fontFamily = pretendard, fontWeight = FontWeight.Thin)
            }
        }











    }
}


