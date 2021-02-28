package com.thinkup.stepsindicator.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

const val ANIM_DEFAULT_VALUE = 300L
const val STEP_FACTOR = 100
const val MIN_STEP_FACTOR = 1

fun View.animateProgress(
    sum: Int,
    min: Int = MIN_STEP_FACTOR,
    duration: Long = ANIM_DEFAULT_VALUE,
    reverse: Boolean = false,
    listener: (() -> Unit)? = null
) {
    val anim = if (!reverse) ValueAnimator.ofInt(min, sum) else ValueAnimator.ofInt(sum, min)
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            listener?.invoke()
        }
    })
    anim.addUpdateListener { valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        val layoutParams: LinearLayout.LayoutParams = layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = value.toFloat()
        setLayoutParams(layoutParams)
    }
    anim.duration = duration
    anim.start()
}

fun View.setMargins(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.setMargins(
        left ?: lp.leftMargin,
        top ?: lp.topMargin,
        right ?: lp.rightMargin,
        bottom ?: lp.rightMargin
    )
    layoutParams = lp
}

fun ViewGroup.LayoutParams.toLinear() = this as LinearLayout.LayoutParams