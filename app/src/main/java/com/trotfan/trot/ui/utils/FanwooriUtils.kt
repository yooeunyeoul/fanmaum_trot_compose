package com.trotfan.trot.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.trotfan.trot.BaseApplication
import com.trotfan.trot.datastore.*
import com.trotfan.trot.ui.home.vote.voteTopShareText
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


inline fun Modifier.clickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = rememberRipple(bounded = true),
        interactionSource = MutableInteractionSource()
    ) {
        onClick()
    }
}

fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    hideRipple: Boolean = false,
    onClick: () -> Unit,
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication = if (hideRipple) null else LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }
    )
}

fun addDynamicLink(
    titleText: String,
    uri: String,
    descriptionText: String,
    onSuccess: (ShortDynamicLink) -> Unit
) {
    Firebase.dynamicLinks.shortLinkAsync {
        link = Uri.parse(uri)
        domainUriPrefix = "https://fanmaum.page.link"
        androidParameters("com.trotfan.trot") {}
        iosParameters("com.trotfan.trot") {}
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
    alpha: Float = 0.20f,
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

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onPress = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}

private val HorizontalScrollConsumer = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(y = 0f)
    override suspend fun onPreFling(available: Velocity) = available.copy(y = 0f)
}

private val VerticalScrollConsumer = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(x = 0f)
    override suspend fun onPreFling(available: Velocity) = available.copy(x = 0f)
}

fun Modifier.disabledHorizontalPointerInputScroll(disabled: Boolean = true) =
    if (disabled) this.nestedScroll(HorizontalScrollConsumer) else this

fun Modifier.disabledVerticalPointerInputScroll(disabled: Boolean = true) =
    if (disabled) this.nestedScroll(VerticalScrollConsumer) else this

fun getTime(
    targetMonth: Int? = null,
    targetDay: Int? = null,
    targetSecond: Int? = null,
    targetHour: Int = 23,
    targetMinute: Int = 30,
    application: BaseApplication

): Int {
    val cal = Calendar.getInstance()
    targetMonth?.let {
        cal.set(Calendar.MONTH, targetMonth)
    }
    targetDay?.let {
        cal.set(Calendar.DAY_OF_MONTH, targetDay)
    }
    targetSecond?.let {
        cal.set(Calendar.SECOND, targetSecond)
    }
    cal.set(Calendar.HOUR_OF_DAY, targetHour)
    cal.set(Calendar.MINUTE, targetMinute)
    val diff = abs(
        cal.timeInMillis.minus(
            application.kronosClock.getCurrentNtpTimeMs() ?: 0
        )
    ) / 1000;
    return diff.toInt()
}

fun getTime(
    targetMilliSecond: Long?,
    application: BaseApplication
): Int {
    val diff = abs(
        (targetMilliSecond
            ?: 0).minus(application.kronosClock.getCurrentNtpTimeMs() ?: 0) / 1000
    )
    return diff.toInt()
}

fun convertStringToTime(date: String): Long {
    return if (date.isNotEmpty()) {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatter.parse(date)?.time ?: 0
    } else {
        0
    }
}

fun Modifier.textBrush(brush: Brush) = this
    .graphicsLayer(alpha = 0.99f)
    .drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(brush, blendMode = BlendMode.SrcAtop)
        }
    }

val CHO = listOf(
    "ㄱ",
    "ㄲ",
    "ㄴ",
    "ㄷ",
    "ㄸ",
    "ㄹ",
    "ㅁ",
    "ㅂ",
    "ㅃ",
    "ㅅ",
    "ㅆ",
    "ㅇ",
    "ㅈ",
    "ㅉ",
    "ㅊ",
    "ㅋ",
    "ㅌ",
    "ㅍ",
    "ㅎ"
)

fun getShareChar(char: Char): String {
    return try {
        val uniVal: Int = char.code - 0xAC00
        val cho = ((uniVal - (uniVal % 28)) / 28) / 21
        CHO[cho]
    } catch (e: ArrayIndexOutOfBoundsException) {
        e.printStackTrace()
        ""
    }
}

fun clearDataStore(context: Context, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        context.run {
            AppVersionManager.edit { it.clear() }
            RankMainDataStore.edit { it.clear() }
            UserInfoDataStore.edit { it.clear() }
            VoteMainDataStore.edit { it.clear() }
            dateManager.updateData { it.toBuilder().clear().build() }
            userIdStore.updateData { it.toBuilder().clear().build() }
            userTokenStore.updateData { it.toBuilder().clear().build() }
        }
    }
}

internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= 1000L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}

object NumberComma {
    val decimalFormat = DecimalFormat("#,###")
}

@Composable
fun getActivity() = LocalContext.current as ComponentActivity

@Composable
inline fun <reified VM : ViewModel> composableActivityViewModel(
    key: String? = null,
    factory: ViewModelProvider.Factory? = null
): VM = viewModel(
    VM::class.java,
    getActivity(),
    key,
    factory
)

@Composable
fun Lifecycle.observeAsState(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsState.addObserver(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }
    return state
}