package com.trotfan.trot.ui.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trotfan.trot.InAppProduct
import com.trotfan.trot.R
import com.trotfan.trot.ui.components.button.BtnFilledMPrimary
import com.trotfan.trot.ui.theme.*

@Composable
fun StoreItem(onItemClick: (InAppProduct) -> Unit, product: InAppProduct) {
    val strokeWidth = LocalDensity.current.run { 1.dp.toPx() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .drawBehind {

                val y = size.height
                drawLine(
                    color = Gray100,
                    start = Offset(0f, y),
                    end = Offset(size.width , y),
                    strokeWidth = strokeWidth
                )
            }

    ) {

        Box(
            modifier = Modifier
                .size(64.dp)
                .align(CenterVertically)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RectangleShape)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = product.productName,
                color = Gray800,
                style = FanwooriTypography.body6
            )

            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier.background(color = Color.White)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = null,
                    tint = Primary500
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_vote_iconcolored),
                    contentDescription = null,
                    tint = Primary500
                )
                Text(
                    text = product.bonus,
                    color = Primary500,
                    style = FanwooriTypography.body4
                )
            }
        }
        
        BtnFilledMPrimary(text = product.price, modifier = Modifier.align(CenterVertically).width(104.dp)) {
            onItemClick.invoke(product)
        }

    }

}