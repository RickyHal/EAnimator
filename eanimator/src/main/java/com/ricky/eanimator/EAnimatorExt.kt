@file:Suppress("unused")

package com.ricky.eanimator

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.drawable.ColorDrawable
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.FloatRange
import androidx.core.view.*


/**
 * 可用动画
 * @author Ricky Hal
 * @date 2021/12/13
 */
private val WIDTH = object : Property<View, Float>(Float::class.java, "width") {
    override fun set(view: View, value: Float) {
        view.layoutParams.width = value.toInt()
        view.requestLayout()
    }

    override fun get(view: View): Float {
        return view.layoutParams.width.toFloat()
    }
}
private val HEIGHT = object : Property<View, Float>(Float::class.java, "height") {
    override fun get(view: View): Float {
        return view.layoutParams.height.toFloat()
    }

    override fun set(view: View, value: Float) {
        view.layoutParams.height = value.toInt()
        view.requestLayout()
    }
}
private val MARGIN_LEFT = object : Property<View, Float>(Float::class.java, "margin_left") {
    override fun get(view: View): Float {
        return view.marginLeft.toFloat()
    }

    override fun set(view: View, value: Float) {
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            leftMargin = value.toInt()
        }
        view.requestLayout()
    }
}
private val MARGIN_TOP = object : Property<View, Float>(Float::class.java, "margin_top") {
    override fun get(view: View): Float {
        return view.marginTop.toFloat()
    }

    override fun set(view: View, value: Float) {
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = value.toInt()
        }
        view.requestLayout()
    }
}

private val MARGIN_RIGHT = object : Property<View, Float>(Float::class.java, "margin_right") {
    override fun get(view: View): Float {
        return view.marginRight.toFloat()
    }

    override fun set(view: View, value: Float) {
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            rightMargin = value.toInt()
        }
        view.requestLayout()
    }
}

private val MARGIN_BOTTOM = object : Property<View, Float>(Float::class.java, "margin_bottom") {
    override fun get(view: View): Float {
        return view.marginBottom.toFloat()
    }

    override fun set(view: View, value: Float) {
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = value.toInt()
        }
        view.requestLayout()
    }
}
private val BACKGROUND_COLOR = object : Property<View, Int>(Int::class.java, "background_color") {

    override fun get(view: View): Int {
        val background = view.background
        return if (background is ColorDrawable) {
            background.color
        } else {
            Color.TRANSPARENT
        }
    }

    override fun set(view: View, value: Int) {
        view.setBackgroundColor(value)
    }
}
private val TEXT_COLOR = object : Property<View, Int>(Int::class.java, "text_color") {

    override fun get(view: View): Int {
        return if (view is TextView) {
            view.currentTextColor
        } else {
            Color.TRANSPARENT
        }
    }

    override fun set(view: View, value: Int) {
        if (view is TextView) {
            view.setTextColor(value)
        }
    }
}
private val COLOR_EVALUATOR = TypeEvaluator<Int> { fraction, startValue, endValue -> getCurrentColor(fraction, startValue, endValue) }

fun EAnimator.pivotX(@FloatRange(from = 0.0, to = 1.0) pivotX: Float) {
    views.forEach { view ->
        view.pivotX = view.width * pivotX
    }
}

fun EAnimator.pivotY(@FloatRange(from = 0.0, to = 1.0) pivotY: Float) {
    views.forEach { view ->
        view.pivotY = view.height * pivotY
    }
}

fun EAnimator.pivot(@FloatRange(from = 0.0, to = 1.0) pivotX: Float, @FloatRange(from = 0.0, to = 1.0) pivotY: Float) {
    views.forEach { view ->
        view.pivotX = view.width * pivotX
        view.pivotY = view.height * pivotY
    }
}

fun EAnimator.scaleX(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, *values).applyConfig(this, config)
    listOf(scaleX)
}

fun EAnimator.scaleXTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, view.scaleX, to).applyConfig(this, config)
    listOf(scaleX)
}

fun EAnimator.scaleY(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, *values).applyConfig(this, config)
    listOf(scaleY)
}

fun EAnimator.scaleYTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, view.scaleY, to).applyConfig(this, config)
    listOf(scaleY)
}

fun EAnimator.scale(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, *values).applyConfig(this, config)
    val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, *values).applyConfig(this, config)
    listOf(scaleX, scaleY)
}

fun EAnimator.scaleTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, view.scaleX, to).applyConfig(this, config)
    val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, view.scaleY, to).applyConfig(this, config)
    listOf(scaleX, scaleY)
}

fun EAnimator.translationX(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val translationX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, *values).applyConfig(this, config)
    listOf(translationX)
}

fun EAnimator.translationXTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val translationX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, view.translationX, to).applyConfig(this, config)
    listOf(translationX)
}

fun EAnimator.translationY(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val translationY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, *values).applyConfig(this, config)
    listOf(translationY)
}

fun EAnimator.translationYTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val translationY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.translationY, to).applyConfig(this, config)
    listOf(translationY)
}

fun EAnimator.translationZ(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val translationZ = ObjectAnimator.ofFloat(view, View.TRANSLATION_Z, *values).applyConfig(this, config)
    listOf(translationZ)
}

fun EAnimator.translationZTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val translationZ = ObjectAnimator.ofFloat(view, View.TRANSLATION_Z, view.translationZ, to).applyConfig(this, config)
    listOf(translationZ)
}

fun EAnimator.rotationX(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val rotationX = ObjectAnimator.ofFloat(view, View.ROTATION_X, *values).applyConfig(this, config)
    listOf(rotationX)
}

fun EAnimator.rotationXTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val rotationX = ObjectAnimator.ofFloat(view, View.ROTATION_X, view.rotationX, to).applyConfig(this, config)
    listOf(rotationX)
}

fun EAnimator.rotationY(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val rotationY = ObjectAnimator.ofFloat(view, View.ROTATION_Y, *values).applyConfig(this, config)
    listOf(rotationY)
}

fun EAnimator.rotationYTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val rotationY = ObjectAnimator.ofFloat(view, View.ROTATION_Y, view.rotationY, to).applyConfig(this, config)
    listOf(rotationY)
}

fun EAnimator.rotation(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val rotation = ObjectAnimator.ofFloat(view, View.ROTATION, *values).applyConfig(this, config)
    listOf(rotation)
}

fun EAnimator.rotationTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val rotation = ObjectAnimator.ofFloat(view, View.ROTATION, view.rotation, to).applyConfig(this, config)
    listOf(rotation)
}

fun EAnimator.width(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val width = ObjectAnimator.ofFloat(view, WIDTH, *values).applyConfig(this, config)
    listOf(width)
}

fun EAnimator.widthTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val width = ObjectAnimator.ofFloat(view, WIDTH, view.width.toFloat(), to).applyConfig(this, config)
    listOf(width)
}

fun EAnimator.height(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val height = ObjectAnimator.ofFloat(view, HEIGHT, *values).applyConfig(this, config)
    listOf(height)
}

fun EAnimator.heightTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val height = ObjectAnimator.ofFloat(view, HEIGHT, view.height.toFloat(), to).applyConfig(this, config)
    listOf(height)
}

fun EAnimator.marginLeft(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val marginLeft = ObjectAnimator.ofFloat(view, MARGIN_LEFT, *values).applyConfig(this, config)
    listOf(marginLeft)
}

fun EAnimator.marginLeftTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val marginLeft = ObjectAnimator.ofFloat(view, MARGIN_LEFT, view.marginLeft.toFloat(), to).applyConfig(this, config)
    listOf(marginLeft)
}

fun EAnimator.marginTop(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val marginTop = ObjectAnimator.ofFloat(view, MARGIN_TOP, *values).applyConfig(this, config)
    listOf(marginTop)
}

fun EAnimator.marginTopTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val marginTop = ObjectAnimator.ofFloat(view, MARGIN_TOP, view.marginTop.toFloat(), to).applyConfig(this, config)
    listOf(marginTop)
}

fun EAnimator.marginRight(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val marginRight = ObjectAnimator.ofFloat(view, MARGIN_RIGHT, *values).applyConfig(this, config)
    listOf(marginRight)
}

fun EAnimator.marginRightTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val marginRight = ObjectAnimator.ofFloat(view, MARGIN_RIGHT, view.marginRight.toFloat(), to).applyConfig(this, config)
    listOf(marginRight)
}

fun EAnimator.marginBottom(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val marginBottom = ObjectAnimator.ofFloat(view, MARGIN_BOTTOM, *values).applyConfig(this, config)
    listOf(marginBottom)
}

fun EAnimator.marginBottomTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val marginBottom = ObjectAnimator.ofFloat(view, MARGIN_BOTTOM, view.marginBottom.toFloat(), to).applyConfig(this, config)
    listOf(marginBottom)
}

fun EAnimator.alpha(vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, *values).applyConfig(this, config)
    listOf(alpha)
}

fun EAnimator.alphaTo(to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, view.alpha, to).applyConfig(this, config)
    listOf(alpha)
}

fun EAnimator.backgroundColor(vararg values: Int, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val custom = ObjectAnimator.ofObject(view, BACKGROUND_COLOR, COLOR_EVALUATOR, *values.toTypedArray()).applyConfig(this, config)
    listOf(custom)
}

fun EAnimator.backgroundColor(vararg values: String, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val intColors = values.map { Color.parseColor(it) }.toTypedArray()
    val custom = ObjectAnimator.ofObject(view, BACKGROUND_COLOR, COLOR_EVALUATOR, *intColors).applyConfig(this, config)
    listOf(custom)
}

fun EAnimator.backgroundColorTo(to: Int, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val custom = ObjectAnimator.ofObject(view, BACKGROUND_COLOR, COLOR_EVALUATOR, BACKGROUND_COLOR.get(view), to).applyConfig(this, config)
    listOf(custom)
}

fun EAnimator.backgroundColorTo(to: String, configDsl: EAnimatorConfig.() -> Unit = {}) = backgroundColorTo(Color.parseColor(to), configDsl)

fun EAnimator.textColor(vararg values: Int, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val textColor = ObjectAnimator.ofObject(view, TEXT_COLOR, COLOR_EVALUATOR, *values.toTypedArray()).applyConfig(this, config)
    listOf(textColor)
}

fun EAnimator.textColor(vararg values: String, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val intColors = values.map { Color.parseColor(it) }.toTypedArray()
    val textColor = ObjectAnimator.ofObject(view, TEXT_COLOR, COLOR_EVALUATOR, *intColors).applyConfig(this, config)
    listOf(textColor)
}

fun EAnimator.textColorTo(to: Int, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val textColor = ObjectAnimator.ofObject(view, TEXT_COLOR, COLOR_EVALUATOR, TEXT_COLOR.get(view), to).applyConfig(this, config)
    listOf(textColor)
}

fun EAnimator.textColorTo(to: String, configDsl: EAnimatorConfig.() -> Unit = {}) = textColorTo(Color.parseColor(to), configDsl)


fun EAnimator.custom(property: Property<View, Float>, vararg values: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val custom = ObjectAnimator.ofFloat(view, property, *values).applyConfig(this, config)
    listOf(custom)
}

fun EAnimator.customTo(property: Property<View, Float>, to: Float, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val custom = ObjectAnimator.ofFloat(view, property, property.get(view), to).applyConfig(this, config)
    listOf(custom)
}

fun EAnimator.path(path: Path, configDsl: EAnimatorConfig.() -> Unit = {}) = defineAnimation(configDsl) { view, config ->
    val pathMeasure = PathMeasure(path, false)
    val pos = FloatArray(2)
    val pathAnimator = ValueAnimator.ofFloat(0f, pathMeasure.length).applyConfig(this, config)
    pathAnimator.addUpdateListener {
        val current = it.animatedValue as Float
        pathMeasure.getPosTan(current, pos, null)
        view.x = pos[0]
        view.y = pos[1]
    }
    listOf(pathAnimator)
}
