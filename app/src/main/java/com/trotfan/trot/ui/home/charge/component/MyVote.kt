package com.trotfan.trot.ui.home.charge.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trotfan.trot.R
import com.trotfan.trot.model.Ticket
import com.trotfan.trot.model.TicketItem
import com.trotfan.trot.ui.components.button.UnderlineTextButton
import com.trotfan.trot.ui.theme.FanwooriTypography
import com.trotfan.trot.ui.theme.Gray100
import com.trotfan.trot.ui.theme.Gray900
import com.trotfan.trot.ui.theme.Primary900
import com.trotfan.trot.ui.utils.NumberComma
import com.trotfan.trot.ui.utils.clickableSingle

@Composable
fun MyVote(
    modifier: Modifier = Modifier,
    tickets: Ticket,
    onclick: () -> Unit = {}
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        modifier
            .fillMaxWidth()
            .background(color = Gray100, shape = RoundedCornerShape(16.dp))
            .padding(start = 24.dp, end = 24.dp, top = 22.dp, bottom = 22.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "내 투표권",
                color = Primary900,
                style = FanwooriTypography.body3,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.width(2.dp))

            Image(
                painter = painterResource(id = R.drawable.icon_vote),
                contentDescription = null,
                modifier = Modifier.weight(1f, fill = true),
                alignment = Alignment.CenterStart
            )

            Text(
                text = NumberComma.decimalFormat.format(
                    tickets.limited.plus(
                        tickets.unlimited ?: 0
                    )
                ),
                color = Gray900,
                style = FanwooriTypography.button1,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

        }

    }
}

@Composable
fun Charging(onclick: () -> Unit) {
    UnderlineTextButton(text = "충전하기", modifier = Modifier.clickableSingle {
        onclick.invoke()
    })

}

@Preview
@Composable
fun PreviewMyVote() {
    MyVote(tickets = Ticket(0, 0), onclick = {})
}