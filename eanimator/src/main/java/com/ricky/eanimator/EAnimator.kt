@file:Suppress("unused")

package com.ricky.eanimator

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Interpolator
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnDetach

/**
 *  动画执行器
 * @author Ricky Hal
 * @date 2021/12/13
 */

class EAnimator(internal val views: List<View>) {
    internal val animators: MutableList<AnimatorBuilder> = mutableListOf()
    internal var globalDuration: Long? = null
    internal var globalInterpolator: Interpolator? = null
    private var prevAnimator: EAnimator? = null
    private var nextAnimator: EAnimator? = null

    private var onStartCallback: () -> Unit = {}
    private var onEndCallback: () -> Unit = {}
    private var onCancelCallback: () -> Unit = {}
    private var interruptIndex = mutableSetOf<Int>()
    private var currentAnimatorSet = AnimatorSet()
    private var isActive: Boolean = true

    operator fun AnimatorBuilder.plus(nextAnimators: AnimatorBuilder): AnimatorBuilder {
        val newAnimators: AnimatorBuilder = {
            this.invoke() + nextAnimators.invoke()
        }
        animators.removeLast()
        animators[animators.size - 1] = newAnimators
        return newAnimators
    }

    fun withGlobalDuration(duration: Long) {
        globalDuration = duration
    }

    fun withGlobalInterpolator(interpolator: Interpolator) {
        globalInterpolator = interpolator
    }

    fun onGlobalStart(block: () -> Unit) {
        onStartCallback = block
    }

    fun onGlobalEnd(block: () -> Unit) {
        onEndCallback = block
    }

    fun onGlobalCancel(block: () -> Unit) {
        onCancelCallback = block
    }

    fun cancel() {
        isActive = false
        currentAnimatorSet.cancel()
        currentAnimatorSet = AnimatorSet()
        nextAnimator = null
        animators.clear()
    }

    private fun onEnd() {
        if (nextAnimator != null) {
            nextAnimator?.start()
            prevAnimator?.nextAnimator = null
        } else {
            onEndCallback()
        }
    }

    fun next(animator: EAnimator): EAnimator {
        animator.prevAnimator = this
        nextAnimator = animator
        return animator
    }

    internal fun defineAnimation(configDsl: EAnimatorConfig.() -> Unit = {}, block: (View, EAnimatorConfig) -> List<ValueAnimator>): AnimatorBuilder {
        val animatorBuilder: AnimatorBuilder = {
            val config = EAnimatorConfig().apply(configDsl)
            views.map { view ->
                block(view, config)
            }.flatten()
        }
        animators.add(animatorBuilder)
        return animatorBuilder
    }

    private fun start(index: Int) {
        if (index > animators.size - 1) {
            onEnd()
            return
        }
        currentAnimatorSet = AnimatorSet()
        views.forEach { it.doOnDetach { currentAnimatorSet.cancel() } }
        currentAnimatorSet.doOnEnd { if (isActive) start(index + 1) }
        currentAnimatorSet.doOnCancel { onCancelCallback() }
        globalDuration?.let { currentAnimatorSet.duration = it / animators.size }
        globalInterpolator?.let { currentAnimatorSet.interpolator = it }
        currentAnimatorSet.playTogether(*animators[index].invoke().toTypedArray())
        currentAnimatorSet.start()
    }

    fun start() {
        if (animators.isNotEmpty()) {
            onStartCallback()
            start(0)
        }
    }
}

typealias AnimatorBuilder = () -> List<ValueAnimator>

fun View.animate(animatorDsl: EAnimator.() -> Unit) = listOf(this).animate(animatorDsl)
fun List<View>.animate(animatorDsl: EAnimator.() -> Unit) = EAnimator(this).apply(animatorDsl)
