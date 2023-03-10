package com.trotfan.trot.ui.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnOutlineLPrimary
import com.trotfan.trot.ui.components.button.BtnFilledMBlackIcon
import com.trotfan.trot.ui.components.button.BtnFilledLPrimary
import com.trotfan.trot.ui.theme.*

@OptIn(ExperimentalTextApi::class)
@Composable
fun VerticalDialogReceiveGift(
    modifier: Modifier = Modifier,
    contentText: String,
    gradientText: String,
    buttonOneText: String,
    buttonTwoText: String? = null,
    buttonThreeText: String? = null,
    icon: Int? = R.drawable.ic_vote_iconcolored,
    onDismiss: () -> Unit
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.popup_confetti02))

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(),
    ) {

        Box(
            modifier = Modifier.size(650.dp)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(com.fanmaum.roullete.R.drawable.charge_rewardpopup)
                    .crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 280.dp)
                    .width(200.dp)
                    .height(144.dp)
                    .zIndex(2f)
                    .align(Alignment.Center)


            )


            Surface(
                modifier = modifier
                    .width(328.dp)
                    .height(204.dp)
                    .zIndex(1f)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(24.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 12.dp,
                        top = 32.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = contentText,
                        textAlign = TextAlign.Center,
                        style = FanwooriTypography.subtitle1,
                        color = Gray800
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        icon?.let {
                            Image(painter = painterResource(id = icon), contentDescription = null)
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = gradientText,
                            style = TextStyle(
                                brush = gradient04, fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                letterSpacing = (-0.25).sp
                            )
                        )

                    }

                    BtnFilledLPrimary(
                        text = buttonOneText,
                        modifier = Modifier.padding(
                            top = if (buttonTwoText.isNullOrEmpty()) 24.dp else 16.dp,
                            bottom = if (buttonTwoText.isNullOrEmpty()) 20.dp else 0.dp
                        )
                    ) {
                        onDismiss()
                    }

                    buttonThreeText?.let {
                        BtnOutlineLPrimary(text = it, modifier = Modifier.padding(top = 8.dp)) {

                        }
                    }

                    buttonTwoText?.let {
                        BtnFilledMBlackIcon(
                            text = it,
                            modifier = Modifier.padding(
                                top = if (buttonThreeText.isNullOrEmpty()) 16.dp else 8.dp
                            )
                        ) {
                            onDismiss()
                        }
                    }
                }
            }

            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(0f)
                    .size(360.dp)

            )
        }


    }
}
