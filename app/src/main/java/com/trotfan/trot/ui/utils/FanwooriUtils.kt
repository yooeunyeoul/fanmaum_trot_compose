package com.trotfan.trot.ui.utils

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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