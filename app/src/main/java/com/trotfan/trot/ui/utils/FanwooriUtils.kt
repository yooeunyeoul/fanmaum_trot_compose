package com.trotfan.trot.ui.utils

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase


inline fun Modifier.clickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = rememberRipple(bounded = true),
        interactionSource = MutableInteractionSource()
    ) {
        onClick()
    }
}

fun addDynamicLink(
    titleText: String,
    uri: String,
    descriptionText: String,
    packageName: String,
    onSuccess: (ShortDynamicLink) -> Unit
) {
    Firebase.dynamicLinks.shortLinkAsync {
        link = Uri.parse(uri)
        domainUriPrefix = "https://fanwoori.page.link"
        androidParameters(packageName) {}
        iosParameters("com.trotfan.trotDev") {}
        socialMetaTagParameters {
            title = titleText
            description = descriptionText
        }
    }.addOnSuccessListener {
        onSuccess(it)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.28f,
    borderRadius: Dp = 28.dp,
    shadowRadius: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 4.dp
) = this.drawBehind {
    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
    }
}