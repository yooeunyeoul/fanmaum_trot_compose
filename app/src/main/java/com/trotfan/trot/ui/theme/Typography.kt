package com.trotfan.trot.ui.theme

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
class Typography internal constructor(
    val h1: TextStyle,
    val h2: TextStyle,
    val subtitle1: TextStyle,
    val subtitle2: TextStyle,
    val subtitle3: TextStyle,
    val subtitle4: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val body4: TextStyle,
    val body5: TextStyle,
    val button1: TextStyle,
    val button2: TextStyle,
    val caption1: TextStyle,
    val caption2: TextStyle
) {
    constructor(
        defaultFontFamily: FontFamily = FontFamily.Default,
        h1: TextStyle = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            letterSpacing = (-1.5).sp
        ),
        h2: TextStyle = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 60.sp,
            letterSpacing = (-0.5).sp
        ),
        subtitle1: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.15.sp
        ),
        subtitle2: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 0.1.sp
        ),
        subtitle3: TextStyle = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            letterSpacing = (-0.25).sp
        ),
        subtitle4: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            letterSpacing = (-0.25).sp
        ),
        body1: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        ),
        body2: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp
        ),
        body3: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        ),
        body4: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp
        ),
        body5: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            letterSpacing = (-0.25).sp
        ),
        button1: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 1.25.sp
        ),
        button2: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 1.25.sp
        ),
        caption1: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 0.4.sp
        ),
        caption2: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 0.4.sp
        )
    ) : this(
        h1 = h1.withDefaultFontFamily(defaultFontFamily),
        h2 = h2.withDefaultFontFamily(defaultFontFamily),
        subtitle1 = subtitle1.withDefaultFontFamily(defaultFontFamily),
        subtitle2 = subtitle2.withDefaultFontFamily(defaultFontFamily),
        subtitle3 = subtitle3.withDefaultFontFamily(defaultFontFamily),
        subtitle4 = subtitle4.withDefaultFontFamily(defaultFontFamily),
        body1 = body1.withDefaultFontFamily(defaultFontFamily),
        body2 = body2.withDefaultFontFamily(defaultFontFamily),
        body3 = body3.withDefaultFontFamily(defaultFontFamily),
        body4 = body4.withDefaultFontFamily(defaultFontFamily),
        body5 = body5.withDefaultFontFamily(defaultFontFamily),
        button1 = button1.withDefaultFontFamily(defaultFontFamily),
        button2 = button2.withDefaultFontFamily(defaultFontFamily),
        caption1 = caption1.withDefaultFontFamily(defaultFontFamily),
        caption2 = caption2.withDefaultFontFamily(defaultFontFamily)
    )

    /**
     * Returns a copy of this Typography, optionally overriding some of the values.
     */
    fun copy(
        h1: TextStyle = this.h1,
        h2: TextStyle = this.h2,
        subtitle1: TextStyle = this.subtitle1,
        subtitle2: TextStyle = this.subtitle2,
        subtitle3: TextStyle = this.subtitle3,
        body1: TextStyle = this.body1,
        body2: TextStyle = this.body2,
        body3: TextStyle = this.body3,
        body4: TextStyle = this.body4,
        body5: TextStyle = this.body5,
        button1: TextStyle = this.button1,
        button2: TextStyle = this.button2,
        caption1: TextStyle = this.caption1,
        caption2: TextStyle = this.caption2
    ): Typography = Typography(
        h1 = h1,
        h2 = h2,
        subtitle1 = subtitle1,
        subtitle2 = subtitle2,
        subtitle3 = subtitle3,
        body1 = body1,
        body2 = body2,
        body3 = body3,
        body4 = body4,
        body5 = body5,
        button1 = button1,
        button2 = button2,
        caption1 = caption1,
        caption2 = caption2
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Typography) return false

        if (h1 != other.h1) return false
        if (h2 != other.h2) return false
        if (subtitle1 != other.subtitle1) return false
        if (subtitle2 != other.subtitle2) return false
        if (subtitle3 != other.subtitle3) return false
        if (body1 != other.body1) return false
        if (body2 != other.body2) return false
        if (body3 != other.body3) return false
        if (body4 != other.body4) return false
        if (body5 != other.body5) return false
        if (button1 != other.button1) return false
        if (button2 != other.button2) return false
        if (caption1 != other.caption1) return false
        if (caption2 != other.caption2) return false

        return true
    }

    override fun hashCode(): Int {
        var result = h1.hashCode()
        result = 31 * result + h2.hashCode()
        result = 31 * result + subtitle1.hashCode()
        result = 31 * result + subtitle2.hashCode()
        result = 31 * result + subtitle3.hashCode()
        result = 31 * result + body1.hashCode()
        result = 31 * result + body2.hashCode()
        result = 31 * result + body3.hashCode()
        result = 31 * result + body4.hashCode()
        result = 31 * result + body5.hashCode()
        result = 31 * result + button1.hashCode()
        result = 31 * result + button2.hashCode()
        result = 31 * result + caption1.hashCode()
        result = 31 * result + caption2.hashCode()
        return result
    }

    override fun toString(): String {
        return "Typography(h1=$h1, h2=$h2, " +
                "subtitle1=$subtitle1, subtitle2=$subtitle2, subtitle3=$subtitle3, body1=$body1, " +
                "body2=$body2, body3=$body3, body4=$body4, body5=$body5, button1=$button1, button2=$button2, caption2=$caption2, caption1=$caption1)"
    }
}

/**
 * @return [this] if there is a [FontFamily] defined, otherwise copies [this] with [default] as
 * the [FontFamily].
 */
private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

/**
 * This CompositionLocal holds on to the current definition of typography for this application as
 * described by the Material spec. You can read the values in it when creating custom components
 * that want to use Material types, as well as override the values when you want to re-style a
 * part of your hierarchy. Material components related to text such as [Button] will use this
 * CompositionLocal to set values with which to style children text components.
 *
 * To access values within this CompositionLocal, use [MaterialTheme.typography].
 */
internal val LocalTypography = staticCompositionLocalOf { Typography() }
