package com.ricky.eanimator

import android.graphics.Color
import java.util.*

/**
 *
 * @author Ricky Hal
 * @date 2021/12/13
 */

internal fun Int.toHex(alpha: Int = 255, isUpperCase: Boolean = true): String {
    val sb = StringBuffer()
    var r: String = Integer.toHexString(Color.red(this))
    var g: String = Integer.toHexString(Color.green(this))
    var b: String = Integer.toHexString(Color.blue(this))
    var a: String = Integer.toHexString(alpha)
    r = if (r.length == 1) "0$r" else r
    g = if (g.length == 1) "0$g" else g
    b = if (b.length == 1) "0$b" else b
    a = if (a.length == 1) "0$a" else a
    sb.append("#")
    if (isUpperCase) {
        sb.append(a.uppercase(Locale.ROOT))
        sb.append(r.uppercase(Locale.ROOT))
        sb.append(g.uppercase(Locale.ROOT))
        sb.append(b.uppercase(Locale.ROOT))
    } else {
        sb.append(a)
        sb.append(r)
        sb.append(g)
        sb.append(b)
    }
    return sb.toString()
}

internal fun getCurrentColor(fraction: Float, startColor: Int, endColor: Int): Int {
    val redCurrent: Int
    val blueCurrent: Int
    val greenCurrent: Int
    val alphaCurrent: Int
    val redStart = Color.red(startColor)
    val blueStart = Color.blue(startColor)
    val greenStart = Color.green(startColor)
    val alphaStart = Color.alpha(startColor)
    val redEnd = Color.red(endColor)
    val blueEnd = Color.blue(endColor)
    val greenEnd = Color.green(endColor)
    val alphaEnd = Color.alpha(endColor)
    val redDifference = redEnd - redStart
    val blueDifference = blueEnd - blueStart
    val greenDifference = greenEnd - greenStart
    val alphaDifference = alphaEnd - alphaStart
    redCurrent = (redStart + fraction * redDifference).toInt()
    blueCurrent = (blueStart + fraction * blueDifference).toInt()
    greenCurrent = (greenStart + fraction * greenDifference).toInt()
    alphaCurrent = (alphaStart + fraction * alphaDifference).toInt()
    return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent)
}
