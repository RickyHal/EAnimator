@file:Suppress("unused")

package com.ricky.eanimator

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

/**
 * 单一动画的配置
 * @author Ricky Hal
 * @date 2021/12/9
 */


class EAnimatorConfig {
    companion object {
        internal val DEFAULT_INTERPOLATOR = LinearInterpolator()
        internal const val DEFAULT_DURATION = 500L
        internal const val DEFAULT_REPEAT_COUNT = 0
    }

    internal var duration: Long = DEFAULT_DURATION
    internal var interpolator: Interpolator = DEFAULT_INTERPOLATOR
    internal var repeatCount: Int = DEFAULT_REPEAT_COUNT
    internal var repeatMode: Int = ValueAnimator.RESTART
    internal var hasSetDuration: Boolean = false
    internal var hasSetInterpolator: Boolean = false

    internal var onStartCallback: () -> Unit = {}
    internal var onCancelCallback: () -> Unit = {}
    internal var onEndCallback: () -> Unit = {}
    internal var onRepeatCallback: () -> Unit = {}

    fun withDuration(duration: Long) {
        this.duration = duration
        hasSetDuration = true
    }

    fun withInterpolator(interpolator: Interpolator) {
        this.interpolator = interpolator
        hasSetInterpolator = true
    }

    fun withRepeatCount(count: Int) {
        this.repeatCount = count
    }

    fun withRepeatMode(mode: Int) {
        this.repeatMode = mode
    }

    fun onStart(block: () -> Unit) {
        onStartCallback = block
    }

    fun onCancel(block: () -> Unit) {
        onCancelCallback = block
    }

    fun onEnd(block: () -> Unit) {
        onEndCallback = block
    }

    fun onRepeat(block: () -> Unit) {
        onRepeatCallback = block
    }
}

fun ValueAnimator.applyConfig(animator: EAnimator, config: EAnimatorConfig) = apply {
    duration = if (config.hasSetDuration) {
        config.duration
    } else {
        if (animator.globalDuration != null) {
            animator.globalDuration!! / animator.animators.size
        } else {
            config.duration
        }
    }
    interpolator = if (config.hasSetInterpolator) {
        config.interpolator
    } else {
        animator.globalInterpolator ?: config.interpolator
    }
    repeatCount = config.repeatCount
    repeatMode = config.repeatMode
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
            config.onStartCallback.invoke()
        }

        override fun onAnimationEnd(animation: Animator?) {
            config.onEndCallback.invoke()
        }

        override fun onAnimationCancel(animation: Animator?) {
            config.onCancelCallback.invoke()
        }

        override fun onAnimationRepeat(animation: Animator?) {
            config.onRepeatCallback.invoke()
        }
    })
}